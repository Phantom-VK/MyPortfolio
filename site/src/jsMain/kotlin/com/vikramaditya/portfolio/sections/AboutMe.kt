package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
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
import com.vikramaditya.portfolio.utils.theme.Type
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.fontFace
import com.vikramaditya.portfolio.utils.theme.textStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

/**
 * Responsive lead statement. Declarative breakpoints rather than
 * `rememberBreakpoint()` so this emits static CSS and costs no recomposition.
 */
val AboutLeadStyle = CssStyle {
    base { Modifier.textStyle(Type.Heading) }
    Breakpoint.MD { Modifier.textStyle(Type.Display) }
    Breakpoint.LG { Modifier.textStyle(Type.Hero) }
}

/**
 * Editorial layout: prose sits directly on the page ground with a hairline rule,
 * rather than inside a grey card. A card implies elevation, and this section has
 * none to communicate. Left-aligned, because a centred wall of body copy is
 * harder to read and every other section was already centred.
 */
@Composable
fun AboutMe() {
    val c = colors(ColorMode.current)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(leftRight = Space.lg, topBottom = Section.gapSm),
        horizontalAlignment = Alignment.Start
    ) {
        // A `<p>`, not a heading. `SectionTitle` already emits the section's H2,
        // and the hero owns the page's only H1, so a second H1 here would break
        // the document outline.
        P(
            attrs = AboutLeadStyle.toModifier()
                .margin(0.px)
                .fontFace(Font.DISPLAY)
                .color(c.textPrimary)
                .styleModifier { property("max-width", "20ch") }
                .toAttrs()
        ) {
            Text("I love computers and make them work for me.")
        }

        // Hairline rule instead of a card edge: it separates without boxing.
        Box(
            modifier = Modifier
                .margin(topBottom = Space.xl)
                .height(1.px)
                .width(96.px)
                .backgroundColor(c.borderStrong)
        )

        SpanText(
            text = "I like to develop all kinds of stuff on computers. I hate web development though, " +
                "(HTML, CSS, JS...). Currently working at Emplay Analytics as an Agentic AI Automation " +
                "Engineer, building enterprise AI copilots with LLMs and agentic workflows. My main focus " +
                "areas are AI/ML applications, and full stack development.",
            modifier = Modifier
                .textStyle(Type.Body)
                .fontFace(Font.BODY)
                .color(c.textSecondary)
                // Reading measure. Body copy past ~70 characters per line gets
                // hard to track back to the next line.
                .styleModifier { property("max-width", "68ch") }
        )
    }
}
