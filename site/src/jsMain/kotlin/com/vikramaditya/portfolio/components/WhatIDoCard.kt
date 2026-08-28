package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
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
import com.vikramaditya.portfolio.utils.theme.Font
import com.vikramaditya.portfolio.utils.theme.Space
import com.vikramaditya.portfolio.utils.theme.Type
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.fontFace
import com.vikramaditya.portfolio.utils.theme.textStyle
import org.jetbrains.compose.web.dom.Div

@Composable
fun WhatIDoCard(
    iconImage: String,
    description: String,
    modifier: Modifier = Modifier,
    backContent: @Composable () -> Unit = {},
) {
    val c = colors(ColorMode.current)

    // `tabIndex` and `ariaLabel` live here rather than in `CardStyle`: Kobweb
    // rejects attribute modifiers inside a `CssStyle` block at runtime.
    Div(
        attrs = CardStyle.toModifier()
            .then(modifier)
            .tabIndex(0)
            .ariaLabel(description)
            .toAttrs()
    ) {
        Div(
            attrs = CardInnerStyle.toModifier()
                .classNames("card-inner")
                .toAttrs()
        ) {
            Div(attrs = CardFrontStyle.toModifier().toAttrs()) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(Space.xl),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ) {
                    Image(width = 44, height = 44, src = iconImage, alt = "")

                    SpanText(
                        text = description,
                        modifier = Modifier
                            .textAlign(TextAlign.Start)
                            .fontFace(Font.DISPLAY)
                            .textStyle(Type.Title)
                            .color(c.textPrimary)
                    )
                }
            }

            Div(attrs = CardBackStyle.toModifier().toAttrs()) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(Space.xl),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    backContent()
                }
            }
        }
    }
}
