package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.FontSize
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.Width
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.framework.annotations.DelicateApi
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.widgets.HeaderItem
import com.vikramaditya.portfolio.widgets.ThemeSwitchButton
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.w3c.dom.SMOOTH
import org.w3c.dom.ScrollBehavior
import org.w3c.dom.ScrollToOptions

@OptIn(DelicateApi::class)
@Composable
fun Header(modifier: Modifier) {
    var colorMode by ColorMode.currentState
    val breakpoint = rememberBreakpoint()
    val isMobile = breakpoint <= Breakpoint.SM

    val (bgColor, textColor) = if (colorMode.isDark) {
        Res.Theme.DARK_THEME_BACKGROUND.color to Res.Theme.LIGHT_THEME_BACKGROUND.color
    } else {
        Res.Theme.LIGHT_THEME_BACKGROUND.color to Res.Theme.DARK_THEME_BACKGROUND.color
    }

    var scrollY by remember { mutableStateOf(0.0) }
    var currentSection by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        window.addEventListener("scroll", {
            val scrollY = (document.documentElement?.scrollTop ?: 0.0).toDouble()
            val scrollHeight = (document.documentElement?.scrollHeight ?: 0).toDouble()
            val clientHeight = (document.documentElement?.clientHeight ?: 0).toDouble()

            val atBottom = scrollY + clientHeight >= scrollHeight - 50
            val sections = listOf("", "about-me", "languages", "projects", "contact")
            val sectionOffsets = sections.associateWith { id ->
                document.getElementById(id)?.getBoundingClientRect()?.top?.plus(window.scrollY) ?: Double.MAX_VALUE
            }

            currentSection = when {
                atBottom -> "contact"
                else -> sectionOffsets.minByOrNull { (_, top) ->
                    val distance = kotlin.math.abs(scrollY - top)
                    if (distance < 500) distance else Double.MAX_VALUE
                }?.key ?: ""
            }
        })
    }


    Column(
        modifier = modifier
            .fillMaxWidth()
            .backdropFilter(blur(4.px))
            .padding(leftRight = 4.percent)
    ) {
        // Top Row: Branding
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement =  Arrangement.Center
        ) {
            SpanText(
                text = "Hi, I am ${Res.String.NAME}",
                modifier = Modifier
                    .fontFamily("Share Tech Mono")
                    .color(textColor)
                    .fontSize(FontSize.XLarge)
                    .fontWeight(FontWeight.Bold)
                    .textAlign(TextAlign.Center)
                    .onClick {
                        document.documentElement?.scroll(ScrollToOptions(top = 0.0, behavior = ScrollBehavior.SMOOTH))
                    }
            )
        }

        // Bottom Row: Navigation
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.px),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            HeaderItem("Home", isOnline = currentSection == "") {
                document.documentElement?.scroll(ScrollToOptions(top = 0.0, behavior = ScrollBehavior.SMOOTH))
            }
            HeaderItem("About Me", isOnline = currentSection == "about-me") {
                scrollToSection("about-me")
            }
            HeaderItem("Skills", isOnline = currentSection == "languages") {
                scrollToSection("languages")
            }
            HeaderItem("Projects", isOnline = currentSection == "projects") {
                scrollToSection("projects")
            }
            HeaderItem("Contact Me", isOnline = currentSection == "contact") {
                scrollToSection("contact")
            }
            ThemeSwitchButton(
                colorMode = colorMode,
                onClick = { colorMode = colorMode.opposite }
            )
        }
    }
}

fun scrollToSection(id: String) {
    val element = document.getElementById(id)
    val offsetTop = element?.getBoundingClientRect()?.top?.plus(window.scrollY)
    offsetTop?.let {
        window.scroll(ScrollToOptions(top = it, behavior = ScrollBehavior.SMOOTH))
    }
}
