package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.vikramaditya.portfolio.utils.Achievement
import com.vikramaditya.portfolio.utils.rememberPrefersReducedMotion
import com.vikramaditya.portfolio.utils.theme.Font
import com.vikramaditya.portfolio.utils.theme.Space
import com.vikramaditya.portfolio.utils.theme.Type
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.fontFace
import com.vikramaditya.portfolio.utils.theme.textStyle
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

/**
 * A drag-and-flick coverflow of achievement images.
 *
 * Branches at the composable level rather than in CSS, so under reduced motion no
 * pointer listeners and no animation loop are ever created in the first place.
 */
@Composable
fun AchievementCoverflow(
    items: List<Achievement>,
    modifier: Modifier = Modifier,
    label: String = "Achievements",
) {
    val reduced = rememberPrefersReducedMotion()
    when {
        items.isEmpty() -> CoverflowEmpty(modifier)
        reduced -> AchievementStrip(items, modifier, label)
        else -> CoverflowPhysics(items, modifier, label)
    }
}

@Composable
private fun CoverflowPhysics(
    items: List<Achievement>,
    modifier: Modifier,
    label: String,
) {
    val activeIndexState = remember(items.size) { mutableStateOf(0) }
    val activeIndex by activeIndexState
    val controller = remember(items.size) { CoverflowController(activeIndexState, items.size) }
    val c = colors(ColorMode.current)
    val lastIndex = items.lastIndex

    Column(modifier = Modifier.fillMaxWidth().then(modifier)) {
        Box(modifier = CoverflowOuterStyle.toModifier()) {
            Div(
                attrs = CoverflowViewportStyle.toModifier().toAttrs {
                    attr("role", "group")
                    attr("aria-roledescription", "carousel")
                    attr("aria-label", label)
                    attr("aria-describedby", "coverflow-help")
                    attr("tabindex", "0")
                }
            ) {
                Div(attrs = CoverflowTrackStyle.toModifier().toAttrs()) {
                    registerRefScope(
                        disposableRef(items.size) { element ->
                            controller.setTrack(element)
                            onDispose { controller.setTrack(null) }
                        }
                    )

                    items.forEachIndexed { index, item ->
                        val isActive = index == activeIndex
                        Div(
                            attrs = CoverflowCardStyle.toModifier().toAttrs {
                                attr("data-cf-index", index.toString())
                                attr("role", "group")
                                attr("aria-roledescription", "slide")
                                attr("aria-label", "${index + 1} of ${items.size}: ${item.title}")
                                if (!isActive) attr("aria-hidden", "true")
                            }
                        ) {
                            registerRefScope(
                                disposableRef(index, items.size) { element ->
                                    controller.setCard(index, element)
                                    onDispose { controller.setCard(index, null) }
                                }
                            )
                            CoverflowCardFace(item)
                        }
                    }
                }

                // Registered last on purpose: Compose dispatches these effects in
                // the order they were recorded, so every card element above is
                // already captured by the time attach() measures and paints.
                registerRefScope(
                    disposableRef(items.size) { element ->
                        controller.attach(element)
                        onDispose { controller.detach() }
                    }
                )
            }
        }

        val active = items.getOrNull(activeIndex)
        Div(attrs = CoverflowCaptionStyle.toModifier().toAttrs()) {
            if (active != null) {
                SpanText(
                    active.title,
                    modifier = Modifier
                        .textStyle(Type.Title)
                        .fontFace(Font.DISPLAY)
                        .color(c.textPrimary)
                )
                val meta = listOfNotNull(active.issuer, active.year).joinToString(" · ")
                if (meta.isNotEmpty() || active.caption.isNotEmpty()) {
                    P(
                        attrs = Modifier
                            .margin(top = Space.sm, bottom = 0.px)
                            .textStyle(Type.Small)
                            .fontFace(Font.BODY)
                            .color(c.textSecondary)
                            .toAttrs()
                    ) {
                        Text(if (active.caption.isNotEmpty()) active.caption else meta)
                    }
                }
            }
        }

        // Controls
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
                    attr("aria-label", "Previous achievement")
                    attr("type", "button")
                    if (activeIndex <= 0) attr("disabled", "")
                    onClick { controller.step(-1) }
                }
            ) { Text("<") }

            Div(
                attrs = CoverflowDotsStyle.toModifier().toAttrs {
                    attr("role", "group")
                    attr("aria-label", "Choose achievement")
                }
            ) {
                items.forEachIndexed { index, item ->
                    Button(
                        attrs = CoverflowDotStyle.toModifier().toAttrs {
                            attr("type", "button")
                            attr("aria-label", "Go to ${index + 1} of ${items.size}: ${item.title}")
                            attr("aria-current", if (index == activeIndex) "true" else "false")
                            onClick { controller.goTo(index) }
                        }
                    ) {}
                }
            }

            Button(
                attrs = CoverflowNavButtonStyle.toModifier().toAttrs {
                    attr("aria-label", "Next achievement")
                    attr("type", "button")
                    if (activeIndex >= lastIndex) attr("disabled", "")
                    onClick { controller.step(1) }
                }
            ) { Text(">") }
        }

        // Announced, not seen. The dots carry this information visually.
        Div(
            attrs = VisuallyHiddenStyle.toModifier().toAttrs {
                attr("aria-live", "polite")
                attr("aria-atomic", "true")
            }
        ) {
            if (active != null) Text("Showing ${activeIndex + 1} of ${items.size}: ${active.title}")
        }
        Div(attrs = VisuallyHiddenStyle.toModifier().toAttrs { attr("id", "coverflow-help") }) {
            Text("Use the left and right arrow keys to move between achievements.")
        }
    }
}

/** The card's inner face: the image, or a typographic stand-in until one exists. */
@Composable
private fun CoverflowCardFace(item: Achievement) {
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
            SpanText(
                item.title,
                modifier = Modifier.textStyle(Type.Title).color(c.textPrimary)
            )
            Box(
                modifier = Modifier
                    .margin(topBottom = Space.md)
                    .width(48.px)
                    .height(1.px)
                    .backgroundColor(c.borderStrong)
            )
            val meta = listOfNotNull(item.issuer, item.year).joinToString(" · ")
            if (meta.isNotEmpty()) {
                SpanText(
                    meta,
                    modifier = Modifier
                        .textStyle(Type.Micro)
                        .fontFace(Font.BODY)
                        .color(c.textSecondary)
                )
            }
        }
    }
}

/**
 * Reduced-motion path. A plain scroll-snap strip with no active index at all, so
 * each card carries its own caption and there is nothing to announce.
 */
@Composable
private fun AchievementStrip(
    items: List<Achievement>,
    modifier: Modifier,
    label: String,
) {
    val c = colors(ColorMode.current)

    Div(
        attrs = CoverflowStripStyle.toModifier().then(modifier).toAttrs {
            attr("role", "list")
            attr("aria-label", label)
            attr("tabindex", "0")
        }
    ) {
        items.forEach { item ->
            Div(attrs = Modifier.styleModifier { property("flex", "0 0 auto") }.toAttrs { attr("role", "listitem") }) {
                Div(
                    attrs = CoverflowStripItemStyle.toModifier()
                        .styleModifier { property("aspect-ratio", "16 / 10") }
                        .toAttrs()
                ) {
                    CoverflowCardFace(item)
                }
                Column(modifier = Modifier.margin(top = Space.md).styleModifier {
                    property("max-width", "min(78vw, 460px)")
                }) {
                    SpanText(
                        item.title,
                        modifier = Modifier
                            .textStyle(Type.Body)
                            .fontFace(Font.DISPLAY)
                            .color(c.textPrimary)
                    )
                    val meta = listOfNotNull(item.issuer, item.year).joinToString(" · ")
                    val line = if (item.caption.isNotEmpty()) item.caption else meta
                    if (line.isNotEmpty()) {
                        SpanText(
                            line,
                            modifier = Modifier
                                .margin(top = Space.xs)
                                .textStyle(Type.Micro)
                                .fontFace(Font.BODY)
                                .color(c.textSecondary)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Shown until the first achievement is added. Sized like a real card so the
 * section does not change height when content arrives.
 */
@Composable
private fun CoverflowEmpty(modifier: Modifier = Modifier) {
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
                "Felicitations",
                modifier = Modifier
                    .textStyle(Type.Title)
                    .fontFace(Font.DISPLAY)
                    .color(c.textPrimary)
            )
            SpanText(
                "Awards and recognitions are being added here.",
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
