package com.vikramaditya.portfolio.components

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssName
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.window
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Canvas
import org.w3c.dom.HTMLCanvasElement
import kotlin.math.max
import kotlin.random.Random

@CssName("matrix-canvas")
val MatrixStyle = CssStyle {
    base {
        Modifier
            .position(Position.Fixed)
            .top(0.px)
            .left(0.px)
            .zIndex(0)
            .size(100.vw, 100.vh)
    }
}

@Composable
fun MatrixRainAnimation(
    modifier: Modifier = Modifier,
    fontSizePx: Int = 16,
    frameDelayMs: Long = 50,
    trailAlpha: Double = 0.05
) {
    var colorMode by ColorMode.currentState
    val canvasRef = remember { mutableStateOf<HTMLCanvasElement?>(null) }
    val letters = "アイウエオカキグケゲゴザジズゼゾダチッヂヅデドナニネバビピプペボポマミムメモヤラリルレヲンABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

    LaunchedEffect(canvasRef.value) {
        val canvas = canvasRef.value ?: return@LaunchedEffect
        val ctx = canvas.getContext("2d") as? org.w3c.dom.CanvasRenderingContext2D ?: return@LaunchedEffect

        println("Canvas is ready. Starting animation.")

        canvas.width = window.innerWidth
        canvas.height = window.innerHeight

        val fontSize = fontSizePx
        val columns = max(1, canvas.width / fontSize)
        val drops = IntArray(columns) { Random.nextInt(canvas.height / fontSize) }

        launch {
            while (true) {
                val fade = trailAlpha.coerceIn(0.01, 0.2)
                ctx.fillStyle = if (colorMode.isDark) "rgba(0, 0, 0, $fade)" else "rgba(255, 255, 255, $fade)"
                ctx.fillRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())

                ctx.fillStyle = if (colorMode.isDark) "#0F0" else "#006400"
                ctx.font = "${fontSize}px DM Sans"

                for (i in drops.indices) {
                    val text = letters.random().toString()
                    ctx.fillText(text, i * fontSize.toDouble(), drops[i] * fontSize.toDouble())

                    if (drops[i] * fontSize > canvas.height && Random.nextDouble() > 0.975) {
                        drops[i] = 0
                    }
                    drops[i]++
                }

                delay(frameDelayMs)
            }
        }




    }

    Canvas(
        attrs = MatrixStyle.toModifier()
            .then(modifier)
            .toAttrs {
                ref { element ->
                    canvasRef.value = element
                    onDispose {}
                }
            }
    )
}
