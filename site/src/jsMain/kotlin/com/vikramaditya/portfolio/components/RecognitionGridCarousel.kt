package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.varabyte.kobweb.compose.dom.disposableRef
import com.varabyte.kobweb.compose.dom.registerRefScope
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.*
import com.vikramaditya.portfolio.utils.Recognition
import com.vikramaditya.portfolio.utils.theme.Font
import com.vikramaditya.portfolio.utils.theme.Space
import com.vikramaditya.portfolio.utils.theme.ThemeColors
import com.vikramaditya.portfolio.utils.theme.Type
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.fontFace
import com.vikramaditya.portfolio.utils.theme.textStyle
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement
import org.w3c.dom.SMOOTH
import org.w3c.dom.ScrollBehavior
import org.w3c.dom.ScrollToOptions

/**
 * A grid of badges, [rows] high, that scrolls horizontally as a carousel.
 *
 * Deliberately not built on a pointer-drag physics engine: on a real touch
 * device, custom horizontal-drag JS has to race the browser's own native
 * touch-scroll gesture recognizer for every gesture, and that race can be
 * lost silently (confirmed on-device: a 3D pointer-physics coverflow here
 * worked perfectly with a mouse but did not respond to touch at all). Plain
 * CSS sidesteps the race entirely: `display: grid` with [rows] fixed rows
 * and `grid-auto-flow: column` lays items into columns of [rows] cards
 * automatically, and `overflow-x: auto` with `scroll-snap-type` hands the
 * entire gesture to the browser's native touch-scroll handling — the same
 * code path every native app and every other scrollable page on the device
 * already uses, so there is no race to lose.
 */
@Composable
fun RecognitionGridCarousel(
    items: List<Recognition>,
    modifier: Modifier = Modifier,
    label: String = "Certifications",
    emptyTitle: String,
    emptySubtitle: String,
    rows: Int = 2,
) {
    if (items.isEmpty()) {
        GridCarouselEmpty(emptyTitle, emptySubtitle, modifier)
        return
    }

    val c = colors(ColorMode.current)
    val scrollerRef = remember { mutableStateOf<HTMLElement?>(null) }

    fun scrollByPage(direction: Int) {
        val el = scrollerRef.value ?: return
        val step = (el.clientWidth * 0.8).coerceAtLeast(240.0)
        el.scrollBy(ScrollToOptions(left = step * direction, behavior = ScrollBehavior.SMOOTH))
    }

    Column(modifier = Modifier.fillMaxWidth().then(modifier)) {
        Div(attrs = GridCarouselPanelStyle.toModifier().toAttrs()) {
            Div(
                attrs = GridCarouselScrollerStyle.toModifier()
                    .styleModifier { property("grid-template-rows", "repeat($rows, auto)") }
                    .toAttrs {
                        attr("role", "list")
                        attr("aria-label", label)
                        attr("tabindex", "0")
                    }
            ) {
                registerRefScope(
                    disposableRef(items.size) { element ->
                        scrollerRef.value = element
                        onDispose { scrollerRef.value = null }
                    }
                )

                items.forEach { item ->
                    Div(
                        attrs = GridCarouselCardStyle.toModifier().toAttrs {
                            attr("role", "listitem")
                        }
                    ) {
                        Div(attrs = GridCarouselCardImageStyle.toModifier().toAttrs()) {
                            GridCarouselCardFace(item)
                        }
                        GridCarouselCaption(item, c)
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .margin(top = Space.md)
                .styleModifier { property("gap", "16px") },
            horizontalArrangement = com.varabyte.kobweb.compose.foundation.layout.Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                attrs = CoverflowNavButtonStyle.toModifier().toAttrs {
                    attr("aria-label", "Scroll $label left")
                    attr("type", "button")
                    onClick { scrollByPage(-1) }
                }
            ) { Text("<") }

            Button(
                attrs = CoverflowNavButtonStyle.toModifier().toAttrs {
                    attr("aria-label", "Scroll $label right")
                    attr("type", "button")
                    onClick { scrollByPage(1) }
                }
            ) { Text(">") }
        }
    }
}

/**
 * The card's image, or a typographic stand-in until one exists. The title
 * and description live in [GridCarouselCaption] below, not here, so the
 * fallback face is purely decorative — otherwise the title would appear twice.
 */
@Composable
private fun GridCarouselCardFace(item: Recognition) {
    val c = colors(ColorMode.current)
    val imageUrl = item.imageUrl

    if (imageUrl != null) {
        Img(
            src = imageUrl,
            attrs = CoverflowMediaStyle.toModifier().toAttrs {
                attr("alt", item.imageAlt.ifEmpty { item.title })
                attr("loading", "lazy")
                attr("decoding", "async")
                item.intrinsicWidth?.let { attr("width", it.toString()) }
                item.intrinsicHeight?.let { attr("height", it.toString()) }
            }
        )
    } else {
        Div(attrs = CoverflowFallbackStyle.toModifier().toAttrs()) {
            Box(
                modifier = Modifier
                    .width(32.px)
                    .height(1.px)
                    .backgroundColor(c.borderStrong)
            )
        }
    }
}

/** Title + description, shown under every card's image — a flat grid has no shared "active card" area to hang a caption off, so each card carries its own. */
@Composable
private fun GridCarouselCaption(item: Recognition, c: ThemeColors) {
    Column(modifier = Modifier.fillMaxWidth().padding(Space.sm)) {
        SpanText(
            item.title,
            modifier = Modifier
                .fillMaxWidth()
                .textStyle(Type.Micro)
                .fontFace(Font.DISPLAY)
                .color(c.textPrimary)
                .then(GridCarouselCaptionTitleStyle.toModifier())
        )
        val meta = listOfNotNull(item.issuer, item.year).joinToString(" · ")
        val line = if (item.caption.isNotEmpty()) item.caption else meta
        if (line.isNotEmpty()) {
            SpanText(
                line,
                modifier = Modifier
                    .fillMaxWidth()
                    .margin(top = Space.xs)
                    .textStyle(Type.Micro)
                    .fontFace(Font.BODY)
                    .color(c.textSecondary)
                    .then(GridCarouselCaptionBodyStyle.toModifier())
            )
        }
    }
}

/** Shown until the first entry is added. */
@Composable
private fun GridCarouselEmpty(
    emptyTitle: String,
    emptySubtitle: String,
    modifier: Modifier = Modifier,
) {
    val c = colors(ColorMode.current)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(topBottom = Space.xl)
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Div(attrs = CoverflowEmptyStyle.toModifier().toAttrs()) {
            SpanText(
                emptyTitle,
                modifier = Modifier
                    .textStyle(Type.Title)
                    .fontFace(Font.DISPLAY)
                    .color(c.textPrimary)
            )
            SpanText(
                emptySubtitle,
                modifier = Modifier
                    .margin(top = Space.sm)
                    .textStyle(Type.Small)
                    .fontFace(Font.BODY)
                    .color(c.textSecondary)
            )
            SpanText(
                "_",
                modifier = Modifier
                    .margin(top = Space.sm)
                    .textStyle(Type.Title)
                    .fontFace(Font.DISPLAY)
                    .color(c.signal)
                    .animation(
                        CaretBlink.toAnimation(
                            duration = 1.s,
                            iterationCount = AnimationIterationCount.Infinite,
                        )
                    )
            )
        }
    }
}
