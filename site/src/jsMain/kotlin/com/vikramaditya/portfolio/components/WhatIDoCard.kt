package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.CardBackStyle
import com.vikramaditya.portfolio.styles.CardFrontStyle
import com.vikramaditya.portfolio.styles.CardInnerStyle
import com.vikramaditya.portfolio.styles.CardStyle
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.dom.Div


@Composable
fun WhatIDoCard(
    iconImage: String,
    description: String,
    backContent: @Composable () -> Unit = {
        SpanText("Coming soon...", Modifier.color(Colors.White))
    }
) {
    val colorMode = ColorMode.current

    Div(
        attrs = CardStyle.toModifier()
            .toAttrs()
    ) {
        Div(
            attrs = CardInnerStyle.toModifier()
                .classNames("card-inner")
                .toAttrs()
        ) {
            // Front side content
            Div(
                attrs = CardFrontStyle.toModifier().toAttrs()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.percent),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ) {
                    Image(
                        width = 50,
                        height = 50,
                        src = iconImage
                    )

                    SpanText(
                        text = description,
                        modifier = Modifier
                            .textAlign(TextAlign.Start)
                            .fontFamily("VT323")
                            .fontSize(2.5.em)
                            .color(
                                if (colorMode.isDark)
                                    Res.Theme.GLASS_BOX_BORDER_COLOR_LIGHT.color
                                else Colors.White
                            )
                    )
                }
            }

            // Back side content
            Div(
                attrs = CardBackStyle.toModifier().toAttrs()
            ) {
                backContent()
            }
        }
    }
}
