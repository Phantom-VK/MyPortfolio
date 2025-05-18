package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.framework.annotations.DelicateApi
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

data class Skill(val label: String, val percentage: String)

@OptIn(DelicateApi::class)
@Composable
fun MySkillsSection() {
    val breakpoint = rememberBreakpoint()
    val skills = listOf(
        Skill("PYTHON", "80%"),
        Skill("JAVA", "70%"),
        Skill("Kotlin", "60%"),
        Skill("C/C++", "50%")
    )


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.percent)
            .margin(top = when(breakpoint){
                Breakpoint.SM -> 10.cssRem
                else -> 2.cssRem
            })
            .styleModifier {
                property("text-align", "center")
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.px)
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
