package com.vikramaditya.portfolio.styles

import com.varabyte.kobweb.compose.css.BoxShadow
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.backgroundColor
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.backgroundImage
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.onMouseEnter
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.transform
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
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

    hover{
        Modifier
            .boxShadow(BoxShadow.of(0.px, 8.px, 16.px, color = rgba(0, 0, 0, 0.2)))
            .transform { scale(1.02) }
            .color(Colors.White)
            .backgroundImage(
                linearGradient(45.deg) {
                    add(Res.Theme.GoogleBlue.color, 0.percent)
                    add(Res.Theme.GoogleRed.color, 33.percent)
                    add(Res.Theme.GoogleYellow.color, 66.percent)
                    add(Res.Theme.GoogleGreen.color)
                }
            )
    }
}
