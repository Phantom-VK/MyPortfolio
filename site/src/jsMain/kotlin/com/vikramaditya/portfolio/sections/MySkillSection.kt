package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.px

data class Skill(val label: String, val percentage: String)

@Composable
fun MySkillsSection() {
    val skills = listOf(
        Skill("PYTHON", "80%"),
        Skill("JAVA", "70%"),
        Skill("Kotlin", "50%"),
        Skill("C/C++", "30%")
    )

    val colorMode = ColorMode.current

    Column(
        modifier = Modifier
            .id("my-skills")
            .fillMaxWidth()
            .padding(topBottom = 48.px)
            .styleModifier {
                property("text-align", "center")
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpanText(
            "My Skills",
            modifier = Modifier
                .fontSize(28.px)
                .fontWeight(FontWeight.Bold)
                .color(if (colorMode.isDark) Res.Theme.LIGHT_THEME_BACKGROUND.color else Res.Theme.DARK_THEME_BACKGROUND.color)
        )

        Spacer()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .maxWidth(900.px)
                .padding(16.px)
                .backgroundColor(
                    if (colorMode.isDark) Color.rgba(255, 255, 255, 0.05f)
                    else Color.rgba(0, 0, 0, 0.05f)
                )
                .borderRadius(8.px)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.px),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                skills.forEach { skill ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SpanText(
                            skill.percentage,
                            modifier = Modifier
                                .fontSize(32.px)
                                .fontWeight(FontWeight.Bold)
                                .color(Color.rgba(255, 255, 255, 0.8f))
                        )
                        SpanText(
                            skill.label,
                            modifier = Modifier
                                .fontSize(14.px)
                                .margin(top = 4.px)
                                .fontWeight(FontWeight.Medium)
                                .color(Color.rgb(0, 255, 128)) // neon green
                        )
                    }
                }
            }
        }
    }
}
