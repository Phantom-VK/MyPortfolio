package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.BackgroundColor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignContent
import com.varabyte.kobweb.compose.ui.modifiers.background
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
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.AlignContent
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Text

@Composable
fun SkillSection(colorMode: ColorMode, breakpoint: Breakpoint) {
    Section(
        Modifier
            .fillMaxWidth()
            .background(if (colorMode.isLight) Res.Theme.LIGHT_THEME_BACKGROUND.color else Res.Theme.DARK_THEME_BACKGROUND.color)
            .padding(32.px)
            .alignContent(AlignContent.Center)
            .toAttrs()
    ) {
        H1{
            SpanText(
                modifier = Modifier
                    .fontSize(2.cssRem)
                    .fontWeight(FontWeight.Bold),
                text = "Technical Skills"
            )
        }

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
