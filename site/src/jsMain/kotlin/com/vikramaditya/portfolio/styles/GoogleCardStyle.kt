package com.vikramaditya.portfolio.styles

import com.varabyte.kobweb.compose.css.BoxShadow
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba

val GoogleCardStyle = CssStyle {

    base {
        Modifier
            .background(if (colorMode.isLight) Res.Theme.LIGHT_CARD_BACKGROUND.color else Res.Theme.DARK_CARD_BACKGROUND.color)
            .border {
                if (colorMode.isLight) {
                    color(Res.Theme.GoogleBlue.color)
                } else {
                    color(Res.Theme.LIGHT_BLUE.color)
                }
                width(1.px)
                style(LineStyle.Solid)
            }
            .borderRadius(7.px)
            .boxShadow(BoxShadow.of(0.px, 4.px, 8.px, color = rgba(0, 0, 0, 0.1)))
            .transition(Transition.of("all", 300.ms))
    }

    hover {
        Modifier
            .border {
                if (colorMode.isLight) {
                    color(Res.Theme.GoogleBlue.color)
                } else {
                    color(Res.Theme.LIGHT_BLUE.color)
                }
                width(1.px)
                style(LineStyle.Solid)
            }
            .borderRadius(12.px)
            .boxShadow(
                if (colorMode.isLight) {
                    BoxShadow.of(
                        blurRadius = 20.px,
                        color = Res.Theme.GoogleBlue.color
                    )
                } else {
                    BoxShadow.of(
                        blurRadius = 20.px,
                        color = Res.Theme.PRIMARY_BUTTON.color
                    )
                }
            )
            .transform { scale(1.02) }
    }
}

