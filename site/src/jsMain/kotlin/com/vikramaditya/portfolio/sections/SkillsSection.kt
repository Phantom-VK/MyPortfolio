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
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.SkillCard
import org.jetbrains.compose.web.css.AlignContent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Text

@Composable
fun SkillSection(colorMode: ColorMode, breakpoint: Breakpoint) {
    Section(
        Modifier
            .fillMaxWidth()
            .padding(32.px)
            .alignContent(AlignContent.Center)
            .backgroundColor(if (colorMode.isLight) Colors.WhiteSmoke else Colors.DarkSlateGray)
            .toAttrs()
    ) {
        SpanText(
            modifier = Modifier
                .margin(bottom = 24.px)
                .fontSize(36.px)
                .fontWeight(FontWeight.Bold)
                .textAlign(TextAlign.Center),
            text = "Technical Skills"
        )

        SimpleGrid(
            numColumns = numColumns(base = 1,sm = 2,  md = 3),
            modifier = Modifier.fillMaxWidth().gap(24.px)
        ) {
            SkillCard(
                title = "Programming",
                description = "Python (Advanced), Java (Intermediate), Kotlin (Intermediate), OOP",
                colorMode = colorMode,
                breakpoint = breakpoint
            )
            SkillCard(
                title = "Development",
                description = "Android Development, Jetpack Compose, Kobweb, Firebase",
                colorMode = colorMode,
                breakpoint = breakpoint
            )
            SkillCard(
                title = "Tools",
                description = "Android Studio, Git, GitHub, PyCharm, IntelliJ IDEA, Figma, VSCode",
                colorMode = colorMode,
                breakpoint = breakpoint
            )
        }
    }
}
