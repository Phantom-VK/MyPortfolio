package com.vikramaditya.portfolio.styles


import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.rotate
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.selectors.focusVisible
import com.varabyte.kobweb.silk.style.selectors.hover
import com.vikramaditya.portfolio.utils.theme.Radius
import com.vikramaditya.portfolio.utils.theme.Space
import com.vikramaditya.portfolio.utils.theme.Stroke
import com.vikramaditya.portfolio.utils.theme.colors
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px


/**
 * Shared chrome for every square icon target.
 *
 * These live in a style rather than in the composable's modifier chain because
 * a modifier applied at a call site becomes an inline `style` attribute, and an
 * inline value always beats a class rule: setting the background inline would
 * silently kill every `:hover` background below.
 */
val IconButtonStyle = CssStyle.base {
    val c = colors(colorMode)
    Modifier
        .padding(Space.sm)
        .backgroundColor(c.surfaceRaised)
        .borderRadius(Radius.default)
        .border(Stroke.hairline, LineStyle.Solid, c.border)
}

val SocialIconStyle = CssStyle {
    base {
        Modifier
            .rotate(0.deg)
            .transition(
                Transition.of(property = "rotate", duration = 250.ms),
                Transition.of(property = "background-color", duration = 250.ms),
                Transition.of(property = "border-color", duration = 250.ms),
            )
    }
    hover {
        val c = colors(colorMode)
        // A small tilt plus an accent edge. Enough to confirm the target
        // without the icon jumping out of the row.
        Modifier
            .rotate((-6).deg)
            .backgroundColor(c.surface)
            .border(Stroke.hairline, LineStyle.Solid, c.borderStrong)
    }
    focusVisible {
        Modifier
            .outline(2.px, LineStyle.Solid, colors(colorMode).signal)
            .styleModifier { property("outline-offset", "2px") }
    }
}

val ThemeIconStyle = CssStyle {
    base {
        val c = colors(colorMode)
        Modifier
            .backgroundColor(c.surfaceRaised)
            .border(Stroke.hairline, LineStyle.Solid, c.border)
            .transition(
                Transition.of(property = "background-color", duration = 250.ms),
                Transition.of(property = "border-color", duration = 250.ms),
            )
    }
    hover {
        val c = colors(colorMode)
        Modifier
            .backgroundColor(c.surface)
            .border(Stroke.hairline, LineStyle.Solid, c.borderStrong)
    }
    focusVisible {
        Modifier
            .outline(2.px, LineStyle.Solid, colors(colorMode).signal)
            .styleModifier { property("outline-offset", "2px") }
    }
}
