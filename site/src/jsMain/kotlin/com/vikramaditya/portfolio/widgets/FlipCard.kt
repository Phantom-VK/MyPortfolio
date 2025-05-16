package com.vikramaditya.portfolio.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.vikramaditya.portfolio.styles.CardBackStyle
import com.vikramaditya.portfolio.styles.CardFrontStyle
import com.vikramaditya.portfolio.styles.CardInnerStyle
import com.vikramaditya.portfolio.styles.CardStyle
import org.jetbrains.compose.web.dom.Div

@Composable
fun FlipCard(front: @Composable () -> Unit, back: @Composable () -> Unit) {
    Div(
        attrs = CardStyle.toModifier()
            .toAttrs()
    ) {
        Div(
            attrs = CardInnerStyle.toModifier()
                .classNames("card-inner")
                .toAttrs()
        ) {
            Div(
                attrs = CardFrontStyle.toModifier().toAttrs()
            ) {
                front()
            }

            Div(
                attrs = CardBackStyle.toModifier().toAttrs()
            ) {
                back()
            }
        }
    }
}
