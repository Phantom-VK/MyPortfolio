package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.FontSize
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.framework.annotations.DelicateApi
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.HeaderItem
import com.vikramaditya.portfolio.utils.Res
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

    val (bgColor, textColor) = if (colorMode.isDark) {
        Res.Theme.DARK_THEME_BACKGROUND.color to Res.Theme.LIGHT_THEME_BACKGROUND.color
    } else {
        Res.Theme.LIGHT_THEME_BACKGROUND.color to Res.Theme.DARK_THEME_BACKGROUND.color
    }

    var scrollY by remember { mutableStateOf(window.scrollY.toDouble()) }
    var lastScrollY by remember { mutableStateOf(window.scrollY.toDouble()) }
    var showHeader by remember { mutableStateOf(true) }
    var currentSection by remember { mutableStateOf("") }

    // Scroll listener to toggle header visibility
    LaunchedEffect(Unit) {
        window.addEventListener("scroll", {
            scrollY = window.scrollY.toDouble()

            // Direction logic
            showHeader = scrollY < lastScrollY || scrollY < 100
            lastScrollY = scrollY

            // Section detection (same as before)
            val scrollHeight = (document.documentElement?.scrollHeight ?: 0).toDouble()
            val clientHeight = (document.documentElement?.clientHeight ?: 0).toDouble()
            val atBottom = scrollY + clientHeight >= scrollHeight - 70
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

    if (showHeader) {

        Column(
            modifier = modifier.fillMaxWidth()
            .backgroundColor(Color.rgba(0, 255, 65, 0.06f))
            .backdropFilter(blur(4.px))
            .borderBottom(1.px, LineStyle.Solid, Res.Theme.THEME_GREEN_NEON.color)
            .padding(leftRight = 4.percent)
        ) {
            if(breakpoint > Breakpoint.SM) {
                // Branding
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    SpanText(
                        text = "Hi, I am ${Res.String.NAME}",
                        modifier = Modifier
                            .fontFamily("Share Tech Mono")
                            .margin(topBottom = 1.cssRem)
                            .color(textColor)
                            .fontSize(FontSize.XLarge)
                            .fontWeight(FontWeight.Bold)
                            .textAlign(TextAlign.Center)
                            .onClick {
                                document.documentElement?.scroll(
                                    ScrollToOptions(
                                        top = 0.0,
                                        behavior = ScrollBehavior.SMOOTH
                                    )
                                )
                            }
                    )
                }
            }

            // Navigation Row with animated show/hide
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                HeaderItem("Home", isOnline = currentSection == "", breakpoint = breakpoint) {
                    document.documentElement?.scroll(ScrollToOptions(top = 0.0, behavior = ScrollBehavior.SMOOTH))
                }
                HeaderItem("About", isOnline = currentSection == "about-me", breakpoint = breakpoint) {
                    scrollToSection("about-me")
                }
                HeaderItem("Skills", isOnline = currentSection == "languages", breakpoint = breakpoint) {
                    scrollToSection("languages")
                }
                HeaderItem("Projects", isOnline = currentSection == "projects", breakpoint = breakpoint) {
                    scrollToSection("projects")
                }
                HeaderItem("Contact", isOnline = currentSection == "contact", breakpoint = breakpoint) {
                    scrollToSection("contact")
                }
                ThemeSwitchButton(
                    colorMode = colorMode,
                    onClick = { colorMode = colorMode.opposite }
                )
            }

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
