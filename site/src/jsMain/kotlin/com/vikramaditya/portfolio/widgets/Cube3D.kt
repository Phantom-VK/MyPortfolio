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
import kotlin.math.abs

/**
 * Resting orientation. Not square-on: a slight tilt shows three faces at once,
 * so the shape reads as a cube before anyone touches it.
 */
private const val REST_X = -18.0
private const val REST_Y = 26.0

/** How far the pointer can push the cube away from rest, in degrees. */
private const val TILT_X = 26.0
private const val TILT_Y = 34.0

private const val SUB_STEP = 1.0 / 240.0
private const val DT_CLAMP = 0.064
private const val REST_VELOCITY = 0.35
private const val REST_DELTA = 0.05

/**
 * Drives cube rotation with a spring instead of an 8-second linear loop.
 *
 * The old rotation was hover-only, constant-speed, and endless: motion with no
 * cause. This one tracks the pointer, so the cube turns *because* you moved,
 * and springs back to rest when you leave. Per-frame transforms are written
 * straight onto the element, never through Compose state.
 */
private class CubeController(
    /** The stable, unrotated box that receives pointer events and defines the frame. */
    private val frameEl: HTMLElement,
    /** The rotating cube. Transforms are written here. */
    private val el: HTMLElement,
) {
    private val rotX = SpringState().apply { x = REST_X }
    private val rotY = SpringState().apply { x = REST_Y }
    private val spring = Spring(dampingRatio = 0.72, response = 0.42)

    private var targetX = REST_X
    private var targetY = REST_Y
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
            targetY = REST_Y + nx * TILT_Y
            targetX = REST_X - ny * TILT_X
            start()
        }
    }

    private val onPointerLeave: (Event) -> Unit = {
        targetX = REST_X
        targetY = REST_Y
        start()
    }

    private val frame: (Double) -> Unit = { now ->
        rafHandle = 0
        val dt = if (lastTime == 0.0) SUB_STEP else ((now - lastTime) / 1000.0).coerceAtMost(DT_CLAMP)
        lastTime = now

        // Fixed sub-steps with an exact remainder, so the settle feels identical
        // at 60Hz and 120Hz rather than stiffer on a faster display.
        var remaining = dt
        while (remaining > 0.0) {
            val h = if (remaining > SUB_STEP) SUB_STEP else remaining
            spring.step(rotX, targetX, h)
            spring.step(rotY, targetY, h)
            remaining -= h
        }

        render()

        val atRest = abs(rotX.v) < REST_VELOCITY && abs(rotX.x - targetX) < REST_DELTA &&
            abs(rotY.v) < REST_VELOCITY && abs(rotY.x - targetY) < REST_DELTA
        if (atRest) {
            // Snap and stop. At rest the rAF handle is 0 and idle cost is zero.
            rotX.x = targetX; rotX.v = 0.0
            rotY.x = targetY; rotY.v = 0.0
            render()
            lastTime = 0.0
        } else {
            rafHandle = window.requestAnimationFrame(frame)
        }
    }

    private fun render() {
        val next = "rotateX(${rotX.x.fx(2)}deg) rotateY(${rotY.x.fx(2)}deg)"
        if (next != lastTransform) {
            lastTransform = next
            el.style.setProperty("transform", next)
        }
    }

    private fun start() {
        if (rafHandle == 0) {
            lastTime = 0.0
            rafHandle = window.requestAnimationFrame(frame)
        }
    }

    fun attach() {
        render()
        frameEl.addEventListener("pointermove", onPointerMove)
        frameEl.addEventListener("pointerleave", onPointerLeave)
        frameEl.addEventListener("pointercancel", onPointerLeave)
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
        rotX.x = REST_X
        rotY.x = REST_Y
        render()
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
