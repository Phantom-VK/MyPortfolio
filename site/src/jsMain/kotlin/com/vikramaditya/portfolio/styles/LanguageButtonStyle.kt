package com.vikramaditya.portfolio.styles

import com.varabyte.kobweb.compose.css.BoxShadow
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.scaleY
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px

val LanguageButtonStyle = CssStyle {
    base {
        Modifier
            .transition(
                Transition.of(property = "scaleY", duration = 300.ms),
            )
    }
    hover {
        Modifier
            .boxShadow(
                BoxShadow.of(
                    color = when(Res.Selected.LANGUAGE){
                        "language-python" -> Res.Theme.PRIMARY_BUTTON.color
                        "language-java" -> Res.Theme.JavaOrange.color

                        "language-kotlin" -> Res.Theme.GoogleBlue.color

                        else -> Res.Theme.GoogleBlue.color

                    }
                )


            )
    }
}
