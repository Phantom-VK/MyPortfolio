package com.vikramaditya.portfolio.styles

import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.focusVisible
import com.varabyte.kobweb.silk.style.selectors.hover
import com.vikramaditya.portfolio.utils.theme.colors
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px

/**
 * Selection colours are applied inline at the call site (see `CodeBox.kt`) so
 * they track state directly. That means this style must not try to set colour
 * on hover: an inline `style` attribute always beats a class rule, so such a
 * rule would silently never apply. Opacity and outline are untouched inline,
 * so they are what hover and focus can actually move.
 */
val LanguageButtonStyle = CssStyle {
    base {
        // The previous rule transitioned "scaleY", which is not a CSS property,
        // so nothing ever animated.
        Modifier.transition(
            Transition.of("opacity", 180.ms),
            Transition.of("background-color", 180.ms),
        )
    }
    hover {
        Modifier.opacity(0.82f)
    }
    focusVisible {
        Modifier
            .outline(2.px, LineStyle.Solid, colors(colorMode).signal)
            .styleModifier { property("outline-offset", "-2px") }
    }
}
