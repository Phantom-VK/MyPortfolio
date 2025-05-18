package com.vikramaditya.portfolio.styles

import com.varabyte.kobweb.compose.css.BackfaceVisibility
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.TransformStyle
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.*

private val sharedModifier3 = Modifier
    .position(Position.Absolute)
    .display(DisplayStyle.Flex)
    .backfaceVisibility(BackfaceVisibility.Hidden)
    .alignItems(AlignItems.Center)
    .justifyContent(JustifyContent.Center)
    .borderRadius(0.5.cssRem)
    .fillMaxSize()

val CardStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .height(200.px)
            .cursor(Cursor.Pointer)
            .styleModifier {
                property("perspective", "1000px")
            }
    }
    cssRule(":hover .card-inner") {
        Modifier
            .transform { rotateY(180.deg) }
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
    sharedModifier3
        .background(Res.Theme.GREY_BACKGROUND.color)
}

val CardBackStyle = CssStyle.base {
    sharedModifier3
        .background(Res.Theme.DARK_CARD_BACKGROUND.color)
        .color(Colors.White)
        .transform { rotateY(180.deg) }
}
