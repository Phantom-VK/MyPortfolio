package com.vikramaditya.portfolio.styles

import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.selectors.hover
import com.vikramaditya.portfolio.utils.theme.Radius
import com.vikramaditya.portfolio.utils.theme.Space
import com.vikramaditya.portfolio.utils.theme.colors
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s

/**
 * A plain wrapping grid, not a carousel: Badges are deliberately lighter-weight
 * than Certifications, so they shouldn't carry the same scroll-snap/nav-button
 * machinery. Small tiles, no fixed row count — they just flow and wrap.
 */
val BadgeGridStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .styleModifier {
            property("display", "grid")
            property("grid-template-columns", "repeat(auto-fill, minmax(128px, 1fr))")
            property("gap", "12px")
        }
}

val BadgeTileStyle = CssStyle {
    base {
        val c = colors(colorMode)
        Modifier
            .fillMaxWidth()
            .display(org.jetbrains.compose.web.css.DisplayStyle.Flex)
            .flexDirection(org.jetbrains.compose.web.css.FlexDirection.Column)
            .alignItems(org.jetbrains.compose.web.css.AlignItems.Center)
            .padding(Space.sm)
            .borderRadius(Radius.default)
            .border(1.px, LineStyle.Solid, c.border)
            .backgroundColor(c.surfaceRaised)
            .transition(Transition.of("border-color", 0.2.s))
            .cursor(Cursor.Default)
    }
    hover {
        val c = colors(colorMode)
        Modifier.border(1.px, LineStyle.Solid, c.borderStrong)
    }
}

/** Fixed-height image box so every tile lines up regardless of the source badge's aspect ratio. */
val BadgeImageBoxStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .styleModifier {
            property("height", "64px")
            property("display", "flex")
            property("align-items", "center")
            property("justify-content", "center")
        }
}

val BadgeImageStyle = CssStyle.base {
    Modifier.styleModifier {
        property("max-width", "100%")
        property("max-height", "100%")
        property("object-fit", "contain")
        property("-webkit-user-drag", "none")
        property("pointer-events", "none")
    }
}

/** Issuer + label, line-clamped so one long title doesn't stretch the whole row. */
val BadgeLabelStyle = CssStyle.base {
    Modifier.styleModifier {
        property("display", "-webkit-box")
        property("-webkit-line-clamp", "2")
        property("-webkit-box-orient", "vertical")
        property("overflow", "hidden")
        property("text-align", "center")
    }
}
