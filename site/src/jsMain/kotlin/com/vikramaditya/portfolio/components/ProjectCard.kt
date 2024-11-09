package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.GoogleCardStyle
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H1

@Composable
fun ProjectCard(
    title: String,
    description: String,
    tech: String,
    colorMode: ColorMode
) {
    Box(
        GoogleCardStyle.toModifier()
            .then(Modifier
                .fillMaxWidth()
                .padding(2.em)

            )

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
        ){

                H1 {
                    SpanText(
                        modifier = Modifier
                            .color(Res.Theme.GoogleBlue.color)
                            .textAlign(TextAlign.Center),
                        text = title
                    )
                }

            SpanText(
                modifier = Modifier
                    .margin(bottom = 1.em)
                    .lineHeight(1.5)
                    .fontSize(2.cssRem),
                text = description
            )
            SpanText(
                modifier = Modifier
                    .color(if (colorMode.isLight) Colors.DarkGray else Colors.LightGray)
                    .fontSize(1.cssRem),
                text = tech
            )

            }

        }
    }

