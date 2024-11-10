package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.GoogleCardStyle
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.dom.H1


@Composable
fun SkillCard(
    title: String,
    description: String,
    colorMode: ColorMode,
    breakpoint: Breakpoint
) {
    Box(
        modifier = GoogleCardStyle.toModifier()
            .then(Modifier
                .fillMaxWidth()
                .padding(2.em)

            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            H1 {
                SpanText(
                    text = title,
                    modifier = Modifier
                        .fontFamily(Res.String.ROBOTO_CONDENSED)
                        .fontWeight(FontWeight.Bold)
                        .textAlign(
                            if (breakpoint <= Breakpoint.SM) TextAlign.Center
                            else TextAlign.Start
                        )
                        .color(
                            if(colorMode.isLight) Res.Theme.PROJECT_TITLE_LIGHT.color
                            else Res.Theme.PROJECT_TITLE_DARK.color
                            ))

            }

            SpanText(
                text = description,
                modifier = Modifier
                    .fontFamily(Res.String.ROBOTO_REGULAR)
                    .lineHeight(1.5)
                    .fontSize(1.cssRem)
                    .color(
                        if (colorMode == ColorMode.LIGHT) Res.Theme.BODY_TEXT_DARK.color
                        else Res.Theme.BODY_TEXT_LIGHT.color
                    )
            )
        }
    }
}


