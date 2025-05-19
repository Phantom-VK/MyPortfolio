package com.vikramaditya.portfolio.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
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
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px

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

    LaunchedEffect(title) {
        document.title = "Vikramaditya Khupse - $title"
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
        //  Matrix Rain Background
        MatrixRainAnimation(
            Modifier.fillMaxSize()
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
