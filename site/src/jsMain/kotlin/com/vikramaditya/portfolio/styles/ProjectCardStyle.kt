package com.vikramaditya.portfolio.styles

import com.varabyte.kobweb.compose.css.Background
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.css.functions.brightness
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.css.functions.toImage
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.*

val MatrixBgDark = Color.rgb(0x0d0d0d)


val ProjectCardSTyle = CssStyle {
    base {
        Modifier
            .maxWidth(280.px)
            .margin(topBottom = 3.cssRem)
            .border(1.px, LineStyle.Solid, Res.Theme.THEME_GREEN_NEON.color)
            .borderRadius(Res.Dimens.BORDER_RADIUS.px)
            .background(
                Background.of(
                    linearGradient(
                        MatrixBgDark,
                        Color.rgba(0, 0, 0, 0.7f),
                        0.deg
                    ).toImage()
                )
            )
            .backdropFilter(blur(5.px),
                    brightness(0.9))
            .boxShadow(
                offsetX = 0.px,
                offsetY = 10.px,
                blurRadius = 32.px,
                color = Color.rgba(0, 0, 0, 0.35f)
            )
            .transition(Transition.of("transform", 0.35.s), Transition.of("box-shadow", 0.35.s))
            .styleModifier {
                property("overflow", "hidden")
            }
    }
    hover {
        Modifier
            .boxShadow(
                blurRadius = 45.px,
                spreadRadius = 6.px,
                color = Res.Theme.THEME_GREEN.color
            )
            .transform {
                translateY((-6).px)
                scale(1.04)
            }
            .filter(brightness(1.2))
    }
}
