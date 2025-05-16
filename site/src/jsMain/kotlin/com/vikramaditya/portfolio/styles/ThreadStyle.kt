    package com.vikramaditya.portfolio.styles

    import com.varabyte.kobweb.compose.css.*
    import com.varabyte.kobweb.compose.css.functions.LinearGradient
    import com.varabyte.kobweb.compose.css.functions.linearGradient
    import com.varabyte.kobweb.compose.css.functions.toImage
    import com.varabyte.kobweb.compose.ui.Modifier
    import com.varabyte.kobweb.compose.ui.graphics.Color
    import com.varabyte.kobweb.compose.ui.graphics.Colors
    import com.varabyte.kobweb.compose.ui.modifiers.*
    import com.varabyte.kobweb.compose.ui.styleModifier
    import com.varabyte.kobweb.silk.style.CssStyle
    import com.varabyte.kobweb.silk.style.animation.Keyframes
    import com.varabyte.kobweb.silk.style.selectors.hover
    import org.jetbrains.compose.web.css.*

    // Physics-based animations
    val ThreadSwingAnimation = Keyframes {
        0.percent { Modifier.transform { rotate((-3).deg) } }
        50.percent { Modifier.transform { rotate(0.deg) } }
        100.percent { Modifier.transform { rotate(3.deg) } }
    }

    val ThreadWaveAnimation = Keyframes {
        0.percent { Modifier.transform { translateX((-1).px) } }
        50.percent { Modifier.transform { translateX(1.px) } }
        100.percent { Modifier.transform { translateX((-1).px) } }
    }

    val ThreadStretchAnimation = Keyframes {
        0.percent { Modifier.height(5.px) }
        50.percent { Modifier.height(7.px) }
        100.percent { Modifier.height(5.px) }
    }

    val ThreadStyle = CssStyle {
        base {
            Modifier
                .width(1.px)
                .height(7.px)
                .background(
                    Background.of(
                        linearGradient(
                            dir = LinearGradient.Direction.ToBottom,
                            from = Color.rgb(0x888888),
                            to = Colors.Transparent
                        ).toImage()
                    )
                )
                .margin(0.px, autoLength)
                .transformOrigin(TransformOrigin.Top)
                .animation(
                    ThreadSwingAnimation.toAnimation(
                        duration = 1.5.s,
                        timingFunction = AnimationTimingFunction.EaseInOut,
                        iterationCount = AnimationIterationCount.Infinite
                    )
                )
                .then(Modifier.animation(
                    ThreadWaveAnimation.toAnimation(
                        duration = 0.8.s,
                        timingFunction = AnimationTimingFunction.EaseInOut,
                        iterationCount = AnimationIterationCount.Infinite
                    )
                )
                )
                .then(
                    Modifier.animation(
                        ThreadStretchAnimation.toAnimation(
                            duration = 1.2.s,
                            timingFunction = AnimationTimingFunction.EaseInOut,
                            iterationCount = AnimationIterationCount.Infinite
                        )
                    )
                )
        }

        // Staggered animations for thread segments
        (1..10).forEach { index ->
            cssRule(":nth-child($index):hover") {
                val delay = (index * 0.05).s
                Modifier
                    .opacity(1.0 - (index * 0.08))
                    .styleModifier {
                        property("animation-delay", "${delay.value}${delay.unit}, ${delay.value}${delay.unit}, ${delay.value}${delay.unit}")
                    }
            }
        }
    }