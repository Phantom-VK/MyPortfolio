package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.utils.theme.Font
import com.vikramaditya.portfolio.utils.theme.Section
import com.vikramaditya.portfolio.utils.theme.Space
import com.vikramaditya.portfolio.utils.theme.ThemeColors
import com.vikramaditya.portfolio.utils.theme.Type
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.fontFace
import com.vikramaditya.portfolio.utils.theme.textStyle
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

private data class Skill(val ordinal: String, val label: String, val value: String)

private val skills = listOf(
    Skill("01", "PYTHON", "80"),
    Skill("02", "JAVA", "70"),
    Skill("03", "KOTLIN", "50"),
)

/**
 * Numeric grid. One column per language, stacked on phones, with the lead
 * language given a wider track and a larger numeral so the row is not three
 * identical cells.
 *
 * Deliberately no progress bars: a filled track promises a measured scale that
 * a self-assessed percentage does not have, and three identical bars is the
 * single most templated shape in a developer portfolio.
 */
val SkillGridStyle = CssStyle {
    base {
        Modifier.fillMaxWidth().styleModifier {
            property("display", "grid")
            property("grid-template-columns", "1fr")
            property("align-items", "end")
            property("gap", "32px")
        }
    }
    Breakpoint.MD {
        Modifier.styleModifier {
            property("grid-template-columns", "1.4fr 1fr 1fr")
            property("gap", "24px")
        }
    }
}

/** The lead numeral, one step above the others at every breakpoint. */
val SkillValueLeadStyle = CssStyle {
    base { Modifier.textStyle(Type.Display) }
    Breakpoint.MD { Modifier.textStyle(Type.Hero) }
}

val SkillValueStyle = CssStyle {
    base { Modifier.textStyle(Type.Heading) }
    Breakpoint.MD { Modifier.textStyle(Type.Display) }
}

@Composable
fun MySkillsSection() {
    val c = colors(ColorMode.current)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(leftRight = Space.lg, topBottom = Section.gapSm),
        horizontalAlignment = Alignment.Start
    ) {
        // A single rule above the group ties the columns together without
        // boxing each one, and echoes the experience rail.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.px)
                .margin(bottom = Space.xl)
                .backgroundColor(c.border)
        )

        Div(attrs = SkillGridStyle.toModifier().toAttrs()) {
            skills.forEachIndexed { index, skill ->
                SkillEntry(skill = skill, colors = c, isLead = index == 0)
            }
        }
    }
}

@Composable
private fun SkillEntry(skill: Skill, colors: ThemeColors, isLead: Boolean) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        SpanText(
            text = skill.ordinal,
            modifier = Modifier
                .fontFace(Font.ACCENT)
                .textStyle(Type.Small)
                .color(colors.textSecondary)
        )

        // Numeral and unit share a baseline. The percent sign is deliberately
        // quieter: the number is the information, the sign is only its unit.
        Row(
            modifier = Modifier.margin(top = Space.sm),
            verticalAlignment = Alignment.Bottom
        ) {
            SpanText(
                text = skill.value,
                modifier = (if (isLead) SkillValueLeadStyle else SkillValueStyle)
                    .toModifier()
                    .fontFace(Font.DISPLAY)
                    .color(colors.textPrimary)
            )
            SpanText(
                text = "%",
                modifier = Modifier
                    .margin(left = 2.px)
                    .textStyle(Type.Title)
                    .fontFace(Font.DISPLAY)
                    .color(colors.textSecondary)
            )
        }

        SpanText(
            text = skill.label,
            modifier = Modifier
                .margin(top = Space.sm)
                .textStyle(Type.Small)
                .fontFace(Font.DISPLAY)
                .letterSpacing(0.08.em)
                .color(colors.textPrimary)
        )
    }
}
