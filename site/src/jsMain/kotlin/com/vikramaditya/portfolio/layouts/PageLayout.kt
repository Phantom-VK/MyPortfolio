package com.vikramaditya.portfolio.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.framework.annotations.DelicateApi
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.BackToTopButton
import com.vikramaditya.portfolio.components.MatrixRainAnimation
import com.vikramaditya.portfolio.sections.Header
import com.vikramaditya.portfolio.utils.VisitReporter
import com.vikramaditya.portfolio.utils.theme.colors
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import kotlin.math.max

val PageContentStyle = CssStyle {
    base {
        Modifier
            .fillMaxSize()
            .padding(leftRight = 1.cssRem, top = 4.cssRem)
    }
    Breakpoint.MD {
        Modifier.maxWidth(100.cssRem)
    }
}

@OptIn(DelicateApi::class)
@Composable
fun PageLayout(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    val colorMode by ColorMode.currentState
    val breakpoint = rememberBreakpoint()
    val c = colors(colorMode)
    var scrollProgress by remember { mutableStateOf(0f) }

    VisitReporter(colorMode)

    LaunchedEffect(title) {
        document.title = "Vikramaditya Khupse - $title"
    }

    DisposableEffect(Unit) {
        var ticking = false

        fun evaluate() {
            ticking = false
            val root = (document.documentElement ?: document.body) as? org.w3c.dom.HTMLElement
            if (root != null) {
                val denominator = max(1.0, root.scrollHeight.toDouble() - root.clientHeight.toDouble())
                val next = (root.scrollTop / denominator).toFloat().coerceIn(0f, 1f)
                // Only write state when the rendered percentage actually changes,
                // so a scroll does not recompose on every event.
                if ((next * 1000).toInt() != (scrollProgress * 1000).toInt()) scrollProgress = next
            }
        }

        val listener: (org.w3c.dom.events.Event) -> Unit = {
            if (!ticking) {
                ticking = true
                window.requestAnimationFrame { evaluate() }
            }
        }
        window.addEventListener("scroll", listener)
        evaluate()
        onDispose { window.removeEventListener("scroll", listener) }
    }

    // No `cursor: none` here any more. The custom matrix cursor it existed for is
    // gone: it recomposed on every mouse move, never removed its listener, and
    // replacing the system cursor hurts anyone relying on pointer affordances.
    Box(modifier = Modifier.fillMaxSize().backgroundColor(c.surface)) {
        val isMobile = breakpoint <= Breakpoint.SM
        MatrixRainAnimation(
            modifier = Modifier.fillMaxSize(),
            fontSizePx = if (isMobile) 22 else 16,
            trailAlpha = if (isMobile) 0.08 else 0.05
        )

        // Scrim over the rain, so glyphs read as texture rather than competing
        // with the content above them.
        Box(
            modifier = Modifier
                .id("overlay")
                .fillMaxSize()
                .zIndex(1)
                .backgroundColor(
                    if (colorMode.isDark) Color.rgba(11, 12, 20, 0.72f)
                    else Color.rgba(220, 220, 220, 0.80f)
                )
        )

        Box(
            modifier = Modifier
                .position(Position.Fixed)
                .zIndex(3)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.px)
                    .backgroundColor(Color.rgba(0, 0, 0, 0.2f))
            ) {
                Box(
                    modifier = Modifier
                        .height(100.percent)
                        .styleModifier {
                            property("width", "${scrollProgress * 100}%")
                            property("background-color", c.signal.toString())
                            property("transition", "width 120ms ease-out")
                        }
                )
            }

            Header(modifier = Modifier.fillMaxWidth())
        }

        Column(
            modifier = PageContentStyle.toModifier()
                .fillMaxSize()
                .zIndex(2)
                .padding(top = 80.px)
        ) {
            content()
            BackToTopButton()
        }
    }
}
