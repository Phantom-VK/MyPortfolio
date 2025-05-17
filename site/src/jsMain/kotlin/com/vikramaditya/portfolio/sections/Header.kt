package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.FontSize
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.Width
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.widgets.HeaderItem
import com.vikramaditya.portfolio.widgets.ThemeSwitchButton
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.w3c.dom.SMOOTH
import org.w3c.dom.ScrollBehavior
import org.w3c.dom.ScrollToOptions

@Composable
fun Header(modifier: Modifier) {
    var colorMode by ColorMode.currentState

    val (bgColor, textColor) = if (colorMode.isDark) {
        Res.Theme.DARK_THEME_BACKGROUND.color to Res.Theme.LIGHT_THEME_BACKGROUND.color
    } else {
        Res.Theme.LIGHT_THEME_BACKGROUND.color to Res.Theme.DARK_THEME_BACKGROUND.color
    }

    var scrollY by remember { mutableStateOf(0.0) }
    var currentSection by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        window.addEventListener("scroll", {
            scrollY = document.documentElement?.scrollTop ?: 0.0

            val sections = listOf("","about-me", "languages", "projects", "contact")
            val sectionOffsets = sections.associateWith { id ->
                document.getElementById(id)?.getBoundingClientRect()?.top?.plus(window.scrollY) ?: Double.MAX_VALUE
            }

            currentSection = sectionOffsets.minByOrNull { (id, top) ->
                val distance = kotlin.math.abs(scrollY - top)
                if (distance < 500) distance else Double.MAX_VALUE
            }?.key ?: ""
        })
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .backdropFilter(blur(4.px))
            .padding(leftRight = 5.percent),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SpanText(
            text = "Hi, I am ${Res.String.NAME}",
            modifier = Modifier
                .fontFamily("Share Tech Mono")
                .color(textColor)
                .fontSize(FontSize.XLarge)
                .fontWeight(FontWeight.Bold)
                .textAlign(TextAlign.Start)
                .onClick {
                    document.documentElement?.scroll(ScrollToOptions(top = 0.0, behavior = ScrollBehavior.SMOOTH))
                }
        )

        Row(
            modifier = Modifier.width(Width.FitContent),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            HeaderItem("Home", isOnline = currentSection == "") {
                document.documentElement?.scroll(ScrollToOptions(top = 0.0, behavior = ScrollBehavior.SMOOTH))
            }
            HeaderItem("About Me", isOnline = currentSection == "about-me") {
                window.location.href = "#about-me"
            }
            HeaderItem("Skills", isOnline = currentSection == "languages") {
                window.location.href = "#languages"
            }
            HeaderItem("Projects", isOnline = currentSection == "projects") {
                window.location.href = "#projects"
            }

            HeaderItem("Contact Me", isOnline = currentSection == "contact") {
                window.location.href = "#contact"
            }

            ThemeSwitchButton(
                colorMode = colorMode,
                onClick = { colorMode = colorMode.opposite }
            )
        }
    }
}


