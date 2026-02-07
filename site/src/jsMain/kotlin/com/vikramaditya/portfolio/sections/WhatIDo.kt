package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Height
import com.varabyte.kobweb.compose.css.JustifyItems
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignContent
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.justifyItems
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.framework.annotations.DelicateApi
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.WhatIDoCard
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.widgets.SectionTitle
import org.jetbrains.compose.web.css.AlignContent
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@OptIn(DelicateApi::class)
@Composable
fun WhatIDo() {
    val colorMode by ColorMode.currentState
    val breakpoint = rememberBreakpoint()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.cssRem)
            .margin(

                bottom = when (breakpoint) {
                    Breakpoint.SM -> 10.cssRem
                    Breakpoint.MD -> 4.cssRem
                    Breakpoint.XL -> 4.cssRem
                    else -> 10.cssRem
                }
            ),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SimpleGrid(
            modifier = Modifier
                .fillMaxWidth()
                .gap(10.percent)
                .padding(leftRight = 2.cssRem)
                .justifyItems(JustifyItems.Center),
            numColumns = numColumns(
                base = 1,
                sm = 1,
                md = 2,
                lg = 3
            )
        ) {
            WhatIDoCard(
                iconImage = Res.Icon.HEXAWEB,
                description = "Full-Stack Systems",
                backContent = {
                    SpanText(
                        text = "Design and ship web backends and UIs end-to-end with Kotlin/Java and Python APIs, focusing on reliable data flows and clean UX.",
                        modifier = Modifier
                            .textAlign(TextAlign.Center)
                            .fontFamily("JetBrains Mono")
                            .fontSize(if (breakpoint >= Breakpoint.MD) 1.3.em else 1.1.em)
                            .color(if (colorMode.isDark) Res.Theme.GLASS_BOX_BORDER_COLOR_LIGHT.color else Colors.White)
                    )
                }
            )

            WhatIDoCard(
                iconImage = Res.Icon.DEV,
                description = "AI / ML & LLM Apps",
                backContent = {
                    SpanText(
                        text = "Build and deploy LLM-powered assistants and ML pipelines (Transformers, LangGraph, scikit-learn) with real-time APIs.",
                        modifier = Modifier
                            .textAlign(TextAlign.Center)
                            .fontFamily("JetBrains Mono")
                            .fontSize(if (breakpoint >= Breakpoint.MD) 1.3.em else 1.1.em)
                            .color(if (colorMode.isDark) Res.Theme.GLASS_BOX_BORDER_COLOR_LIGHT.color else Colors.White)
                    )
                }
            )

            WhatIDoCard(
                iconImage = Res.Icon.CUBOID,
                description = "DevOps & Cloud",
                backContent = {
                    SpanText(
                        text = "Automate CI/CD with GitHub Actions + Docker; deploy to AWS (EC2/S3/ECR) and manage multi-environment releases.",
                        modifier = Modifier
                            .textAlign(TextAlign.Center)
                            .fontFamily("JetBrains Mono")
                            .fontSize(if (breakpoint >= Breakpoint.MD) 1.3.em else 1.1.em)
                            .color(if (colorMode.isDark) Res.Theme.GLASS_BOX_BORDER_COLOR_LIGHT.color else Colors.White)
                    )
                }
            )
        }
    }
}
