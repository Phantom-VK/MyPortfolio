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

@OptIn(DelicateApi::class)
@Composable
fun WhatIDo() {
    val colorMode by ColorMode.currentState
    val breakpoint = rememberBreakpoint()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(topBottom = 2.cssRem),
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
                description = "Software Development",
                backContent = {
                    SpanText(
                        text = "Developed software projects spanning domains like utilities, automation, and educational tools.",
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
                description = "App Development",
                backContent = {
                    SpanText(
                        text = "Engineered Android apps using Kotlin and Jetpack Compose with an emphasis on clean architecture and UI performance.",
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
                description = "UI/UX and Software Design",
                backContent = {
                    SpanText(
                        text = "Designed intuitive UIs in Figma and planned system-level architecture for seamless development.",
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
