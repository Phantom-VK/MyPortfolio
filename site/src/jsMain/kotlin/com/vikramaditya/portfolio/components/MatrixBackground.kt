package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.varabyte.kobweb.compose.dom.disposableRef
import com.varabyte.kobweb.compose.dom.registerRefScope
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssName
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.utils.prefersReducedMotion
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Canvas
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.events.Event
import kotlin.math.max
import kotlin.random.Random

@CssName("matrix-canvas")
val MatrixStyle = CssStyle.base {
    Modifier
        .position(Position.Fixed)
        .top(0.px)
        .left(0.px)
        .zIndex(0)
        .size(100.vw, 100.vh)
}

private const val LETTERS =
    "アイウエオカキグケゲゴザジズゼゾダチッヂヅデドナニネバビピプペボポマミムメモヤラリルレヲンABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

/**
 * The glyph rain, on a full-viewport canvas behind everything else.
 *
 * Driven by `requestAnimationFrame` rather than a `delay` loop, so it is synced
 * to the display and stops automatically when the tab is backgrounded. Also
 * re-fits on resize, which the previous version never did: the canvas kept its
 * first-paint dimensions and left a blank strip after any window change.
 */
@Composable
fun MatrixRainAnimation(
    modifier: Modifier = Modifier,
    fontSizePx: Int = 16,
    /** Milliseconds between glyph advances. The rain is deliberately slower than the refresh rate. */
    stepMs: Double = 55.0,
    trailAlpha: Double = 0.05,
) {
    val colorMode by ColorMode.currentState
    val canvasState = remember { mutableStateOf<HTMLCanvasElement?>(null) }
    val canvas = canvasState.value

    DisposableEffect(canvas, colorMode, fontSizePx, trailAlpha) {
        val target = canvas
        if (target == null) {
            onDispose { }
        } else {
            val ctx = target.getContext("2d") as? CanvasRenderingContext2D
            if (ctx == null) {
                onDispose { }
            } else {
                var drops = IntArray(0)
                var rafHandle = 0
                var lastStep = 0.0
                var disposed = false

                fun resize() {
                    target.width = window.innerWidth
                    target.height = window.innerHeight
                    val columns = max(1, target.width / fontSizePx)
                    val rows = max(1, target.height / fontSizePx)
                    drops = IntArray(columns) { Random.nextInt(rows) }
                }

                fun drawFrame() {
                    val fade = trailAlpha.coerceIn(0.01, 0.2)
                    ctx.fillStyle = if (colorMode.isDark) "rgba(0, 0, 0, $fade)" else "rgba(255, 255, 255, $fade)"
                    ctx.fillRect(0.0, 0.0, target.width.toDouble(), target.height.toDouble())

                    ctx.fillStyle = if (colorMode.isDark) "#00c46a" else "#0d6b3f"
                    ctx.font = "${fontSizePx}px JetBrains Mono, monospace"

                    for (i in drops.indices) {
                        ctx.fillText(
                            LETTERS.random().toString(),
                            i * fontSizePx.toDouble(),
                            drops[i] * fontSizePx.toDouble(),
                        )
                        if (drops[i] * fontSizePx > target.height && Random.nextDouble() > 0.975) {
                            drops[i] = 0
                        }
                        drops[i]++
                    }
                }

                resize()

                val onResize: (Event) -> Unit = { resize() }
                window.addEventListener("resize", onResize)

                if (prefersReducedMotion()) {
                    // A single static frame. No loop, no listeners beyond resize.
                    drawFrame()
                    onDispose {
                        disposed = true
                        window.removeEventListener("resize", onResize)
                    }
                } else {
                    lateinit var frame: (Double) -> Unit
                    frame = { timestamp ->
                        rafHandle = 0
                        if (!disposed) {
                            // rAF already pauses in a backgrounded tab, so no
                            // polling loop is needed to stop work there.
                            if (timestamp - lastStep >= stepMs) {
                                lastStep = timestamp
                                drawFrame()
                            }
                            rafHandle = window.requestAnimationFrame(frame)
                        }
                    }
                    rafHandle = window.requestAnimationFrame(frame)

                    onDispose {
                        disposed = true
                        if (rafHandle != 0) window.cancelAnimationFrame(rafHandle)
                        window.removeEventListener("resize", onResize)
                    }
                }
            }
        }
    }

    Canvas(
        attrs = MatrixStyle.toModifier()
            .then(modifier)
            .toAttrs {
                attr("aria-hidden", "true")
            }
    ) {
        registerRefScope(
            disposableRef { element ->
                canvasState.value = element
                onDispose { canvasState.value = null }
            }
        )
    }
}
