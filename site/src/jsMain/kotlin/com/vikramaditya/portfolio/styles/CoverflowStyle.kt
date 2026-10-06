package com.vikramaditya.portfolio.styles

import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.selectors.hover
import com.vikramaditya.portfolio.utils.theme.Font
import com.vikramaditya.portfolio.utils.theme.Radius
import com.vikramaditya.portfolio.utils.theme.Space
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.fontFace
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s

val CoverflowMediaStyle = CssStyle.base {
    Modifier
        .fillMaxSize()
        .styleModifier {
            property("object-fit", "cover")
            property("-webkit-user-drag", "none")
            property("pointer-events", "none")
        }
}

/** Shown when an entry has no image yet. A designed face, not a broken `<img>`. */
val CoverflowFallbackStyle = CssStyle.base {
    val c = colors(colorMode)
    Modifier
        .fillMaxSize()
        .display(DisplayStyle.Flex)
        .flexDirection(org.jetbrains.compose.web.css.FlexDirection.Column)
        .justifyContent(JustifyContent.Center)
        .alignItems(AlignItems.Center)
        .padding(Space.xl)
        .textAlign(com.varabyte.kobweb.compose.css.TextAlign.Center)
        .fontFace(Font.DISPLAY)
        .color(c.textPrimary)
}

val CoverflowNavButtonStyle = CssStyle {
    base {
        val c = colors(colorMode)
        Modifier
            .size(36.px)
            .display(DisplayStyle.Flex)
            .justifyContent(JustifyContent.Center)
            .alignItems(AlignItems.Center)
            .borderRadius(Radius.default)
            .border(1.px, LineStyle.Solid, c.border)
            .backgroundColor(Color.rgba(0, 0, 0, 0f))
            .color(c.textSecondary)
            .cursor(Cursor.Pointer)
            .fontFace(Font.DISPLAY)
            .transition(
                Transition.of("border-color", 0.2.s),
                Transition.of("color", 0.2.s),
            )
    }
    hover {
        val c = colors(colorMode)
        Modifier.border(1.px, LineStyle.Solid, c.borderStrong).color(c.textPrimary)
    }
    cssRule(":disabled") {
        Modifier.opacity(0.28).cursor(Cursor.NotAllowed)
    }
}

/**
 * Empty state. Sized to a plausible card footprint so the section does not
 * jump in height once the first entry is added.
 */
val CoverflowEmptyStyle = CssStyle.base {
    val c = colors(colorMode)
    Modifier
        .display(DisplayStyle.Flex)
        .flexDirection(org.jetbrains.compose.web.css.FlexDirection.Column)
        .justifyContent(JustifyContent.Center)
        .alignItems(AlignItems.Center)
        .padding(Space.xxl)
        .borderRadius(Radius.default)
        .border(1.px, LineStyle.Dashed, c.border)
        .backgroundColor(c.surfaceRaised)
        .textAlign(com.varabyte.kobweb.compose.css.TextAlign.Center)
        .styleModifier {
            property("width", "min(78vw, 460px)")
            property("aspect-ratio", "16 / 10")
            property("margin-inline", "auto")
        }
}

/** Off-screen but still announced. Used for the live region and keyboard hint. */
val VisuallyHiddenStyle = CssStyle.base {
    Modifier.styleModifier {
        property("position", "absolute")
        property("width", "1px")
        property("height", "1px")
        property("overflow", "hidden")
        property("clip-path", "inset(50%)")
        property("white-space", "nowrap")
        property("border", "0")
        property("padding", "0")
        property("margin", "-1px")
    }
}

// ---------------------------------------------------------------------------
// Badge grid + carousel (Achievements, Certifications). Pure CSS scroll-snap,
// no pointer-physics controller: `display: grid` with `grid-auto-flow: column`
// and N fixed rows lays items into N-high columns automatically, and
// `overflow-x: auto` + `scroll-snap-type` hands the entire gesture to the
// browser's native touch-scroll handling instead of racing it with custom
// pointer-event JS — the race a hand-rolled 3D coverflow lost on real touch
// devices (it worked with a mouse but never responded to touch at all).
// ---------------------------------------------------------------------------

/**
 * The "a little bit white" panel the rows sit inside, so the carousel reads
 * as a distinct surface against the page's dark matrix background rather
 * than floating loose over it.
 */
val GridCarouselPanelStyle = CssStyle.base {
    val c = colors(colorMode)
    Modifier
        .fillMaxWidth()
        .border(1.px, LineStyle.Solid, c.border)
        .borderRadius(Radius.default)
        .padding(Space.lg)
        .backgroundColor(
            if (colorMode.isDark) Color.rgba(255, 255, 255, 0.05f) else Color.rgba(255, 255, 255, 0.5f)
        )
}

val GridCarouselScrollerStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .styleModifier {
                property("display", "grid")
                property("grid-auto-flow", "column")
                property("grid-template-rows", "repeat(2, auto)")
                property("gap", "16px")
                property("overflow-x", "auto")
                property("scroll-snap-type", "x proximity")
                property("scroll-padding-inline", "0")
                property("overscroll-behavior-x", "contain")
                property("touch-action", "pan-x")
                property("-webkit-overflow-scrolling", "touch")
                property("outline", "none")
                // Scrollbar hidden: the prev/next buttons and edge-peek of the
                // next column are the discoverability cue instead.
                property("scrollbar-width", "none")
            }
    }
    cssRule("::-webkit-scrollbar") {
        Modifier.styleModifier { property("display", "none") }
    }
    cssRule(":focus-visible") {
        Modifier
            .outline(2.px, LineStyle.Solid, colors(colorMode).signal)
            .styleModifier { property("outline-offset", "4px") }
    }
}

/**
 * A fixed card width — there's no controller here to measure and set one.
 * Taller than just the image: a caption (title + description) sits below it,
 * so there's no fixed `aspect-ratio` on the card itself, only on the image
 * box inside it ([GridCarouselCardImageStyle]).
 */
val GridCarouselCardStyle = CssStyle.base {
    val c = colors(colorMode)
    Modifier
        .borderRadius(Radius.default)
        .border(1.px, LineStyle.Solid, c.border)
        .backgroundColor(c.surfaceRaised)
        .overflow(Overflow.Hidden)
        .styleModifier {
            property("width", "clamp(160px, 38vw, 240px)")
            property("scroll-snap-align", "start")
            property("scroll-snap-stop", "normal")
        }
}

/** The image (or fallback face) occupies this fixed-ratio box at the top of the card. */
val GridCarouselCardImageStyle = CssStyle.base {
    Modifier.styleModifier { property("aspect-ratio", "16 / 10") }
}

/**
 * Title + description below the image. Line-clamped so one long entry
 * doesn't stretch an entire grid row — grid rows share a height across all
 * their columns, so an unbounded caption here would tax every card in the row.
 */
val GridCarouselCaptionTitleStyle = CssStyle.base {
    Modifier.styleModifier {
        property("display", "-webkit-box")
        property("-webkit-line-clamp", "2")
        property("-webkit-box-orient", "vertical")
        property("overflow", "hidden")
    }
}

val GridCarouselCaptionBodyStyle = CssStyle.base {
    Modifier.styleModifier {
        property("display", "-webkit-box")
        property("-webkit-line-clamp", "3")
        property("-webkit-box-orient", "vertical")
        property("overflow", "hidden")
    }
}
