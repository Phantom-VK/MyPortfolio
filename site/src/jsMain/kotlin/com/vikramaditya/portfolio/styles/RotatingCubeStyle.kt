package com.vikramaditya.portfolio.styles

import com.varabyte.kobweb.compose.css.BackfaceVisibility
import com.varabyte.kobweb.compose.css.Background
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TransformStyle
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.UserSelect
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.css.functions.toImage
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.after
import com.varabyte.kobweb.silk.style.selectors.before
import com.vikramaditya.portfolio.utils.theme.Font
import com.vikramaditya.portfolio.utils.theme.Radius
import com.vikramaditya.portfolio.utils.theme.Type
import com.vikramaditya.portfolio.utils.theme.accentRamp
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.fontFace
import com.vikramaditya.portfolio.utils.theme.textStyle
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s

/**
 * CSS 3D tech-stack cube.
 *
 * The cube's half-depth must match its half-width or the faces do not meet, so
 * [CubeHalf] drives both the face translations and the responsive size below.
 * A cube that is 200px wide but translated by a hardcoded 100px only works at
 * one breakpoint, which is why the size is a token rather than a literal.
 */
const val CUBE_SIZE_SM = 132
const val CUBE_SIZE_MD = 168
const val CUBE_SIZE_LG = 200

private const val CubeHalfSm = CUBE_SIZE_SM / 2
private const val CubeHalfMd = CUBE_SIZE_MD / 2
private const val CubeHalfLg = CUBE_SIZE_LG / 2

val ContainerStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .userSelect(UserSelect.None)
        .justifyContent(JustifyContent.Center)
        .alignItems(AlignItems.Center)
        .transformStyle(TransformStyle.Preserve3d)
        .styleModifier {
            property("perspective", "900px")
        }
}

/**
 * Rotation is driven imperatively from Kotlin (see `widgets/Cube3D.kt`), so this
 * style deliberately declares no `transition` on `transform`: a CSS transition
 * would fight the per-frame writes and produce a permanent lag behind the input.
 */
val BoxCardStyle = CssStyle {
    base {
        Modifier
            .position(Position.Relative)
            .userSelect(UserSelect.None)
            .transformStyle(TransformStyle.Preserve3d)
            .cursor(Cursor.Grab)
            .size(CUBE_SIZE_SM.px)
    }
    Breakpoint.MD { Modifier.size(CUBE_SIZE_MD.px) }
    Breakpoint.LG { Modifier.size(CUBE_SIZE_LG.px) }
    cssRule(":active") { Modifier.cursor(Cursor.Grabbing) }
}

val FaceStyle = CssStyle {
    base {
        val c = colors(colorMode)
        Modifier
            .position(Position.Absolute)
            .userSelect(UserSelect.None)
            .display(DisplayStyle.Flex)
            .justifyContent(JustifyContent.Center)
            .alignItems(AlignItems.Center)
            .textStyle(Type.Title)
            .fontFace(Font.DISPLAY)
            .backfaceVisibility(BackfaceVisibility.Hidden)
            .borderRadius(Radius.default)
            .backgroundColor(c.surface)
            .border(1.px, LineStyle.Solid, c.borderStrong)
            .overflow(Overflow.Hidden)
            .fillMaxSize()
    }

    // A sheen that sweeps across the face on hover. Purely decorative, so it is
    // built from pseudo-elements rather than extra DOM.
    before {
        val c = colors(colorMode)
        Modifier
            .content("")
            .position(Position.Absolute)
            .background(
                Background.of(
                    image = linearGradient(45.deg) {
                        add(Colors.Transparent)
                        add(c.accent)
                        add(Colors.Transparent)
                    }.toImage()
                )
            )
            .top((-100).percent)
            .left((-100).percent)
            .opacity(0.55)
            .transition(Transition.of("all", 0.5.s))
            .transform { translateZ(20.px) }
            .fillMaxSize()
    }

    after {
        val c = colors(colorMode)
        Modifier
            .content("")
            .position(Position.Absolute)
            .styleModifier {
                property("inset", "0")
            }
            .background(
                Background.of(
                    image = linearGradient(135.deg) {
                        add(Colors.Transparent, 0.percent)
                        add(c.accent, 50.percent)
                        add(Colors.Transparent, 100.percent)
                    }.toImage()
                )
            )
            .opacity(0)
            .transition(Transition.of("opacity", 0.3.s))
    }

    cssRule(":hover::before") { Modifier.top(100.percent).left(100.percent) }
    cssRule(":hover::after") { Modifier.opacity(0.65) }
}

/**
 * Face placement. Each face is pushed out by half the cube's edge length, so the
 * translation has to track the responsive size in [BoxCardStyle] step for step.
 */
private fun faceStyle(rampIndex: Int, place: Modifier.(Int) -> Modifier) = CssStyle {
    base {
        Modifier
            .border(1.px, LineStyle.Solid, accentRamp(colorMode)[rampIndex])
            .place(CubeHalfSm)
    }
    Breakpoint.MD { Modifier.place(CubeHalfMd) }
    Breakpoint.LG { Modifier.place(CubeHalfLg) }
}

val FrontStyle = faceStyle(0) { h -> transform { translateZ(h.px) } }
val BackStyle = faceStyle(1) { h -> transform { translateZ((-h).px); rotateY(180.deg) } }
val RightStyle = faceStyle(2) { h -> transform { translateX(h.px); rotateY(90.deg) } }
val LeftStyle = faceStyle(3) { h -> transform { translateX((-h).px); rotateY((-90).deg) } }
val TopStyle = faceStyle(4) { h -> transform { translateY((-h).px); rotateX(90.deg) } }
val BottomStyle = faceStyle(5) { h -> transform { translateY(h.px); rotateX((-90).deg) } }

/** Face icons scale with the cube, so they keep the same optical margin at every size. */
val CubeFaceIconStyle = CssStyle {
    base { Modifier.size((CUBE_SIZE_SM * 0.5).px) }
    Breakpoint.MD { Modifier.size((CUBE_SIZE_MD * 0.5).px) }
    Breakpoint.LG { Modifier.size((CUBE_SIZE_LG * 0.5).px) }
}
