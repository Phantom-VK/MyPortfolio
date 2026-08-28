package com.vikramaditya.portfolio.utils.theme

import com.varabyte.kobweb.compose.css.CSSLengthNumericValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px

/**
 * The single source of truth for colour, type, spacing, and radius.
 *
 * Call sites should never pick a raw colour or a raw pixel size. They ask for a
 * semantic role ([ThemeColors]) or a scale step ([Type], [Space], [Radius]) and
 * get a value that is already correct for the active colour mode.
 *
 * Scales follow the `typeui.sh` "matrix" reference: type 12/14/16/20/24/32,
 * spacing 4/8/12/16/24/32, 2px corners, hairline borders.
 */

// ---------------------------------------------------------------------------
// Colour
// ---------------------------------------------------------------------------

/**
 * Semantic colour roles. Two greens on purpose:
 *
 *  - [accent] is the workhorse. It carries area: borders, labels, links, meta text.
 *  - [signal] is reserved for emphasis: focus rings, the active nav dot, the glyph
 *    rain, hover glow. Neon green over large areas vibrates against a dark ground
 *    and stops reading as emphasis, so it is rationed deliberately.
 */
data class ThemeColors(
    val surface: Color,
    val surfaceRaised: Color,
    /** Translucent material for floating chrome (header, toolbars) that content scrolls under. */
    val chrome: Color,
    val border: Color,
    val borderStrong: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val accent: Color,
    val signal: Color,
    /** Foreground for anything sitting on top of [accent] or [signal]. */
    val onAccent: Color,
)

private val DarkColors = ThemeColors(
    surface = Color.rgb(0x0B0C14),
    surfaceRaised = Color.rgb(0x12141C),
    chrome = Color.rgba(11, 12, 20, 0.72f),
    border = Color.rgba(255, 255, 255, 0.10f),
    borderStrong = Color.rgba(45, 181, 138, 0.40f),
    textPrimary = Color.rgb(0xE6E8EA),
    textSecondary = Color.rgba(230, 232, 234, 0.62f),
    accent = Color.rgb(0x2DB58A),
    signal = Color.rgb(0x00FF41),
    onAccent = Color.rgb(0x06130E),
)

/**
 * Light mode darkens both greens, and by more than looks necessary.
 *
 * Measured against the `#DCDCDC` ground, `#2DB58A` lands at 3.1:1 and `#00A82B`
 * at 2.3:1, so both fail WCAG AA for text and `#00A82B` also fails the 3:1 floor
 * for a focus ring. These two are the darkest variants that clear AA (4.7:1 and
 * 4.2:1) while still reading as the same two greens.
 */
private val LightColors = ThemeColors(
    surface = Color.rgb(0xDCDCDC),
    surfaceRaised = Color.rgb(0xF2F2F2),
    chrome = Color.rgba(220, 220, 220, 0.78f),
    border = Color.rgba(0, 0, 0, 0.14f),
    borderStrong = Color.rgba(13, 107, 78, 0.50f),
    textPrimary = Color.rgb(0x111827),
    textSecondary = Color.rgba(17, 24, 39, 0.66f),
    accent = Color.rgb(0x0D6B4E),
    signal = Color.rgb(0x00761F),
    onAccent = Color.rgb(0xFFFFFF),
)

/** Resolve the palette once, instead of branching on colour mode at every call site. */
fun colors(mode: ColorMode): ThemeColors = if (mode.isDark) DarkColors else LightColors

/**
 * Ordered green shades for elements that repeat a form and want variety across
 * the repeats, such as the six faces of a tech-stack cube. Derived from the two
 * palette greens so there is still exactly one place greens are defined.
 */
fun accentRamp(mode: ColorMode): List<Color> {
    val c = colors(mode)
    return if (mode.isDark) listOf(
        c.accent,
        Color.rgb(0x39FF14),
        Color.rgb(0x00FF9D),
        Color.rgb(0x00FFCC),
        Color.rgba(0, 255, 65, 0.70f),
        c.signal,
    ) else listOf(
        c.accent,
        Color.rgb(0x2F7D32),
        Color.rgb(0x0F7B63),
        Color.rgb(0x136F5B),
        Color.rgba(31, 138, 104, 0.70f),
        c.signal,
    )
}

// ---------------------------------------------------------------------------
// Typography
// ---------------------------------------------------------------------------

object Font {
    /** Headings and UI chrome. The matrix signature face. */
    const val DISPLAY = "Share Tech Mono"

    /** Body, captions, code. Text-grade mono, legible at paragraph sizes. */
    const val BODY = "JetBrains Mono"

    /** Small decorative labels only. A pixel face, unreadable as body copy. */
    const val ACCENT = "VT323"

    const val FALLBACK = "monospace"
}

/**
 * A type step binds size to its weight, leading, and tracking so they cannot drift
 * apart. Tracking is size-specific by design: large text needs negative tracking
 * because letters read too far apart as they grow, small text needs a positive
 * bump for legibility. One tracking value for every size is wrong somewhere.
 */
class TypeStep(
    val size: CSSLengthNumericValue,
    val lineHeight: Double,
    val tracking: CSSLengthNumericValue,
    val weight: FontWeight,
)

object Type {
    /** 12px. Meta rows, captions, dot labels. */
    val Micro = TypeStep(12.px, 1.40, 0.02.em, FontWeight.Medium)

    /** 14px. Secondary copy, nav labels. */
    val Small = TypeStep(14.px, 1.50, 0.01.em, FontWeight.Normal)

    /** 16px. Default body. */
    val Body = TypeStep(16.px, 1.60, 0.em, FontWeight.Normal)

    /** 20px. Card titles, lead paragraphs. */
    val Title = TypeStep(20.px, 1.35, (-0.005).em, FontWeight.Medium)

    /** 24px. Section headings at small breakpoints. */
    val Heading = TypeStep(24.px, 1.25, (-0.01).em, FontWeight.Bold)

    /** 32px. Section headings at large breakpoints. */
    val Display = TypeStep(32.px, 1.10, (-0.02).em, FontWeight.Bold)

    /**
     * 44px. The one step beyond the typeui scale, for the hero headline only.
     * A hero capped at 32px cannot hold the page.
     */
    val Hero = TypeStep(44.px, 1.05, (-0.025).em, FontWeight.Bold)
}

/** Apply a type step as a set. Prefer this over setting `fontSize` alone. */
fun Modifier.textStyle(step: TypeStep): Modifier = this
    .fontSize(step.size)
    .lineHeight(step.lineHeight)
    .letterSpacing(step.tracking)
    .fontWeight(step.weight)

/** Font family with its fallback attached, so no node silently falls back to the browser default. */
fun Modifier.fontFace(family: String): Modifier = this.fontFamily(family, Font.FALLBACK)

// ---------------------------------------------------------------------------
// Spacing and shape
// ---------------------------------------------------------------------------

object Space {
    val xs = 4.px
    val sm = 8.px
    val md = 12.px
    val lg = 16.px
    val xl = 24.px
    val xxl = 32.px
}

/**
 * Vertical rhythm between page sections. Larger than the [Space] scale on purpose:
 * a portfolio wants air between sections, and 32px reads as a gap inside a section
 * rather than a break between two.
 */
object Section {
    val gapSm = 48.px
    val gapMd = 72.px
    val gapLg = 104.px
}

/**
 * One radius for the whole site. Sharp corners read as terminal and hold the
 * matrix posture better than the previous soft 8px.
 */
object Radius {
    val default = 2.px
}

object Stroke {
    /** Hairline. Every border on the site is 1px. */
    val hairline = 1.px
}
