package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FontSize
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.Width
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.lightened
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.widgets.ThemeSwitchButton
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.widgets.HeaderItem
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.w3c.dom.SMOOTH
import org.w3c.dom.ScrollBehavior
import org.w3c.dom.ScrollToOptions


@Composable
fun Header(modifier: Modifier) {
    var colorMode by ColorMode.currentState
    val ctx = rememberPageContext()
    var selectedItem by remember { mutableIntStateOf(-1) }

    val (bgColor, textColor) = if (colorMode.isDark) {
        Res.Theme.DARK_THEME_BACKGROUND.color to Res.Theme.LIGHT_THEME_BACKGROUND.color
    } else {
        Res.Theme.LIGHT_THEME_BACKGROUND.color to Res.Theme.DARK_THEME_BACKGROUND.color
    }

    val breakpoint = rememberBreakpoint()
    val isMobile = breakpoint <= Breakpoint.SM

    Row(
        modifier = modifier
            .fillMaxWidth()
            .backdropFilter(blur(4.px))
            .padding(leftRight = 5.percent),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (isMobile) Arrangement.Center else Arrangement.SpaceBetween
    ) {
        // Left Branding / Name
        SpanText(
            text = "Hi, I am ${Res.String.NAME}",
            modifier = Modifier
                .fontFamily("Share Tech Mono")
                .color(textColor)
                .fontSize(FontSize.XLarge)
                .fontWeight(FontWeight.Bold)
                .textAlign(TextAlign.Start)
        )

        // Navigation Items + Theme Switch
        Row(
            modifier = Modifier
                .padding(left = if (isMobile) 0.px else 2.cssRem)
                .width(Width.FitContent),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            HeaderItem("Home", isOnline = selectedItem == 4) {
                document.documentElement?.scroll(ScrollToOptions(top = 0.0, behavior = ScrollBehavior.SMOOTH))
                selectedItem = 4
            }
            HeaderItem("About Me", isOnline = selectedItem == 0) {
                window.location.href = "#about-me"
                selectedItem = 0
            }

            HeaderItem("Skills", isOnline = selectedItem == 1) {
                window.location.href = "#skills"
                selectedItem = 1
            }

            HeaderItem("Projects", isOnline = selectedItem == 2) {
                window.location.href = "#projects"
                selectedItem = 2
            }

            HeaderItem("Contact Me", isOnline = selectedItem == 3) {
                window.location.href = "#contact"
                selectedItem = 3
            }

            ThemeSwitchButton(
                colorMode = colorMode,
                onClick = { colorMode = colorMode.opposite }
            )
        }
    }
}


//                HangingText(
//                    text = "Resume",
//                    onClick = {ctx.router.navigateTo(Res.String.RESUME_URL) }
//                )