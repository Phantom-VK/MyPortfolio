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
import com.vikramaditya.portfolio.widgets.SectionTitle
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


        Spacer()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .maxWidth(900.px)
                .padding(16.px)
                .borderRadius(7.px)
                .background(color = Res.Theme.GREY_BACKGROUND.color)
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
                                .fontFamily("Share Tech Mono")
                                .fontWeight(FontWeight.Bold)
                                .color(Color.rgba(255, 255, 255, 0.8f))
                        )
                        SpanText(
                            skill.label,
                            modifier = Modifier
                                .fontSize(14.px)
                                .fontFamily("DM Sans")
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
