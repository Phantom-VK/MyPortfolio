package com.vikramaditya.portfolio.styles


import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.rotate
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.ms


val SocialIconStyle = CssStyle {
    base {
        Modifier
            .rotate(0.deg)
            .background(Colors.Transparent)
            .transition(
                Transition.of(property = "rotate", duration = 300.ms),
                Transition.of(property = "background", duration = 300.ms)
            )
    }
    hover {
        Modifier
            .rotate(10.deg)
            .background(
                if (colorMode.isLight) Res.Theme.SOCIAL_ICON_BACKGROUND_LIGHT.color
                else Res.Theme.SOCIAL_ICON_BACKGROUND_DARK.color
            )
    }
}

val ThemeIconStyle = CssStyle {
    base {
        Modifier
            .background(
                if (colorMode.isLight) Colors.White
                else Res.Theme.DARK_BLUE.color
            )
            .transition(
                Transition.of(property = "background", duration = 300.ms)
            )
    }

    hover {
        Modifier
            .background(
                if (colorMode.isLight) Res.Theme.SOCIAL_ICON_BACKGROUND_LIGHT.color
                else Res.Theme.SOCIAL_ICON_BACKGROUND_DARK.color
            )
    }
}