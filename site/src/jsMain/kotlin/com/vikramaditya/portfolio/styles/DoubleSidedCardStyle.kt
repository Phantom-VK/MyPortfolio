package com.vikramaditya.portfolio.styles

import com.varabyte.kobweb.compose.css.BackfaceVisibility
import com.varabyte.kobweb.compose.css.TransformStyle
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.selectors.focusVisible
import com.vikramaditya.portfolio.utils.theme.Radius
import com.vikramaditya.portfolio.utils.theme.Stroke
import com.vikramaditya.portfolio.utils.theme.colors
import org.jetbrains.compose.web.css.*

private val sharedFaceModifier = Modifier
    .position(Position.Absolute)
    .display(DisplayStyle.Flex)
    .backfaceVisibility(BackfaceVisibility.Hidden)
    .alignItems(AlignItems.Center)
    .justifyContent(JustifyContent.Center)
    .borderRadius(Radius.default)
    .fillMaxSize()

/**
 * The flip card.
 *
 * Height is `100%` with a floor rather than a fixed 200px, so the card can be
 * stretched by a grid row (the bento lead card spans two rows) while still
 * standing up on its own in a single-column stack.
 *
 * The flip triggers on `:focus-within` as well as `:hover`, because hover does
 * not exist on touch and never existed for the keyboard: without it, the back
 * of every card was unreachable on a phone.
 */
val CardStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .height(100.percent)
            .styleModifier {
                property("min-height", "200px")
                property("perspective", "1000px")
            }
    }
    cssRule(":hover .card-inner") {
        Modifier.transform { rotateY(180.deg) }
    }
    cssRule(":focus-within .card-inner") {
        Modifier.transform { rotateY(180.deg) }
    }
    focusVisible {
        Modifier
            .outline(2.px, LineStyle.Solid, colors(colorMode).signal)
            .styleModifier { property("outline-offset", "3px") }
    }
}

val CardInnerStyle = CssStyle.base {
    Modifier
        .position(Position.Relative)
        .transition(Transition.of("transform", 0.6.s))
        .transformStyle(TransformStyle.Preserve3d)
        .fillMaxSize()
}

val CardFrontStyle = CssStyle.base {
    val c = colors(colorMode)
    sharedFaceModifier
        .backgroundColor(c.surfaceRaised)
        .border(Stroke.hairline, LineStyle.Solid, c.border)
}

val CardBackStyle = CssStyle.base {
    val c = colors(colorMode)
    sharedFaceModifier
        .backgroundColor(c.surface)
        // The accent edge is what tells you the face turned over.
        .border(Stroke.hairline, LineStyle.Solid, c.borderStrong)
        .color(c.textSecondary)
        .transform { rotateY(180.deg) }
}
