package com.vikramaditya.portfolio.styles

import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.css.BackfaceVisibility
import com.varabyte.kobweb.compose.css.Background
import com.varabyte.kobweb.compose.css.BoxSizing
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TransformStyle
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.css.UserSelect
import com.varabyte.kobweb.compose.css.functions.LinearGradient
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.css.functions.toImage
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.backfaceVisibility
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxSizing
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.content
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.transform
import com.varabyte.kobweb.compose.ui.modifiers.transformStyle
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.userSelect
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.selectors.after
import com.varabyte.kobweb.silk.style.selectors.before
import com.varabyte.kobweb.silk.style.selectors.hover
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s
import org.jetbrains.compose.web.css.vh

val MatrixBlack = Color.rgb(0x0d0d0d)
val MatrixWhiteText = Color.rgb(0xccffcc)
val MatrixGreen = Color.rgb(0x00ff41)
val MatrixNeonGreen = Color.rgba(0, 255, 65, 0.7f)
val MatrixGlow1 = Color.rgb(0x39ff14)
val MatrixGlow2 = Color.rgb(0x00ff9d)
val MatrixGlow3 = Color.rgb(0x00ffcc)



val AllStyle = CssStyle.base {
    Modifier
        .margin(0.px)
        .userSelect(UserSelect.None)
        .padding(0.px)
        .boxSizing(BoxSizing.BorderBox)
}
val BodyStyle = CssStyle.base {
    Modifier
        .minHeight(100.vh)
        .userSelect(UserSelect.None)
        .display(DisplayStyle.Flex)
        .justifyContent(JustifyContent.Center)
        .alignItems(AlignItems.Center)
        .backgroundColor(MatrixBlack)
        .color(MatrixWhiteText)
        .fontFamily("Share Tech Mono", "monospace")
        .styleModifier {
            property("perspective", "1000px")
        }
}

val ContainerStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Flex)
        .userSelect(UserSelect.None)
        .justifyContent(JustifyContent.Center)
        .alignItems(AlignItems.Center)
        .transformStyle(TransformStyle.Preserve3d)
}
val BoxCardStyle = CssStyle {
    base {
        Modifier
            .position(Position.Relative)
            .userSelect(UserSelect.None)
            .transformStyle(TransformStyle.Preserve3d)
            .transition(Transition.of("transform", 1.s, TransitionTimingFunction.Ease))
            .cursor(Cursor.Pointer)
            .size(200.px)
    }
    hover {
        Modifier
            .animation(
                Rotate3d.toAnimation(
                    duration = 8.s,
                    timingFunction = AnimationTimingFunction.Linear,
                    iterationCount = AnimationIterationCount.Infinite
                )
            )
    }
}
val FaceStyle = CssStyle {
    base {
        Modifier
            .position(Position.Absolute)
            .userSelect(UserSelect.None)
            .display(DisplayStyle.Flex)
            .justifyContent(JustifyContent.Center)
            .alignItems(AlignItems.Center)
            .fontSize(1.5.cssRem)
            .fontWeight(FontWeight.Bold)
            .fontFamily("Share Tech Mono", "monospace")
            .backfaceVisibility(BackfaceVisibility.Hidden)
            .borderRadius(8.px)
            .backgroundColor(MatrixBlack)
            .border(2.px, LineStyle.Solid, Res.Theme.THEME_GREEN.color)
            .transition(Transition.of("all", 0.3.s, TransitionTimingFunction.Ease))
            .overflow(Overflow.Hidden)
            .fillMaxSize()
    }

    before {
        Modifier
            .content("")
            .position(Position.Absolute)
            .background(
                Background.of(
                    image = linearGradient(45.deg) {
                        add(Colors.Transparent)
                        add(Res.Theme.THEME_GREEN.color)
                        add(Colors.Transparent)
                    }.toImage()
                )
            )
            .top((-100).percent)
            .left((-100).percent)
            .transition(Transition.of("all", 0.5.s))
            .transform { translateZ(20.px) }
            .fillMaxSize()
    }

    after {
        Modifier
            .content("")
            .position(Position.Absolute)
            .styleModifier {
                property("inset", "0.px")
            }
            .background(
                Background.of(
                    image = linearGradient(135.deg) {
                        add(Colors.Transparent, 0.percent)
                        add(Res.Theme.THEME_GREEN.color, 50.percent)
                        add(Colors.Transparent, 100.percent)
                    }.toImage()
                )
            )
            .opacity(0)
            .transition(Transition.of("opacity", 0.3.s))
    }

    cssRule(":hover::before") {
        Modifier.top(100.percent).left(100.percent)
    }

    cssRule(":hover::after") {
        Modifier.opacity(1)
    }
}

val FrontStyle = CssStyle.base {
    Modifier
        .transform { translateZ(100.px) }
        .border {
            width(1.5.px)
            color(MatrixGreen) }
}

val BackStyle = CssStyle.base {
    Modifier
        .transform {
            translateZ((-100).px)
            rotateY(180.deg)
        }
        .border {
            width(1.5.px)
            color(MatrixGlow1) }
}

val RightStyle = CssStyle.base {
    Modifier
        .transform {
            translateX(100.px)
            rotateY(90.deg)
        }
        .border {
            width(1.5.px)
            color(MatrixGlow2) }
}

val LeftStyle = CssStyle.base {
    Modifier
        .transform {
            translateX((-100).px)
            rotateY((-90).deg)
        }
        .border {
            width(1.5.px)
            color(MatrixGlow3) }
}

val TopStyle = CssStyle.base {
    Modifier
        .transform {
            translateY((-100).px)
            rotateX(90.deg)
        }
        .border {
            width(1.5.px)
            color(MatrixNeonGreen) }
}

val BottomStyle = CssStyle.base {
    Modifier
        .transform {
            translateY(100.px)
            rotateX((-90).deg)
        }
        .border {
            width(1.5.px)
            color(MatrixGreen) }
}

val Rotate3d = Keyframes {
    0.percent {
        Modifier
            .transform {
                rotateX(0.deg)
                rotateY(0.deg)
            }
    }
    25.percent {
        Modifier
            .transform {
                rotateX(90.deg)
                rotateY(90.deg)
            }
    }
    50.percent {
        Modifier
            .transform {
                rotateX(180.deg)
                rotateY(180.deg)
            }
    }
    75.percent {
        Modifier
            .transform {
                rotateX(270.deg)
                rotateY(270.deg)
            }
    }
    100.percent {
        Modifier
            .transform {
                rotateX(360.deg)
                rotateY(360.deg)
            }
    }
}

