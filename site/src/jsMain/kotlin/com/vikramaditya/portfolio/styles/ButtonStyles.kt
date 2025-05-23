package com.vikramaditya.portfolio.styles


import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.margin
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.transform
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px


@OptIn(ExperimentalComposeWebApi::class)
val ContactLinkButtonStyle = CssStyle {
    base {
        Modifier
            .transition(
                Transition.of(property = "transform", duration = 300.ms),
                Transition.of(property = "background", duration = 300.ms)
            )
            .background(Res.Theme.BLUE.color)
            .display(DisplayStyle.InlineBlock)
    }
    hover {
        Modifier
            .background(Res.Theme.BLUE.color)
            .transform {
                scale(1.5)  // Pop-up effect
               margin(
                    bottom = 10.px
                )
            }
    }
}


