package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Height
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.WhatIDoCard
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.widgets.SectionTitle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.percent

@Composable
fun WhatIDo(){

    val colorMode by ColorMode.currentState

    Column(
        modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally){

        SimpleGrid(
            numColumns = numColumns(base = 1, sm = 1, md = 3),
            modifier = Modifier.fillMaxWidth()
                .padding(5.percent)
                .height(Height.FitContent)
                .id("what_i_do_grid")
        ){
            WhatIDoCard(
                iconImage = Res.Icon.HEXAWEB,
                description = "Software Development",
                backContent = {
                    SpanText(
                        text = "Developed many softwares with different tech stack",
                        modifier = Modifier
                            .textAlign(TextAlign.Center)
                            .fontFamily("JetBrains Mono")
                            .fontSize(1.3.em)
                            .color(
                                if (colorMode.isDark)
                                    Res.Theme.GLASS_BOX_BORDER_COLOR_LIGHT.color
                                else Colors.White
                            )
                    )
                }
            )
            Spacer()
            WhatIDoCard(
                iconImage = Res.Icon.DEV,
                description = "App Development",
                backContent = {
                    SpanText(
                        text = "Developed many android apps using kotlin and jetpack compose",
                        modifier = Modifier
                            .textAlign(TextAlign.Center)
                            .fontFamily("JetBrains Mono")
                            .fontSize(1.3.em)
                            .color(
                                if (colorMode.isDark)
                                    Res.Theme.GLASS_BOX_BORDER_COLOR_LIGHT.color
                                else Colors.White
                            )
                    )
                }
            )
            Spacer()
            WhatIDoCard(
                iconImage = Res.Icon.CUBOID,
                description = "UI/UX And Software Design",
                backContent = {
                    SpanText(
                        text = "Created many designs in figma and made software architectures",
                        modifier = Modifier
                            .textAlign(TextAlign.Center)
                            .fontFamily("JetBrains Mono")
                            .fontSize(1.3.em)
                            .color(
                                if (colorMode.isDark)
                                    Res.Theme.GLASS_BOX_BORDER_COLOR_LIGHT.color
                                else Colors.White
                            )
                    )
                }
            )
        }
    }


}