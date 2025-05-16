package com.vikramaditya.portfolio.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TransformOrigin
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.transform
import com.varabyte.kobweb.compose.ui.modifiers.transformOrigin
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.ThreadStyle
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.AnimationDirection
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s

val swingAnimation = Keyframes {
    0.percent {
        Modifier.Companion
            .transform { rotate((-5).deg) }
    }
    100.percent {
        Modifier.Companion
            .transform { rotate(5.deg) }
    }
}


@Composable
fun HangingText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.Companion
) {
    val colorMode = ColorMode.Companion.current
    Column(
        modifier = modifier
            .transform { translateX((-40).percent) }
            .zIndex(1000)
            .cursor(Cursor.Companion.Pointer)
            .onClick { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Physics-enhanced thread (multiple segments)
        repeat(10) { index ->
            Box(
                modifier = ThreadStyle.toModifier()
                    .styleModifier {
                        // Each segment gets progressively slower animation
                        property("animation-delay", "${index * 0.05}s")
                        property("opacity", "${1 - (index * 0.08)}")
                    }
            )
        }

        // The hanging text
        SpanText(
            text = text,
            modifier = Modifier.Companion
                .padding(10.px, 20.px)
                .backgroundColor(if (colorMode.isLight) Res.Theme.THEME_GREEN.color else Colors.White)
                .color(if (colorMode.isDark) Res.Theme.THEME_GREEN.color else Colors.White)
                .borderRadius(Res.Dimens.BORDER_RADIUS.px)
                .fontFamily("DM Sans")
                .fontWeight(FontWeight.Companion.Bold)
                .boxShadow(0.px, 4.px, 8.px, 2.px, Color.Companion.rgba(0, 0, 0, 0.2f))
                .animation(
                    swingAnimation.toAnimation(
                        duration = 2.5.s, // Slower swing
                        timingFunction = AnimationTimingFunction.Companion.EaseInOut,
                        iterationCount = AnimationIterationCount.Companion.Infinite,
                        direction = AnimationDirection.Companion.Alternate
                    )
                )
                .transformOrigin(TransformOrigin.Companion.Top)
        )
    }
}