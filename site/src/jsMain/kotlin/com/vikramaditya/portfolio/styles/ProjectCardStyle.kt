package com.vikramaditya.portfolio.styles

import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.vikramaditya.portfolio.utils.theme.Radius
import com.vikramaditya.portfolio.utils.theme.Stroke
import com.vikramaditya.portfolio.utils.theme.colors
import org.jetbrains.compose.web.css.*

/**
 * Reading [colorMode] inside the style block (rather than branching at the call
 * site) lets Silk emit both light and dark variants as static CSS, so no
 * recomposition is involved in a theme switch.
 */
val ProjectCardStyle = CssStyle {
    base {
        val c = colors(colorMode)
        Modifier
            .fillMaxWidth()
            // No vertical margin: the project grid owns spacing through `gap`,
            // and a margin here would double it between rows.
            .height(100.percent)
            .border(Stroke.hairline, LineStyle.Solid, c.border)
            .borderRadius(Radius.default)
            .backgroundColor(c.surfaceRaised)
            .boxShadow(
                offsetX = 0.px,
                offsetY = 10.px,
                blurRadius = 32.px,
                color = Color.rgba(0, 0, 0, 0.35f),
            )
            .transition(
                Transition.of("transform", 0.35.s),
                Transition.of("box-shadow", 0.35.s),
                Transition.of("border-color", 0.35.s),
            )
            .styleModifier {
                property("overflow", "hidden")
            }
    }
    hover {
        val c = colors(colorMode)
        Modifier
            // Lift only. The previous scale(1.04) resampled the thumbnail and
            // made it visibly soft on hover.
            .transform { translateY((-6).px) }
            .border(Stroke.hairline, LineStyle.Solid, c.borderStrong)
            .boxShadow(
                offsetX = 0.px,
                offsetY = 16.px,
                blurRadius = 44.px,
                color = Color.rgba(0, 0, 0, 0.45f),
            )
    }
}
