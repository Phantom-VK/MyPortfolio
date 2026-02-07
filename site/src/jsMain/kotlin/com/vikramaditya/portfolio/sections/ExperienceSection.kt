package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.silk.components.text.SpanText
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun ExperienceSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(leftRight = 5.percent, topBottom = 1.percent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExperienceCard(
            role = "Associate Software Engineer (Intern)",
            company = "Better Software",
            period = "Oct 2025 – Present",
            highlights = listOf(
                "Architected CI/CD pipelines with GitHub Actions + Docker to automate build, test, and deploy.",
                "Built multi-cloud release workflows across AWS and DigitalOcean with environment-specific rollouts.",
                "Hardened delivery with monitoring hooks and automated checks for production safety."
            )
        )
    }
}

@Composable
private fun ExperienceCard(
    role: String,
    company: String,
    period: String,
    highlights: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Res.Theme.GREY_BACKGROUND.color)
            .borderRadius(Res.Dimens.BORDER_RADIUS.px)
            .padding(24.px)
    ) {
        SpanText(
            text = "$role · $company",
            modifier = Modifier
                .fontWeight(FontWeight.Bold)
                .fontSize(1.2.em)
        )
        SpanText(
            text = period,
            modifier = Modifier
                .margin(top = 6.px)
                .fontWeight(FontWeight.Medium)
                .color(Res.Theme.THEME_GREEN.color)
        )

        Column(
            modifier = Modifier
                .margin(top = 12.px)
                .fillMaxWidth()
        ) {
            highlights.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .margin(bottom = 6.px),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SpanText(
                        text = "•",
                        modifier = Modifier
                            .width(12.px)
                            .textAlign(TextAlign.Center)
                    )
                    SpanText(
                        text = item,
                        modifier = Modifier
                            .margin(left = 8.px)
                    )
                }
            }
        }
    }
}
