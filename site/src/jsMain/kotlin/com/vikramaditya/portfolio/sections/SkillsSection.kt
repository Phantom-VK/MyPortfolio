package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignContent
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.SkillCard
import org.jetbrains.compose.web.css.AlignContent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Text

@Composable
fun SkillSection(colorMode: ColorMode, breakpoint: Breakpoint){

    Section(
            Modifier
                .fillMaxWidth()
                .padding(32.px)
                .alignContent(AlignContent.Center)
                .backgroundColor(if (colorMode.isLight) Colors.WhiteSmoke else Colors.DarkSlateGray)
                .toAttrs()
        ) {
            H2(
                Modifier
                    .margin(bottom = 24.px)
                    .fontSize(36.px)
                    .fontWeight(FontWeight.Bold)
                    .textAlign(TextAlign.Center)
                    .toAttrs()
            ) { Text("Technical Skills") }

            SimpleGrid(numColumns = numColumns(3), modifier = Modifier.maxWidth(1000.px).gap(24.px)) {
                SkillCard(
                    "Programming",
                    "Python (Advanced), Java (Intermediate), Kotlin (Intermediate), OOP",
                    "fas fa-code",
                    colorMode,
                    breakpoint
                )
                SkillCard(
                    "Development",
                    "Android Development, Jetpack Compose, Kobweb, Firebase",
                    "fas fa-mobile-alt",
                    colorMode,
                    breakpoint
                )
                SkillCard(
                    "Tools",
                    "Android Studio, Git, GitHub, PyCharm, IntelliJ IDEA, Figma, VSCode",
                    "fas fa-tools",
                    colorMode,
                    breakpoint
                )
            }
        }
}