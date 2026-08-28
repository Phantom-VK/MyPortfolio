package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.framework.annotations.DelicateApi
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.HeaderItem
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.utils.theme.Font
import com.vikramaditya.portfolio.utils.theme.Space
import com.vikramaditya.portfolio.utils.theme.Type
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.fontFace
import com.vikramaditya.portfolio.utils.theme.textStyle
import com.vikramaditya.portfolio.widgets.ThemeSwitchButton
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.w3c.dom.events.Event

/** Anchor ids in page order. Scroll-spy walks this, so a new section must be added here. */
private val NAV_SECTIONS = listOf("home", "about-me", "projects", "achievements", "contact")

@OptIn(DelicateApi::class)
@Composable
fun Header(modifier: Modifier) {
    var colorMode by ColorMode.currentState
    val breakpoint = rememberBreakpoint()
    val c = colors(colorMode)

    var showHeader by remember { mutableStateOf(true) }
    var currentSection by remember { mutableStateOf(NAV_SECTIONS.first()) }

    // A scroll listener is unavoidable for a hide-on-scroll header, but the cost
    // that usually comes with one is not: state is written only when a derived
    // value actually flips, so scrolling does not recompose per frame. The
    // previous version added this listener and never removed it.
    DisposableEffect(Unit) {
        var lastScrollY = window.scrollY
        var ticking = false

        fun evaluate() {
            ticking = false
            val scrollY = window.scrollY

            val nextShow = scrollY < lastScrollY || scrollY < 100
            lastScrollY = scrollY
            if (nextShow != showHeader) showHeader = nextShow

            val scrollHeight = (document.documentElement?.scrollHeight ?: 0).toDouble()
            val clientHeight = (document.documentElement?.clientHeight ?: 0).toDouble()
            val atBottom = scrollY + clientHeight >= scrollHeight - 70

            val next = if (atBottom) {
                NAV_SECTIONS.last()
            } else {
                // The section whose top most recently passed under the header.
                NAV_SECTIONS.lastOrNull { id ->
                    val top = document.getElementById(id)
                        ?.getBoundingClientRect()?.top?.plus(scrollY)
                    top != null && scrollY >= top - 140
                } ?: NAV_SECTIONS.first()
            }
            if (next != currentSection) currentSection = next
        }

        val listener: (Event) -> Unit = {
            if (!ticking) {
                ticking = true
                window.requestAnimationFrame { evaluate() }
            }
        }

        window.addEventListener("scroll", listener)
        evaluate()
        onDispose { window.removeEventListener("scroll", listener) }
    }

    if (showHeader) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                // Translucent chrome with content scrolling under it, rather than
                // an opaque bar that eats a fixed strip of the viewport.
                .backgroundColor(c.chrome)
                .backdropFilter(blur(14.px))
                .borderBottom(1.px, LineStyle.Solid, c.border)
                .padding(leftRight = 4.percent)
        ) {
            if (breakpoint > Breakpoint.SM) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    SpanText(
                        text = "Hi, I am ${Res.String.NAME}",
                        modifier = Modifier
                            .fontFace(Font.DISPLAY)
                            .margin(topBottom = Space.md)
                            .color(c.textPrimary)
                            .textStyle(Type.Title)
                            .textAlign(TextAlign.Center)
                            .cursor(com.varabyte.kobweb.compose.css.Cursor.Pointer)
                            .onClick { scrollToTop() }
                    )
                }
            }

            // Scrollable on narrow screens: at small breakpoints the nav items plus
            // the theme toggle do not all fit, and flexbox clips rather than wraps.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .overflow(Overflow.Auto, Overflow.Visible)
                    .styleModifier {
                        property("-webkit-overflow-scrolling", "touch")
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                HeaderItem("Home", isOnline = currentSection == "home", breakpoint = breakpoint) {
                    scrollToTop()
                }
                HeaderItem("About", isOnline = currentSection == "about-me", breakpoint = breakpoint) {
                    scrollToSection("about-me")
                }
                HeaderItem("Projects", isOnline = currentSection == "projects", breakpoint = breakpoint) {
                    scrollToSection("projects")
                }
                HeaderItem("Awards", isOnline = currentSection == "achievements", breakpoint = breakpoint) {
                    scrollToSection("achievements")
                }
                HeaderItem("Contact", isOnline = currentSection == "contact", breakpoint = breakpoint) {
                    scrollToSection("contact")
                }
                ThemeSwitchButton(colorMode = colorMode) { colorMode = colorMode.opposite }
            }
        }
    }
}

private fun scrollToTop() {
    val options = js("{}")
    options.top = 0
    options.behavior = "smooth"
    window.asDynamic().scrollTo(options)
}

/**
 * Uses `scrollIntoView` rather than computing an offset by hand, so the
 * `scroll-margin-top` declared on section headings does the work. That keeps
 * hash links and back/forward restoration correct too, which manual offset math
 * never covered.
 */
fun scrollToSection(id: String) {
    val element = document.getElementById(id) ?: return
    val options = js("{}")
    options.behavior = "smooth"
    options.block = "start"
    element.asDynamic().scrollIntoView(options)
}
