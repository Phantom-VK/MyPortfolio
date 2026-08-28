package com.vikramaditya.portfolio.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.SectionTitleStyle
import com.vikramaditya.portfolio.utils.theme.Font
import com.vikramaditya.portfolio.utils.theme.Section
import com.vikramaditya.portfolio.utils.theme.Space
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.fontFace
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text

/**
 * A section heading and its anchor target.
 *
 * No eyebrow label above it on purpose. A small uppercase category label over
 * every heading is the most templated pattern going, and the heading already
 * says what the section is.
 */
@Composable
fun SectionTitle(
    sectionTitleText: String,
    id: String
) {
    val c = colors(ColorMode.current)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(leftRight = Space.lg, top = Section.gapMd),
        horizontalAlignment = Alignment.Start
    ) {
        H2(
            attrs = SectionTitleStyle.toModifier()
                .id(id)
                .margin(0.px)
                .fontFace(Font.DISPLAY)
                .toAttrs()
        ) {
            Text(sectionTitleText)
        }

        // A short accent rule under the heading, in place of a full-width divider.
        Box(
            modifier = Modifier
                .margin(top = Space.md)
                .width(56.px)
                .height(2.px)
                .backgroundColor(c.signal)
        )
    }
}
