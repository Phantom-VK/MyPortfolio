package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import androidx.compose.web.events.SyntheticMouseEvent
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.JustifyItems
import com.varabyte.kobweb.compose.css.MixBlendMode
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.mixBlendMode
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.MatrixGreen
import com.vikramaditya.portfolio.styles.ProjectCardSTyle
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Img



@Composable
fun ProjectCard(
    title: String,
    description: String,
    imageUrl: String,
    mainTechStack: String,
    otherTechStack: String,
    iconsList: List<String>,
    onClick: (SyntheticMouseEvent) -> Unit
) {
    val colorMode = ColorMode.current
    Box(
        modifier = ProjectCardSTyle.toModifier()
            .onClick { evt -> onClick(evt) }
    ) {
        Column(
            modifier = Modifier
                .width(100.percent)
                .fillMaxHeight()
                .padding(1.cssRem),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Img(
                src = imageUrl,
                attrs = Modifier
                    .width(100.percent)
                    .maxHeight(250.px)
                    .objectFit(ObjectFit.Cover)
                    .borderRadius(0.5.cssRem)
                    .toAttrs()
            )

            SpanText(
                title,
                Modifier
                    .margin(top = 8.px)
                    .fontFamily("Share Tech Mono")
                    .fontWeight(FontWeight.Bold)
                    .color(MatrixGreen)
            )

            SpanText(
                description,
                Modifier
                    .margin(top = 4.px)
                    .fontFamily("VT323")
                    .color(if (colorMode.isDark)
                        Res.Theme.GLASS_BOX_BORDER_COLOR_LIGHT.color
                    else
                        Colors.White)
            )
            Spacer()

            Row(
                Modifier
                    .margin(top = 8.px)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SpanText(
                    mainTechStack,
                    Modifier
                        .color(MatrixGreen)
                        .fontFamily("VT323")
                        .fontWeight(FontWeight.Bold)
                )
                SpanText(
                    otherTechStack,
                    Modifier
                        .color(if (colorMode.isDark)
                            Res.Theme.GLASS_BOX_BORDER_COLOR_LIGHT.color
                        else
                            Colors.White)
                        .fontFamily("VT323")
                )
            }


            CustomHorizontalDivider()

            // Logo Grid
            SimpleGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .justifyItems(JustifyItems.Center)
                    .padding(top = 8.px),
                numColumns = numColumns(base = 3, sm = 4, md = 5)
            ) {
                iconsList.forEach { icon ->
                    Image(
                        src = icon,
                        modifier = Modifier
                            .size(24.px)
                            .mixBlendMode(MixBlendMode.Normal)
                    )
                }
            }

        }




    }
}


@Composable
fun CustomHorizontalDivider() {
    Box(
        modifier = Modifier
            .margin(top = 8.px, bottom = 8.px)
            .height(1.px)
            .width(100.percent)
            .backgroundColor(Res.Theme.MATRIX_GLOW.color)
    )
}

