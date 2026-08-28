package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.JustifyItems
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.ProjectCardStyle
import com.vikramaditya.portfolio.utils.theme.Font
import com.vikramaditya.portfolio.utils.theme.Space
import com.vikramaditya.portfolio.utils.theme.ThemeColors
import com.vikramaditya.portfolio.utils.theme.Type
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.fontFace
import com.vikramaditya.portfolio.utils.theme.textStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img

/**
 * The featured card puts the thumbnail beside the copy instead of above it, so
 * the lead project reads at a different scale from the grid below it.
 */
val FeaturedBodyStyle = CssStyle {
    base {
        Modifier.fillMaxWidth().styleModifier {
            property("display", "grid")
            property("grid-template-columns", "1fr")
        }
    }
    Breakpoint.MD {
        Modifier.styleModifier {
            property("grid-template-columns", "1.15fr 1fr")
            property("align-items", "stretch")
        }
    }
}

/** Full-bleed media: it reaches the card edge, so the card clips it, not padding. */
val FeaturedMediaStyle = CssStyle {
    base {
        Modifier.fillMaxWidth().styleModifier {
            property("height", "100%")
            property("min-height", "200px")
            property("max-height", "260px")
        }
    }
    Breakpoint.MD {
        Modifier.styleModifier { property("max-height", "none") }
    }
}

@Composable
fun ProjectCard(
    title: String,
    description: String,
    imageUrl: String,
    mainTechStack: String,
    otherTechStack: String,
    iconsList: List<String>,
    modifier: Modifier = Modifier,
    featured: Boolean = false,
    onClick: () -> Unit,
) {
    val c = colors(ColorMode.current)

    Box(
        modifier = ProjectCardStyle.toModifier()
            .then(modifier)
            .role("link")
            .tabIndex(0)
            .ariaLabel("View project: $title")
            .onClick { onClick() }
            .onKeyDown { event ->
                val key = event.nativeEvent.asDynamic().key as? String
                if (key == "Enter" || key == " ") {
                    event.preventDefault()
                    onClick()
                }
            }
    ) {
        if (featured) {
            Div(attrs = FeaturedBodyStyle.toModifier().toAttrs()) {
                Img(
                    src = imageUrl,
                    attrs = FeaturedMediaStyle.toModifier()
                        .objectFit(ObjectFit.Cover)
                        .toAttrs {
                            attr("alt", "Screenshot of $title")
                            attr("loading", "lazy")
                            attr("decoding", "async")
                        }
                )
                ProjectCopy(
                    title = title,
                    description = description,
                    mainTechStack = mainTechStack,
                    otherTechStack = otherTechStack,
                    iconsList = iconsList,
                    colors = c,
                    titleStep = Type.Heading,
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Img(
                    src = imageUrl,
                    attrs = Modifier
                        .fillMaxWidth()
                        .height(180.px)
                        .objectFit(ObjectFit.Cover)
                        .toAttrs {
                            attr("alt", "Screenshot of $title")
                            attr("loading", "lazy")
                            attr("decoding", "async")
                        }
                )
                ProjectCopy(
                    title = title,
                    description = description,
                    mainTechStack = mainTechStack,
                    otherTechStack = otherTechStack,
                    iconsList = iconsList,
                    colors = c,
                    titleStep = Type.Title,
                )
            }
        }
    }
}

@Composable
private fun ProjectCopy(
    title: String,
    description: String,
    mainTechStack: String,
    otherTechStack: String,
    iconsList: List<String>,
    colors: ThemeColors,
    titleStep: com.vikramaditya.portfolio.utils.theme.TypeStep,
) {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(Space.lg),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        SpanText(
            title,
            Modifier
                .textStyle(titleStep)
                .fontFace(Font.DISPLAY)
                .color(colors.accent)
        )

        SpanText(
            description,
            Modifier
                .margin(top = Space.sm)
                .textStyle(Type.Small)
                .fontFace(Font.BODY)
                .color(colors.textSecondary)
        )

        Spacer()

        // Meta wraps rather than colliding: two stack strings side by side
        // overflowed a 280px card on phones.
        Row(
            Modifier
                .margin(top = Space.lg)
                .fillMaxWidth()
                .styleModifier {
                    property("flex-wrap", "wrap")
                    property("gap", "4px 16px")
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SpanText(
                mainTechStack,
                Modifier.textStyle(Type.Micro).fontFace(Font.BODY).color(colors.accent)
            )
            SpanText(
                otherTechStack,
                Modifier.textStyle(Type.Micro).fontFace(Font.BODY).color(colors.textSecondary)
            )
        }

        CustomHorizontalDivider()

        SimpleGrid(
            modifier = Modifier
                .fillMaxWidth()
                .justifyItems(JustifyItems.Center),
            numColumns = numColumns(base = 3, sm = 4, md = 5)
        ) {
            iconsList.forEach { icon ->
                Image(src = icon, alt = "", modifier = Modifier.size(24.px))
            }
        }
    }
}

/** Hairline rule. Deliberately not neon: a divider is structure, not emphasis. */
@Composable
fun CustomHorizontalDivider() {
    val c = colors(ColorMode.current)
    Box(
        modifier = Modifier
            .margin(topBottom = Space.md)
            .height(1.px)
            .width(100.percent)
            .backgroundColor(c.border)
    )
}
