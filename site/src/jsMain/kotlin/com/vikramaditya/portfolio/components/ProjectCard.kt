package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import com.vikramaditya.portfolio.styles.MatrixGreen
import com.vikramaditya.portfolio.styles.ProjectCardSTyle
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Img

val MatrixPurple = Color.rgba(168, 158, 201, 0.5f)



@Composable
fun ProjectCard(
    title: String,
    description: String,
    imageUrl: String,
    price: String,
    duration: String,
    creator: String,
    creatorImage: String
) {
    Box(
        modifier = ProjectCardSTyle.toModifier()
    ) {
        Column(
            modifier = Modifier.width(100.percent),
            horizontalAlignment = Alignment.Start
        ) {
            Img(
                src = imageUrl,
                attrs = Modifier
                    .width(100.percent)
                    .height(250.px)
                    .objectFit(ObjectFit.Cover)
                    .borderRadius(0.5.cssRem)
                    .toAttrs()
            )

            SpanText(title, Modifier.margin(top = 8.px).fontWeight(FontWeight.Bold).color(MatrixGreen))
            SpanText(description, Modifier.margin(top = 4.px).color(MatrixPurple))

            Row(
                Modifier
                    .margin(top = 8.px)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SpanText("◘", Modifier.margin(right = 8.px).color(MatrixGreen))
                    SpanText(price, Modifier.color(MatrixGreen).fontWeight(FontWeight.Bold))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SpanText("◷", Modifier.margin(right = 8.px).color(MatrixPurple))
                    SpanText(duration, Modifier.color(MatrixPurple))
                }
            }

            Hr1()

            Row(
                modifier = Modifier.margin(top = 4.px),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(right = 8.px)
                        .border(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.13f))
                        .boxShadow(inset = true, blurRadius = 4.px, color = Color.rgba(0, 0, 0, 0.67f))
                        .borderRadius(50.percent)
                        .padding(4.px)
                ) {
                    Img(
                        src = creatorImage,
                        attrs = Modifier
                            .size(32.px)
                            .borderRadius(50.percent)
                            .border(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.13f))
                            .objectFit(ObjectFit.Cover)
                            .toAttrs()
                    )
                }

                SpanText(buildString {
                    append("Creation of ")
                    append(creator)
                }, Modifier.color(MatrixPurple))
            }
        }
    }
}
@Composable
fun Hr1() {
    Box(
        modifier = Modifier
            .margin(top = 8.px, bottom = 8.px)
            .height(1.px)
            .width(100.percent)
            .backgroundColor(Res.Theme.MATRIX_GLOW.color)
    )
}

