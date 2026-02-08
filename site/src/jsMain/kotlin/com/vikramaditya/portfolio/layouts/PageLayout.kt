package com.vikramaditya.portfolio.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
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
import com.vikramaditya.portfolio.components.MatrixCursor
import com.vikramaditya.portfolio.components.MatrixRainAnimation
import com.vikramaditya.portfolio.sections.Header
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.percent
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
    var scrollProgress by remember { mutableStateOf(0f) }

    LaunchedEffect(title) {
        document.title = "Vikramaditya Khupse - $title"
    }

    DisposableEffect(Unit) {
        val listener: (org.w3c.dom.events.Event) -> Unit = {
            val root = (document.documentElement ?: document.body) as? org.w3c.dom.HTMLElement
            if (root != null) {
                val scrollTop = root.scrollTop.toDouble()
                val scrollHeight = root.scrollHeight.toDouble()
                val clientHeight = root.clientHeight.toDouble()
                val denominator = max(1.0, scrollHeight - clientHeight)
                scrollProgress = (scrollTop / denominator).toFloat().coerceIn(0f, 1f)
            }
        }
        window.addEventListener("scroll", listener)
        onDispose {
            window.removeEventListener("scroll", listener)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .cursor(Cursor.None)
//            .styleModifier {
//                property("cursor", "url('${
//                    if (colorMode.isDark) Res.Image.CUSTOM_CURSOR_DARK
//                    else Res.Image.CUSTOM_CURSOR_LIGHT
//                }'), auto")
//            }
    ) {
        //  Matrix Rain Background (lighter settings on small screens)
        val isMobile = breakpoint <= Breakpoint.SM
        MatrixRainAnimation(
            modifier = Modifier.fillMaxSize(),
            fontSizePx = if (isMobile) 22 else 16,
            frameDelayMs = if (isMobile) 70 else 50,
            trailAlpha = if (isMobile) 0.08 else 0.05
        )

        if(breakpoint > Breakpoint.SM){
            MatrixCursor()
        }


        // ✅Overlay Layer (faint black for dark mode, white for light)
        Box(
            modifier = Modifier
                .id("overlay")
                .fillMaxSize()
                .zIndex(1)
                .backgroundColor(
                    if (colorMode.isDark)
                        Color.rgba(0, 0, 0, 0.5f)
                    else
                       Color.rgba(255, 255, 255, 0.5f)
                )
        )

        // Fixed Header (above everything)
        Box(
            modifier = Modifier
                .position(Position.Fixed)
                .zIndex(3)
                .fillMaxWidth()
        ) {
            // scroll progress indicator
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.px)
                    .backgroundColor(Color.rgba(0, 0, 0, 0.2f))
            ) {
                Box(
                    modifier = Modifier
                        .height(100.percent)
                        .styleModifier {
                            property("width", "${scrollProgress * 100}%")
                            property(
                                "background",
                                "linear-gradient(90deg, #ffffff 0%, #5cf0c5 55%, #00ff41 100%)"
                            )
                            property("transition", "width 120ms ease-out")
                        }
                )
            }

            Header(
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Main Content Layer
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
