package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.GoogleCardStyle
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.I
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
    fun SkillCard(title: String,
                  description: String,
                  icon: String,
                  colorMode: ColorMode,
                  breakpoint: Breakpoint) {
        Box(
            GoogleCardStyle.toModifier()
                .padding(16.px)
        ) {
            Column(
                Modifier.padding(24.px),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpanText(
                    text = title,
                    modifier = Modifier
                        .margin(bottom = 12.px)
                        .fontFamily(Res.String.ROBOTO_CONDENSED)
                        .color(if (colorMode.isLight) Colors.Black else Colors.White)
                        .fontSize(36.px)
                        .fontWeight(FontWeight.Bold)
                        .textAlign(
                            if (breakpoint <= Breakpoint.SM) TextAlign.Center
                            else TextAlign.Start
                        )
                )

                SpanText(
                    text = description,
                    modifier = Modifier
                        .margin(bottom = 24.px)
                        .fontFamily(Res.String.ROBOTO_REGULAR)
                        .lineHeight(1.6)
                        .color(if (colorMode.isLight) Colors.Black else Colors.White)
                        .fontSize(16.px)
                )


            }
        }
    }
//                H3(
//                    Modifier
//                        .margin(bottom = 12.px)
//                        .fontSize(24.px)
//                        .fontWeight(FontWeight.Bold)
//                        .toAttrs()
//                ) { Text(title) }
//                P(
//                    Modifier
//                        .fontSize(16.px)
//                        .lineHeight(1.6)
//                        .color(if (colorMode.isLight) Colors.DarkGray else Colors.LightGray)
//                        .toAttrs()
//                ) { Text(description) }