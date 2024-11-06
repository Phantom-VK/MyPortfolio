package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextOverflow
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textOverflow
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.GoogleCardStyle
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.px

@Composable
fun ProjectCard(
    title: String,
    description: String,
    tech: String,
    colorMode: ColorMode
) {
    Box(
        GoogleCardStyle.toModifier()
            .fillMaxWidth()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(24.px),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                Modifier.margin(bottom = 16.px),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SpanText(
                    modifier = Modifier
                        .textOverflow(TextOverflow.Ellipsis)
                        .fontSize(24.px)
                        .color(Res.Theme.GoogleBlue.color)
                        .margin(right = 12.px),
                    text = title
                )

                SpanText(
                    modifier = Modifier
                        .textOverflow(TextOverflow.Ellipsis)
                        .fontSize(24.px)
                        .fontWeight(FontWeight.Bold),
                    text = title
                )
            }
            SpanText(
                modifier = Modifier
                    .textOverflow(TextOverflow.Ellipsis)
                    .margin(bottom = 12.px)
                    .fontSize(16.px)
                    .lineHeight(1.6),
                text = description
            )
            SpanText(
                modifier = Modifier
                    .textOverflow(TextOverflow.Ellipsis)
                    .color(if (colorMode.isLight) Colors.DarkGray else Colors.LightGray)
                    .fontSize(14.px),
                text = tech
            )
        }
    }
}
