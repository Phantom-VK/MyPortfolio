package com.vikramaditya.portfolio.styles

import com.varabyte.kobweb.compose.css.BackfaceVisibility
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TransformStyle
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.UserSelect
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.hover
import com.vikramaditya.portfolio.utils.theme.Font
import com.vikramaditya.portfolio.utils.theme.Radius
import com.vikramaditya.portfolio.utils.theme.Space
import com.vikramaditya.portfolio.utils.theme.Type
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.fontFace
import com.vikramaditya.portfolio.utils.theme.textStyle
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s

/**
 * Clipping lives on this outer element, never on the viewport below it.
 *
 * `overflow`, `filter`, `mask-image` and `opacity < 1` are CSS grouping
 * properties: applying any of them to an element forces its own `transform-style`
 * to `flat`, which would collapse the 3D scene. Clipping an *ancestor* of the
 * perspective element trims the rendered output without touching the descendants.
 * `overflow-x: clip` pairs legally with `overflow-y: visible`, unlike `hidden`,
 * which would compute to `auto` and create a scroll container.
 */
val CoverflowOuterStyle = CssStyle.base {
    Modifier
        .position(Position.Relative)
        .fillMaxWidth()
        .padding(topBottom = Space.xl)
        .styleModifier {
            property("overflow-x", "clip")
            property("overflow-y", "visible")
        }
}

val CoverflowViewportStyle = CssStyle {
    base {
        Modifier
            .position(Position.Relative)
            .fillMaxWidth()
            .userSelect(UserSelect.None)
            .cursor(Cursor.Grab)
            .styleModifier {
                property("perspective", "1200px")
                property("perspective-origin", "50% 50%")
                // Without pan-y the browser claims horizontal touch movement for
                // scrolling and fires pointercancel mid-gesture, so drag simply
                // does not work on mobile. Vertical page scroll still passes through.
                property("touch-action", "pan-y")
                property("outline", "none")
            }
    }
    Breakpoint.MD {
        Modifier.styleModifier { property("perspective", "1400px") }
    }
    cssRule(":active") { Modifier.cursor(Cursor.Grabbing) }
    cssRule(":focus-visible") {
        Modifier
            .outline(2.px, LineStyle.Solid, colors(colorMode).signal)
            .styleModifier { property("outline-offset", "4px") }
    }
}

val CoverflowTrackStyle = CssStyle.base {
    Modifier
        .position(Position.Relative)
        .fillMaxWidth()
        .transformStyle(TransformStyle.Preserve3d)
        .styleModifier { property("transform-origin", "50% 50%") }
}

/**
 * Card geometry is driven by the `--cf-card-w` custom property, which the
 * controller sets from Kotlin after measuring. That keeps one source of truth for
 * width, and the resting transform for the centre card is what gets serialised
 * into the static export, so first paint is correct before any JS runs.
 *
 * Deliberately no `transition` on `transform` or `opacity`: those are written
 * every frame by the render loop, and a transition would chase them forever.
 */
val CoverflowCardStyle = CssStyle.base {
    val c = colors(colorMode)
    Modifier
        .position(Position.Absolute)
        .top(0.px)
        .left(50.percent)
        .borderRadius(Radius.default)
        .border(1.px, LineStyle.Solid, c.border)
        .backgroundColor(c.surfaceRaised)
        .backfaceVisibility(BackfaceVisibility.Hidden)
        .transformStyle(TransformStyle.Preserve3d)
        .overflow(Overflow.Hidden)
        .boxShadow(
            offsetX = 0.px,
            offsetY = 18.px,
            blurRadius = 44.px,
            color = Color.rgba(0, 0, 0, 0.55f),
        )
        .styleModifier {
            property("width", "var(--cf-card-w, 320px)")
            property("margin-left", "calc(var(--cf-card-w, 320px) / -2)")
            property("aspect-ratio", "16 / 10")
            property("will-change", "transform, opacity")
            property("contain", "layout paint")
        }
}

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

/**
 * A fixed minimum height reserves the caption's space, so changing cards never
 * reflows the carousel above it.
 */
val CoverflowCaptionStyle = CssStyle.base {
    val c = colors(colorMode)
    Modifier
        .fillMaxWidth()
        .minHeight(64.px)
        .margin(top = Space.xl)
        .textAlign(com.varabyte.kobweb.compose.css.TextAlign.Center)
        .textStyle(Type.Small)
        .fontFace(Font.BODY)
        .color(c.textSecondary)
        .transition(Transition.of("opacity", 0.2.s))
}

val CoverflowDotsStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .justifyContent(JustifyContent.Center)
        .alignItems(AlignItems.Center)
        .margin(top = Space.lg)
        .styleModifier { property("gap", "8px") }
}

val CoverflowDotStyle = CssStyle {
    base {
        val c = colors(colorMode)
        Modifier
            .size(8.px)
            .padding(0.px)
            .borderRadius(Radius.default)
            .border(1.px, LineStyle.Solid, c.border)
            .backgroundColor(Color.rgba(0, 0, 0, 0f))
            .cursor(Cursor.Pointer)
            .transition(
                Transition.of("background-color", 0.2.s),
                Transition.of("transform", 0.2.s),
                Transition.of("border-color", 0.2.s),
            )
    }
    hover {
        Modifier.backgroundColor(colors(colorMode).accent)
    }
    cssRule("[aria-current='true']") {
        val c = colors(colorMode)
        Modifier
            .backgroundColor(c.signal)
            .border(1.px, LineStyle.Solid, c.signal)
            .transform { scaleX(2.2) }
    }
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
 * Empty state. Sized from the same `--cf-card-w` custom property as a real card,
 * so the section does not jump in height once the first achievement is added.
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
// Reduced-motion fallback: a plain scroll-snap strip. No physics, no 3D.
// ---------------------------------------------------------------------------

val CoverflowStripStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .display(DisplayStyle.Flex)
        .padding(topBottom = Space.lg)
        .styleModifier {
            property("gap", "24px")
            property("overflow-x", "auto")
            property("scroll-snap-type", "x mandatory")
            property("scroll-padding-inline", "50%")
            property("overscroll-behavior-x", "contain")
            property("-webkit-overflow-scrolling", "touch")
        }
}

val CoverflowStripItemStyle = CssStyle.base {
    val c = colors(colorMode)
    Modifier
        .borderRadius(Radius.default)
        .border(1.px, LineStyle.Solid, c.border)
        .backgroundColor(c.surfaceRaised)
        .overflow(Overflow.Hidden)
        .styleModifier {
            property("flex", "0 0 auto")
            property("width", "min(78vw, 460px)")
            property("scroll-snap-align", "center")
            property("scroll-snap-stop", "always")
        }
}
