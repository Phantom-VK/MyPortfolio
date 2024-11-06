package com.vikramaditya.portfolio.styles

import com.varabyte.kobweb.compose.css.BoxShadow
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.functions.LinearGradient
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.transform
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba

val GoogleCardStyle = CssStyle {
    base {
        Modifier
            .background(if (colorMode.isLight) Colors.White else Colors.Black)
            .borderRadius(12.px)
            .boxShadow(BoxShadow.of(0.px, 4.px, 8.px, color = rgba(0, 0, 0, 0.1)))
            .transition(Transition.of("all", 300.ms))
    }

    hover {
        Modifier
            .boxShadow(
                // Google Blue shadow
                BoxShadow.of(
                    offsetX = (-4).px,
                    offsetY = 4.px,
                    blurRadius = 15.px,
                    color = Res.Theme.GoogleBlue.color
                ),
                // Google Red shadow
                BoxShadow.of(
                    offsetX = 4.px,
                    offsetY = 4.px,
                    blurRadius = 15.px,
                    color = Res.Theme.GoogleRed.color
                ),
                // Google Yellow shadow
                BoxShadow.of(
                    offsetX = 4.px,
                    offsetY = (-4).px,
                    blurRadius = 15.px,
                    color = Res.Theme.GoogleYellow.color
                ),
                // Google Green shadow
                BoxShadow.of(
                    offsetX = (-4).px,
                    offsetY = (-4).px,
                    blurRadius = 15.px,
                    color = Res.Theme.GoogleGreen.color
                )
            )
            .transform { scale(1.02) }
            .borderRadius(12.px)
    }
}
