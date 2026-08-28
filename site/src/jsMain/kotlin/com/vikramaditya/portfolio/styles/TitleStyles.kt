package com.vikramaditya.portfolio.styles

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.vikramaditya.portfolio.utils.theme.Type
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.textStyle

val SubheadlineTextStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .color(colors(colorMode).textSecondary)
}

/**
 * Section headings.
 *
 * `scroll-margin-top` is what makes anchor navigation land correctly under the
 * fixed 80px header. Doing it here lets the browser handle the offset for
 * `scrollIntoView`, hash links, and back/forward restoration alike, instead of
 * re-deriving it in JavaScript at one call site.
 */
val SectionTitleStyle = CssStyle {
    base {
        Modifier
            .textStyle(Type.Heading)
            .color(colors(colorMode).textPrimary)
            .styleModifier { property("scroll-margin-top", "104px") }
    }
    Breakpoint.MD { Modifier.textStyle(Type.Display) }
}
