package com.vikramaditya.portfolio.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.css.BackgroundSize
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.css.functions.url
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.BackToTopButton
import com.vikramaditya.portfolio.components.ThemeSwitchButton
import com.vikramaditya.portfolio.sections.Header
import com.vikramaditya.portfolio.utils.Res
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

@Composable
fun PageLayout(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    val breakpoint = rememberBreakpoint()
    var colorMode = ColorMode.current

    LaunchedEffect(title) {
        document.title = "Vikramaditya Khupse - $title"
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .backgroundImage(url(Res.Image.PAGE_BACKGROUND))
            .backgroundSize(BackgroundSize.Cover)
            .styleModifier {
                property("cursor", "url('${
                    if (colorMode.isDark) Res.Image.CUSTOM_CURSOR_DARK
                    else Res.Image.CUSTOM_CURSOR_LIGHT
                }'), auto")
            }
    ) {
        // Background overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .backgroundColor(if (colorMode.isDark) Color.rgba(0, 0, 0, 0.7f)
                else Color.rgba(255, 255, 255, 0.8f))
        )

        // Main content (below header)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.px) // Adjust based on header height
        ) {
            content()
            BackToTopButton()
        }

        // Fixed header (top layer)
        Box(
            modifier = Modifier
                .position(Position.Fixed)
//                .backdropFilter(blur(4.px))
                .zIndex(2)
                .fillMaxWidth()
        ) {
            Header(
                modifier = Modifier
                    .fillMaxWidth()
            )

        }

    }
}