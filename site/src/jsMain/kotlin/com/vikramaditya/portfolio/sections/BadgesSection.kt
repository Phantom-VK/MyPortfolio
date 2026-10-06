package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.BadgeGridStyle
import com.vikramaditya.portfolio.styles.BadgeImageBoxStyle
import com.vikramaditya.portfolio.styles.BadgeImageStyle
import com.vikramaditya.portfolio.styles.BadgeLabelStyle
import com.vikramaditya.portfolio.styles.BadgeTileStyle
import com.vikramaditya.portfolio.utils.Badge
import com.vikramaditya.portfolio.utils.BadgesData
import com.vikramaditya.portfolio.utils.theme.Font
import com.vikramaditya.portfolio.utils.theme.Section
import com.vikramaditya.portfolio.utils.theme.Space
import com.vikramaditya.portfolio.utils.theme.Type
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.fontFace
import com.vikramaditya.portfolio.utils.theme.textStyle
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img

/**
 * Job simulations, skill-test badges, and short-course completions — lighter
 * credentials than [CertificationsData], shown in a plain wrapping grid
 * instead of competing with the Certifications carousel for attention.
 */
@Composable
fun BadgesSection() {
    val c = colors(ColorMode.current)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(leftRight = Space.lg, topBottom = Section.gapSm),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (BadgesData.items.isEmpty()) return@Column

        Div(attrs = BadgeGridStyle.toModifier().toAttrs { attr("role", "list") }) {
            BadgesData.items.forEach { badge -> BadgeTile(badge, c) }
        }
    }
}

@Composable
private fun BadgeTile(badge: Badge, c: com.vikramaditya.portfolio.utils.theme.ThemeColors) {
    Div(attrs = BadgeTileStyle.toModifier().toAttrs { attr("role", "listitem") }) {
        Div(attrs = BadgeImageBoxStyle.toModifier().toAttrs()) {
            Img(
                src = badge.imageUrl,
                attrs = BadgeImageStyle.toModifier().toAttrs {
                    attr("alt", "${badge.issuer} — ${badge.label}")
                    attr("loading", "lazy")
                    attr("decoding", "async")
                    badge.intrinsicWidth?.let { attr("width", it.toString()) }
                    badge.intrinsicHeight?.let { attr("height", it.toString()) }
                }
            )
        }

        SpanText(
            badge.issuer,
            modifier = Modifier
                .margin(top = Space.sm)
                .textStyle(Type.Micro)
                .fontFace(Font.DISPLAY)
                .color(c.accent)
        )
        SpanText(
            badge.label,
            modifier = Modifier
                .margin(top = Space.xs)
                .textStyle(Type.Micro)
                .fontFace(Font.BODY)
                .color(c.textSecondary)
                .then(BadgeLabelStyle.toModifier())
        )
    }
}
