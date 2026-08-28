package com.vikramaditya.portfolio.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.varabyte.kobweb.compose.dom.disposableRef
import com.varabyte.kobweb.compose.dom.registerRefScope
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignSelf
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.style.toModifier
import com.vikramaditya.portfolio.styles.BackStyle
import com.vikramaditya.portfolio.styles.BottomStyle
import com.vikramaditya.portfolio.styles.BoxCardStyle
import com.vikramaditya.portfolio.styles.ContainerStyle
import com.vikramaditya.portfolio.styles.CubeFaceIconStyle
import com.vikramaditya.portfolio.styles.FaceStyle
import com.vikramaditya.portfolio.styles.FrontStyle
import com.vikramaditya.portfolio.styles.LeftStyle
import com.vikramaditya.portfolio.styles.RightStyle
import com.vikramaditya.portfolio.styles.TopStyle
import com.vikramaditya.portfolio.utils.Spring
import com.vikramaditya.portfolio.utils.SpringState
import com.vikramaditya.portfolio.utils.fx
import com.vikramaditya.portfolio.utils.rememberPrefersReducedMotion
import kotlinx.browser.window
import org.jetbrains.compose.web.css.AlignSelf
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event

/**
 * Resting tilt on X. Not square-on: a slight tilt shows three faces at once, so
 * the shape reads as a cube before anyone touches it. Y has no rest value — it
 * spins continuously, see [SPIN_DEG_PER_SEC].
 */
private const val REST_X = -18.0

/** Continuous idle spin around Y, in degrees/second — this is what makes the full 360 happen on its own, with no pointer input required (so it also works on touch). */
private const val SPIN_DEG_PER_SEC = 22.0

/** Spin speeds up under the pointer, on top of the constant idle spin. */
private const val HOVER_SPIN_MULTIPLIER = 2.4

/** How far the pointer can additionally tilt the cube away from its spin path, in degrees. */
private const val TILT_X = 22.0
private const val TILT_Y = 30.0

private const val SUB_STEP = 1.0 / 240.0
private const val DT_CLAMP = 0.064

/**
 * Drives cube rotation with a continuous spin plus a spring-driven pointer tilt
 * layered on top, instead of the old hover-only spring that only ever nudged the
 * cube a few degrees off rest and never completed a revolution.
 *
 * The spin never stops — that's what "rotates fully" means here, and it's also
 * what makes the cube work on touch devices, which never fire `pointermove`.
 * Hovering (or, on desktop, moving the pointer across the face) both speeds the
 * spin up and adds an extra tilt, so the interaction still feels responsive.
 * Per-frame transforms are written straight onto the element, never through
 * Compose state.
 */
private class CubeController(
    /** The stable, unrotated box that receives pointer events and defines the frame. */
    private val frameEl: HTMLElement,
    /** The rotating cube. Transforms are written here. */
    private val el: HTMLElement,
) {
    private var spinY = 0.0
    private val tiltX = SpringState()
    private val tiltY = SpringState()
    private val spring = Spring(dampingRatio = 0.72, response = 0.42)

    private var targetTiltX = 0.0
    private var targetTiltY = 0.0
    private var hovering = false
    private var rafHandle = 0
    private var lastTime = 0.0
    private var lastTransform = ""

    private val onPointerMove: (Event) -> Unit = { ev ->
        // Measured against the frame, never the cube: `getBoundingClientRect`
        // reports the post-transform box, so a rotating element would report a
        // rect that changes as it turns and feed its own motion back in.
        val rect = frameEl.getBoundingClientRect()
        if (rect.width > 0 && rect.height > 0) {
            val d = ev.asDynamic()
            // Normalised to -1..1 from the cube's centre.
            val nx = ((d.clientX.unsafeCast<Double>() - rect.left) / rect.width) * 2.0 - 1.0
            val ny = ((d.clientY.unsafeCast<Double>() - rect.top) / rect.height) * 2.0 - 1.0
            targetTiltY = nx * TILT_Y
            targetTiltX = -ny * TILT_X
            hovering = true
        }
    }

    private val onPointerLeave: (Event) -> Unit = {
        targetTiltX = 0.0
        targetTiltY = 0.0
        hovering = false
    }

    private val frame: (Double) -> Unit = { now ->
        val dt = if (lastTime == 0.0) SUB_STEP else ((now - lastTime) / 1000.0).coerceAtMost(DT_CLAMP)
        lastTime = now

        val speed = if (hovering) SPIN_DEG_PER_SEC * HOVER_SPIN_MULTIPLIER else SPIN_DEG_PER_SEC
        spinY = (spinY + speed * dt) % 360.0

        // Fixed sub-steps with an exact remainder, so the tilt spring feels
        // identical at 60Hz and 120Hz rather than stiffer on a faster display.
        var remaining = dt
        while (remaining > 0.0) {
            val h = if (remaining > SUB_STEP) SUB_STEP else remaining
            spring.step(tiltX, targetTiltX, h)
            spring.step(tiltY, targetTiltY, h)
            remaining -= h
        }

        render()
        // Never stops: the idle spin is perpetual, so there is no rest state to
        // detect and no reason to cancel the rAF handle mid-flight.
        rafHandle = window.requestAnimationFrame(frame)
    }

    private fun render() {
        val next = "rotateX(${(REST_X + tiltX.x).fx(2)}deg) rotateY(${(spinY + tiltY.x).fx(2)}deg)"
        if (next != lastTransform) {
            lastTransform = next
            el.style.setProperty("transform", next)
        }
    }

    fun attach() {
        render()
        frameEl.addEventListener("pointermove", onPointerMove)
        frameEl.addEventListener("pointerleave", onPointerLeave)
        frameEl.addEventListener("pointercancel", onPointerLeave)
        if (rafHandle == 0) {
            lastTime = 0.0
            rafHandle = window.requestAnimationFrame(frame)
        }
    }

    /** Every listener is a stored `val`, so removal gets the identical reference. */
    fun detach() {
        frameEl.removeEventListener("pointermove", onPointerMove)
        frameEl.removeEventListener("pointerleave", onPointerLeave)
        frameEl.removeEventListener("pointercancel", onPointerLeave)
        if (rafHandle != 0) window.cancelAnimationFrame(rafHandle)
        rafHandle = 0
    }

    fun renderStatic() {
        el.style.setProperty("transform", "rotateX(${REST_X.fx(2)}deg) rotateY(0deg)")
    }
}

@Composable
fun Cube3D(
    icons: List<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    val reduced = rememberPrefersReducedMotion()
    val frameState = remember { mutableStateOf<HTMLElement?>(null) }
    val cubeState = remember { mutableStateOf<HTMLElement?>(null) }
    val frameEl = frameState.value
    val cube = cubeState.value

    DisposableEffect(frameEl, cube, reduced) {
        if (frameEl == null || cube == null) {
            onDispose { }
        } else {
            val controller = CubeController(frameEl, cube)
            if (reduced) {
                // No listeners, no loop: just the resting orientation.
                controller.renderStatic()
                onDispose { }
            } else {
                controller.attach()
                onDispose { controller.detach() }
            }
        }
    }

    Box(
        modifier = modifier
            .alignSelf(AlignSelf.Center)
            .padding(topBottom = 4.percent)
    ) {
        Div(ContainerStyle.toModifier().toAttrs()) {
            Div(BoxCardStyle.toModifier().toAttrs()) {
                val faceStyles = listOf(
                    FrontStyle, BackStyle, RightStyle, LeftStyle, TopStyle, BottomStyle
                )

                for (i in faceStyles.indices) {
                    val (iconSrc, label) = icons.getOrNull(i) ?: continue

                    Div(
                        attrs = FaceStyle.toModifier()
                            .then(faceStyles[i].toModifier())
                            .toAttrs()
                    ) {
                        Image(
                            src = iconSrc,
                            alt = label,
                            modifier = CubeFaceIconStyle.toModifier()
                        )
                    }
                }

                // Registered last, so the element exists and its children are in
                // place before the controller measures and attaches to it.
                registerRefScope(
                    disposableRef { element ->
                        cubeState.value = element
                        onDispose { cubeState.value = null }
                    }
                )
            }

            registerRefScope(
                disposableRef { element ->
                    frameState.value = element
                    onDispose { frameState.value = null }
                }
            )
        }
    }
}
