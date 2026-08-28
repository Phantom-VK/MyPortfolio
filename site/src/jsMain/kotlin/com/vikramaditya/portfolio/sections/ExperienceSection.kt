package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.utils.theme.Font
import com.vikramaditya.portfolio.utils.theme.Section
import com.vikramaditya.portfolio.utils.theme.Space
import com.vikramaditya.portfolio.utils.theme.ThemeColors
import com.vikramaditya.portfolio.utils.theme.Type
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.fontFace
import com.vikramaditya.portfolio.utils.theme.textStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

private data class Experience(
    val role: String,
    val company: String,
    val period: String,
    val location: String,
    val highlights: List<String>,
)

private val experiences = listOf(
    Experience(
        role = "Agentic AI Automation Intern",
        company = "Emplay Analytics Inc",
        period = "Jun 2026 - Present · Remote",
        location = "Dublin, California, USA",
        highlights = listOf(
            "Building and testing AI workflows using LLMs, RAG, and agentic orchestration frameworks.",
            "Supporting enterprise client delivery across Sales, Procurement, and Marketing use cases.",
            "Prompt engineering, data validation with SQL, and UAT for AI copilot deployments.",
        ),
    ),
    Experience(
        role = "Associate Software Engineer (Intern)",
        company = "Better Software",
        period = "Oct 2025 - Feb 2026",
        location = "Rajasthan, India",
        highlights = listOf(
            "Architected CI/CD pipelines with GitHub Actions + Docker to automate build, test, and deploy.",
            "Built multi-cloud release workflows across AWS and DigitalOcean with environment-specific rollouts.",
            "Hardened delivery with monitoring hooks and automated checks for production safety.",
        ),
    ),
)

/**
 * Timeline rail rather than stacked cards. The continuous hairline carries the
 * sense of a sequence, which two separate boxes did not, and it gives this
 * section a shape no other section on the page uses.
 */
@Composable
fun ExperienceSection() {
    val c = colors(ColorMode.current)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(leftRight = Space.lg, topBottom = Section.gapSm),
        horizontalAlignment = Alignment.Start
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                // The rail itself. Entries hang off it and the node dots sit on it.
                .borderLeft(1.px, LineStyle.Solid, c.border)
        ) {
            experiences.forEachIndexed { index, experience ->
                ExperienceEntry(
                    experience = experience,
                    colors = c,
                    isLast = index == experiences.lastIndex,
                )
            }
        }
    }
}

@Composable
private fun ExperienceEntry(
    experience: Experience,
    colors: ThemeColors,
    isLast: Boolean,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .position(Position.Relative)
            .padding(left = Space.xl, bottom = if (isLast) 0.px else Section.gapSm)
    ) {
        // Node. Pulled half its width to the left so it straddles the rail.
        Box(
            modifier = Modifier
                .position(Position.Absolute)
                .left((-5).px)
                .top(Space.sm)
                .size(9.px)
                .borderRadius(50.percent)
                .backgroundColor(colors.accent)
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            SpanText(
                text = experience.role,
                modifier = Modifier
                    .textStyle(Type.Title)
                    .fontFace(Font.DISPLAY)
                    .color(colors.textPrimary)
            )

            SpanText(
                text = experience.company,
                modifier = Modifier
                    .margin(top = Space.xs)
                    .textStyle(Type.Body)
                    .fontFace(Font.BODY)
                    .color(colors.accent)
            )

            // Meta row: mono, small, quiet. Wraps rather than overlapping on narrow screens.
            Row(
                modifier = Modifier
                    .margin(top = Space.sm)
                    .fillMaxWidth()
                    .styleModifier {
                        property("flex-wrap", "wrap")
                        property("gap", "4px 16px")
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                SpanText(
                    text = experience.period,
                    modifier = Modifier
                        .textStyle(Type.Micro)
                        .fontFace(Font.BODY)
                        .color(colors.textSecondary)
                )
                SpanText(
                    text = experience.location,
                    modifier = Modifier
                        .textStyle(Type.Micro)
                        .fontFace(Font.BODY)
                        .color(colors.textSecondary)
                )
            }

            Column(modifier = Modifier.margin(top = Space.lg).fillMaxWidth()) {
                experience.highlights.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .margin(bottom = Space.sm),
                        verticalAlignment = Alignment.Top
                    ) {
                        // A short accent rule reads as a list marker without
                        // needing a bullet glyph in the text stream.
                        Box(
                            modifier = Modifier
                                .margin(top = 10.px, right = Space.md)
                                .width(10.px)
                                .height(1.px)
                                .flexShrink(0)
                                .backgroundColor(colors.borderStrong)
                        )
                        SpanText(
                            text = item,
                            modifier = Modifier
                                .textStyle(Type.Small)
                                .fontFace(Font.BODY)
                                .color(colors.textSecondary)
                        )
                    }
                }
            }
        }
    }
}
