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
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

private data class Education(
    val degree: String,
    val institution: String,
    val period: String,
    val detail: String,
)

private val education = listOf(
    Education(
        degree = "B.Tech, Information Technology",
        institution = "Shri Guru Gobind Singhji Institute of Engineering and Technology Government Autonomous College",
        period = "2022 - 2026",
        detail = "CGPA: 8.04/10 CET: 96%ile",
    ),
)

/**
 * Same timeline-rail shape as ExperienceSection, kept as its own section
 * rather than folded in so degree entries and role entries never mix in
 * one list.
 */
@Composable
fun EducationSection() {
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
                .borderLeft(1.px, LineStyle.Solid, c.border)
        ) {
            education.forEachIndexed { index, entry ->
                EducationEntry(
                    education = entry,
                    colors = c,
                    isLast = index == education.lastIndex,
                )
            }
        }
    }
}

@Composable
private fun EducationEntry(
    education: Education,
    colors: ThemeColors,
    isLast: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .margin(bottom = if (isLast) 0.px else Section.gapSm),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .flexShrink(0)
                .margin(top = Space.sm, right = Space.lg)
                .size(9.px)
                .borderRadius(50.percent)
                .backgroundColor(colors.accent)
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            SpanText(
                text = education.degree,
                modifier = Modifier
                    .textStyle(Type.Title)
                    .fontFace(Font.DISPLAY)
                    .color(colors.textPrimary)
            )

            SpanText(
                text = education.institution,
                modifier = Modifier
                    .margin(top = Space.xs)
                    .textStyle(Type.Body)
                    .fontFace(Font.BODY)
                    .color(colors.accent)
            )

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
                    text = education.period,
                    modifier = Modifier
                        .textStyle(Type.Micro)
                        .fontFace(Font.BODY)
                        .color(colors.textSecondary)
                )
                SpanText(
                    text = education.detail,
                    modifier = Modifier
                        .textStyle(Type.Micro)
                        .fontFace(Font.BODY)
                        .color(colors.textSecondary)
                )
            }
        }
    }
}
