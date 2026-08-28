package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.WhatIDoCard
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.utils.theme.Font
import com.vikramaditya.portfolio.utils.theme.Section
import com.vikramaditya.portfolio.utils.theme.Space
import com.vikramaditya.portfolio.utils.theme.Type
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.fontFace
import com.vikramaditya.portfolio.utils.theme.textStyle
import org.jetbrains.compose.web.dom.Div

/**
 * Asymmetric bento rather than three equal cards.
 *
 * Phones stack. At MD the lead card takes the full width above a pair. At LG it
 * becomes a tall left column beside two stacked wide cards, so the section has a
 * shape no other section on the page repeats.
 */
val WhatIDoGridStyle = CssStyle {
    base {
        Modifier.fillMaxWidth().styleModifier {
            property("display", "grid")
            property("grid-template-columns", "1fr")
            property("gap", "16px")
        }
    }
    Breakpoint.MD {
        Modifier.styleModifier {
            property("grid-template-columns", "repeat(2, 1fr)")
            property("gap", "20px")
        }
    }
    Breakpoint.LG {
        Modifier.styleModifier {
            property("grid-template-columns", "1fr 1.35fr")
            property("grid-template-rows", "200px 200px")
            property("gap", "24px")
        }
    }
}

/** Wide at MD, tall at LG. Everything else auto-places around it. */
val BentoLeadStyle = CssStyle {
    base { Modifier }
    Breakpoint.MD {
        Modifier.styleModifier { property("grid-column", "span 2") }
    }
    Breakpoint.LG {
        Modifier.styleModifier {
            property("grid-column", "1 / 2")
            property("grid-row", "1 / 3")
        }
    }
}

@Composable
fun WhatIDo() {
    val c = colors(ColorMode.current)

    @Composable
    fun back(text: String) {
        SpanText(
            text = text,
            modifier = Modifier
                .textAlign(TextAlign.Start)
                .fontFace(Font.BODY)
                .textStyle(Type.Small)
                .color(c.textSecondary)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(leftRight = Space.lg, topBottom = Section.gapSm),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Div(attrs = WhatIDoGridStyle.toModifier().toAttrs()) {
            WhatIDoCard(
                iconImage = Res.Icon.HEXAWEB,
                description = "Full Stack Systems & DevOps",
                modifier = BentoLeadStyle.toModifier(),
                backContent = {
                    back(
                        "Build Python full stack systems end-to-end, with hands-on Java work " +
                            "and desktop app development using Python."
                    )
                }
            )

            WhatIDoCard(
                iconImage = Res.Icon.DEV,
                description = "AI/ML Applications",
                backContent = { back("End-to-end AI/ML projects and Agentic AI automations.") }
            )

            WhatIDoCard(
                iconImage = Res.Icon.CUBOID,
                description = "Android Development",
                backContent = {
                    back("Android development with the latest tech stack, Kotlin and Jetpack Compose.")
                }
            )
        }
    }
}
