package com.vikramaditya.portfolio.components

import androidx.compose.runtime.MutableState
import com.varabyte.kobweb.browser.dom.observers.ResizeObserver
import com.vikramaditya.portfolio.utils.Spring
import com.vikramaditya.portfolio.utils.SpringState
import com.vikramaditya.portfolio.utils.VelocityTracker
import com.vikramaditya.portfolio.utils.fx
import com.vikramaditya.portfolio.utils.projectMomentum
import com.vikramaditya.portfolio.utils.rubberBand
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import org.w3c.dom.pointerevents.PointerEvent
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sign

/**
 * The coverflow engine.
 *
 * Not a composable and not Compose state by design. Position, velocity, and every
 * per-frame transform live in plain vars here, and a `requestAnimationFrame` loop
 * writes `transform` strings straight onto the card elements. The single Compose
 * write in the whole loop is [activeIndexState], which changes only when the
 * rounded index changes: a handful of times per gesture, not sixty times a second.
 *
 * The one invariant that makes interruption work: [springState].x is
 * simultaneously the on-screen value and the physics value. There is no separate
 * "logical position" to accidentally animate from, and velocity is written in
 * exactly three places (the integrator, drag release, and rest detection). A
 * pointer-down deliberately touches neither, which is why grabbing a moving
 * carousel continues from where it visibly is.
 */
class CoverflowController(
    private val activeIndexState: MutableState<Int>,
    private val itemCount: Int,
    interactive: Boolean = true,
) {

    private companion object {
        const val ROTATION_DEG = 35.0
        const val MIN_SCALE = 0.82
        const val MIN_OPACITY = 0.55
        const val DEPTH_PX = 160.0
        const val CULL_DISTANCE = 3.0
        const val SIDE_COMPRESSION = 0.45
        const val SPACING_RATIO = 0.72
        const val CARD_ASPECT = 16.0 / 10.0

        const val DRAG_THRESHOLD = 8.0
        const val TAP_SLOP = 8.0

        const val SUB_STEP = 1.0 / 240.0
        const val DT_CLAMP = 0.064
        const val REST_VELOCITY = 2.0
        const val REST_DELTA = 0.25
    }

    // Overshoot is right after a flick. Overshooting back past the first card
    // after a rubber-band return is not, so the boundary is critically damped.
    private val settleSpring = Spring(dampingRatio = 0.80, response = 0.35)
    private val boundarySpring = Spring(dampingRatio = 1.00, response = 0.25)

    private val springState = SpringState()
    private var targetPx = 0.0
    private val tracker = VelocityTracker()

    private val interactive = interactive && itemCount > 1

    private var spacingPx = 240.0
    private var cardWidthPx = 320.0
    private var dimPx = 800.0

    private var pointerId = -1
    private var pending = false
    private var dragging = false
    private var downX = 0.0
    private var downY = 0.0
    private var grabPointerX = 0.0
    private var grabOffsetPx = 0.0

    private var rafHandle = 0
    private var lastFrameMs = 0.0
    private var lastAnnouncedIndex = -1

    private var viewportEl: HTMLElement? = null
    private var trackEl: HTMLElement? = null

    // Sized at construction, so a controller is always consistent with the item
    // count it was created for. The composable keys `remember` on the count,
    // which means a changed list rebuilds the engine rather than mutating it.
    private val cardEls = arrayOfNulls<HTMLElement>(itemCount)
    private val lastTransform = arrayOfNulls<String>(itemCount)
    private val lastZ = IntArray(itemCount) { Int.MIN_VALUE }
    private val lastHidden = BooleanArray(itemCount)
    private var resizeObserver: ResizeObserver? = null

    // -----------------------------------------------------------------------
    // Wiring from the composable
    // -----------------------------------------------------------------------

    fun setCard(index: Int, element: HTMLElement?) {
        if (index in cardEls.indices) {
            cardEls[index] = element
            if (element == null && index in lastTransform.indices) lastTransform[index] = null
        }
    }

    fun setTrack(element: HTMLElement?) {
        trackEl = element
    }

    fun attach(viewport: HTMLElement) {
        viewportEl = viewport

        viewport.addEventListener("pointerdown", onPointerDown)
        viewport.addEventListener("pointermove", onPointerMove)
        viewport.addEventListener("pointerup", onPointerUp)
        viewport.addEventListener("pointercancel", onPointerCancel)
        viewport.addEventListener("lostpointercapture", onPointerCancel)
        viewport.addEventListener("keydown", onKeyDown)
        viewport.addEventListener("dragstart", onDragStart)
        document.addEventListener("visibilitychange", onVisibilityChange)

        // Kobweb's ResizeObserver fires once immediately on observe(), which
        // conveniently doubles as the initial measurement.
        val observer = ResizeObserver { _, _ -> measure() }
        observer.observe(viewport)
        resizeObserver = observer

        measure()
        snapToIndexImmediately(activeIndexState.value)
    }

    /**
     * The single teardown path. Every listener is a stored `val` so
     * `removeEventListener` receives the identical reference; an inline lambda
     * here would silently fail to remove anything.
     */
    fun detach() {
        if (rafHandle != 0) {
            window.cancelAnimationFrame(rafHandle)
            rafHandle = 0
        }
        resizeObserver?.disconnect()
        resizeObserver = null

        viewportEl?.let { v ->
            v.removeEventListener("pointerdown", onPointerDown)
            v.removeEventListener("pointermove", onPointerMove)
            v.removeEventListener("pointerup", onPointerUp)
            v.removeEventListener("pointercancel", onPointerCancel)
            v.removeEventListener("lostpointercapture", onPointerCancel)
            v.removeEventListener("keydown", onKeyDown)
            v.removeEventListener("dragstart", onDragStart)
            if (pointerId >= 0) {
                runCatching {
                    if (v.hasPointerCapture(pointerId)) v.releasePointerCapture(pointerId)
                }
            }
        }
        document.removeEventListener("visibilitychange", onVisibilityChange)

        viewportEl = null
        trackEl = null
        cardEls.fill(null)
        lastTransform.fill(null)
        pointerId = -1
        dragging = false
        pending = false
    }

    // -----------------------------------------------------------------------
    // Navigation
    // -----------------------------------------------------------------------

    /**
     * Re-target only. Velocity is deliberately left alone, so holding an arrow key
     * accumulates momentum across successive calls and then settles, exactly as a
     * repeated flick would.
     */
    fun goTo(index: Int) {
        if (itemCount == 0) return
        targetPx = -index.coerceIn(0, itemCount - 1) * spacingPx
        ensureRunning()
    }

    fun step(delta: Int) = goTo(currentIndex() + delta)

    private fun currentIndex(): Int =
        if (spacingPx <= 0.0) 0 else (-springState.x / spacingPx).roundToInt().coerceIn(0, maxOf(itemCount - 1, 0))

    private fun snapToIndexImmediately(index: Int) {
        if (itemCount == 0) return
        val clamped = index.coerceIn(0, itemCount - 1)
        springState.x = -clamped * spacingPx
        springState.v = 0.0
        targetPx = springState.x
        lastAnnouncedIndex = clamped
        render()
    }

    // -----------------------------------------------------------------------
    // Geometry
    // -----------------------------------------------------------------------

    private fun measure() {
        val viewport = viewportEl ?: return

        // clientWidth, never getBoundingClientRect().width: the latter reports the
        // post-transform box, and cards here are rotated and scaled. Feeding that
        // back into spacing would shrink the carousel on every resize.
        dimPx = viewport.clientWidth.toDouble()
        if (dimPx <= 0.0) return

        cardWidthPx = min(dimPx * 0.62, 460.0).coerceAtLeast(200.0)
        val previousSpacing = spacingPx
        spacingPx = cardWidthPx * SPACING_RATIO

        viewport.style.setProperty("--cf-card-w", "${cardWidthPx.fx(1)}px")
        trackEl?.style?.height = "${(cardWidthPx / CARD_ASPECT).fx(1)}px"

        // Keep the same card centred across the resize.
        if (previousSpacing > 0.0 && spacingPx > 0.0) {
            val ratio = spacingPx / previousSpacing
            springState.x *= ratio
            springState.v *= ratio
            targetPx *= ratio
        }
        lastTransform.fill(null)
        ensureRunning()
    }

    private fun minOffset() = -(maxOf(itemCount - 1, 0)) * spacingPx

    private fun applyRubberBand(raw: Double): Double = when {
        raw > 0.0 -> rubberBand(raw, dimPx)
        raw < minOffset() -> minOffset() + rubberBand(raw - minOffset(), dimPx)
        else -> raw
    }

    // -----------------------------------------------------------------------
    // Pointer handling
    // -----------------------------------------------------------------------

    private fun nowMs(): Double = window.asDynamic().performance.now().unsafeCast<Double>()

    // Kotlin types MouseEvent.clientX as Int, which quantises away sub-pixel
    // precision from trackpads and pens and visibly coarsens release velocity.
    private fun PointerEvent.clientXd(): Double = asDynamic().clientX.unsafeCast<Double>()
    private fun PointerEvent.clientYd(): Double = asDynamic().clientY.unsafeCast<Double>()

    private val onPointerDown: (Event) -> Unit = { ev ->
        val e = ev.unsafeCast<PointerEvent>()
        if (interactive && pointerId == -1 && (e.pointerType != "mouse" || e.button.toInt() == 0)) {
            pointerId = e.pointerId
            pending = true
            dragging = false
            downX = e.clientXd()
            downY = e.clientYd()
            grabPointerX = downX
            // Preserve the grab offset so the card does not jump to the pointer.
            grabOffsetPx = springState.x
            tracker.reset()
            tracker.add(downX, nowMs())
            ensureRunning()
        }
    }

    private val onPointerMove: (Event) -> Unit = { ev ->
        val e = ev.unsafeCast<PointerEvent>()
        if (e.pointerId == pointerId) {
            val x = e.clientXd()
            val y = e.clientYd()

            if (pending) {
                val dx = x - downX
                val dy = y - downY
                if (abs(dx) > DRAG_THRESHOLD || abs(dy) > DRAG_THRESHOLD) {
                    if (abs(dx) > abs(dy)) {
                        pending = false
                        dragging = true
                        viewportEl?.let { v -> runCatching { v.setPointerCapture(pointerId) } }
                        // Re-anchor at the lock point, otherwise recognition
                        // causes a visible jump of the threshold distance.
                        grabPointerX = x
                        grabOffsetPx = springState.x
                        tracker.reset()
                        tracker.add(x, nowMs())
                    } else {
                        // Vertical intent: yield to page scroll.
                        pending = false
                        pointerId = -1
                    }
                }
            }

            if (dragging) {
                e.preventDefault()
                springState.x = applyRubberBand(grabOffsetPx + (x - grabPointerX))
                tracker.add(x, nowMs())
                // No style write here. The rAF loop renders, so input rate stays
                // decoupled from frame rate.
            }
        }
    }

    private val onPointerUp: (Event) -> Unit = { ev ->
        val e = ev.unsafeCast<PointerEvent>()
        if (e.pointerId == pointerId) {
            val wasDragging = dragging
            val moved = abs(e.clientXd() - downX)
            releaseCapture()
            pointerId = -1
            pending = false
            dragging = false

            if (!wasDragging || moved < TAP_SLOP) {
                tappedIndex(e)?.let { goTo(it) }
            } else {
                val v = tracker.velocity()
                val projected = springState.x + projectMomentum(v)
                val index = if (spacingPx > 0.0) {
                    (-projected / spacingPx).roundToInt().coerceIn(0, itemCount - 1)
                } else 0
                targetPx = -index * spacingPx
                springState.v = v
            }
            ensureRunning()
        }
    }

    /** A cancelled gesture means the system took over, so inherited velocity would feel wrong. */
    private val onPointerCancel: (Event) -> Unit = { ev ->
        val e = ev.unsafeCast<PointerEvent>()
        if (e.pointerId == pointerId) {
            releaseCapture()
            pointerId = -1
            pending = false
            dragging = false
            springState.v = 0.0
            targetPx = -currentIndex() * spacingPx
            ensureRunning()
        }
    }

    private val onDragStart: (Event) -> Unit = { ev -> ev.preventDefault() }

    private val onKeyDown: (Event) -> Unit = { ev ->
        val key = ev.asDynamic().key.unsafeCast<String?>()
        val target = when (key) {
            "ArrowLeft" -> currentIndex() - 1
            "ArrowRight" -> currentIndex() + 1
            "Home" -> 0
            "End" -> itemCount - 1
            else -> null
        }
        if (target != null && itemCount > 0) {
            ev.preventDefault()
            goTo(target)
        }
    }

    private val onVisibilityChange: (Event) -> Unit = {
        val hidden = document.asDynamic().hidden.unsafeCast<Boolean>()
        if (!hidden) {
            lastFrameMs = 0.0
            ensureRunning()
        }
    }

    private fun releaseCapture() {
        val v = viewportEl ?: return
        if (pointerId >= 0) {
            runCatching {
                if (v.hasPointerCapture(pointerId)) v.releasePointerCapture(pointerId)
            }
        }
    }

    private fun tappedIndex(e: PointerEvent): Int? {
        val target = e.target ?: return null
        val el = target.asDynamic().closest("[data-cf-index]")
        if (el == null || el == undefined) return null
        val raw = el.getAttribute("data-cf-index").unsafeCast<String?>()
        return raw?.toIntOrNull()
    }

    // -----------------------------------------------------------------------
    // Loop
    // -----------------------------------------------------------------------

    private fun ensureRunning() {
        if (rafHandle == 0 && viewportEl != null) {
            lastFrameMs = 0.0
            rafHandle = window.requestAnimationFrame(frame)
        }
    }

    private val frame: (Double) -> Unit = { timestamp ->
        rafHandle = 0

        val dt = if (lastFrameMs == 0.0) 1.0 / 60.0 else min((timestamp - lastFrameMs) / 1000.0, DT_CLAMP)
        lastFrameMs = timestamp

        if (!dragging) {
            // Past a boundary the critically damped spring pulls back without
            // overshooting the edge a second time.
            val spring = if (springState.x > 0.0 || springState.x < minOffset()) boundarySpring else settleSpring
            var remaining = dt
            while (remaining > 1e-6) {
                val h = min(SUB_STEP, remaining)
                spring.step(springState, targetPx, h)
                remaining -= h
            }
        }

        render()

        val index = currentIndex()
        if (index != lastAnnouncedIndex) {
            lastAnnouncedIndex = index
            activeIndexState.value = index
        }

        if (dragging || !atRest()) {
            rafHandle = window.requestAnimationFrame(frame)
        } else {
            // Land exactly on target, paint one final frame, then stop. With the
            // loop parked the carousel costs nothing when idle.
            springState.x = targetPx
            springState.v = 0.0
            render()
        }
    }

    // Both conditions, not just the delta: at a damping ratio of 0.8 the spring
    // passes through zero delta at speed during overshoot, and a delta-only test
    // would freeze it mid-flight.
    private fun atRest(): Boolean =
        abs(springState.v) < REST_VELOCITY && abs(springState.x - targetPx) < REST_DELTA

    private fun render() {
        if (itemCount == 0 || spacingPx <= 0.0) return
        val progress = -springState.x / spacingPx

        for (i in 0 until itemCount) {
            val el = cardEls.getOrNull(i) ?: continue
            val rel = i - progress
            val distance = abs(rel)

            if (distance > CULL_DISTANCE) {
                if (!lastHidden[i]) {
                    el.style.visibility = "hidden"
                    lastHidden[i] = true
                }
                continue
            }
            if (lastHidden[i]) {
                el.style.visibility = "visible"
                lastHidden[i] = false
            }

            val clamped = rel.coerceIn(-1.0, 1.0)
            // Past the first neighbour the spacing compresses, so a long list
            // stacks toward the edges instead of marching off screen linearly.
            val units = if (distance <= 1.0) rel else sign(rel) * (1.0 + (distance - 1.0) * SIDE_COMPRESSION)
            val x = units * spacingPx
            // A card to the right of centre should present its left edge (the one
            // nearest the middle) toward the viewer, which is a positive rotateY.
            // Flipping this sign is the classic concave-instead-of-convex bug.
            val rotation = clamped * ROTATION_DEG
            val scale = 1.0 - (1.0 - MIN_SCALE) * abs(clamped)
            val z = -abs(clamped) * DEPTH_PX
            val opacity = 1.0 - (1.0 - MIN_OPACITY) * min(distance, 1.0)

            val transform = "translate3d(${x.fx(2)}px,0px,${z.fx(1)}px) " +
                "rotateY(${rotation.fx(2)}deg) scale(${scale.fx(4)})"
            if (transform != lastTransform[i]) {
                el.style.transform = transform
                lastTransform[i] = transform
            }
            el.style.opacity = opacity.fx(3)

            val zIndex = 1000 - (distance * 100).roundToInt()
            if (zIndex != lastZ[i]) {
                el.style.zIndex = zIndex.toString()
                lastZ[i] = zIndex
            }
        }
    }
}
