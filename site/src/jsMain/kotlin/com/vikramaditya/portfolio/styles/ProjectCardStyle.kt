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
            .borderRadius(0.7.cssRem)
            .background(
                Background.of(
                    linearGradient(
                        0.deg,
                        MatrixBgDark,
                        Color.rgba(0, 0, 0, 0.7f)
                    ).toImage()
                )
            )
            .backdropFilter(blur(5.px),
                    brightness(0.9))
            .transition(Transition.of("all", 0.5.s))
    }
    hover {
        Modifier
            .boxShadow(blurRadius = 40.px, spreadRadius = 7.px, color = Res.Theme.THEME_GREEN.color)
            .transform { scale(1.05) }
            .filter(brightness(1.2))
    }
}
