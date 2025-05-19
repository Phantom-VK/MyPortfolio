package com.vikramaditya.portfolio.components


import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.framework.annotations.DelicateApi
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.utils.Res
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.w3c.dom.SMOOTH
import org.w3c.dom.ScrollBehavior
import org.w3c.dom.ScrollToOptions

val LetterSpacingPulse = Keyframes {
    0.percent { Modifier.padding(topBottom = 0.em) }
    50.percent { Modifier.padding(topBottom = 0.4 .em) }
    100.percent { Modifier.padding(topBottom = 0.em) }
}
val HoverPulseStyle = CssStyle {
    hover {
        Modifier.animation(
            LetterSpacingPulse.toAnimation(
                duration = 600.ms,
                timingFunction = AnimationTimingFunction.EaseInOut
            )
        )
    }
}



@OptIn(DelicateApi::class)
@Composable
fun BackToTopButton() {
    var scroll: Double? by remember { mutableStateOf(null) }
    val colormode by ColorMode.currentState

    LaunchedEffect(Unit) {
        window.addEventListener("scroll", {
            scroll = document.documentElement?.scrollTop
        })
    }

    val show = scroll != null && scroll!! > 400.0

    Column(
        modifier = Modifier
            .position(Position.Fixed)
            .right(8.px)
            .bottom(16.px)
            .backgroundColor(Colors.Transparent)
            .padding(4.px)
            .zIndex(5)
            .pointerEvents(if (show) PointerEvents.Auto else PointerEvents.None)
            .visibility(if (show) Visibility.Visible else Visibility.Hidden)
            .onClick {
                document.documentElement?.scroll(
                    ScrollToOptions(
                        top = 0.0,
                        behavior = ScrollBehavior.SMOOTH
                    )
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val textList = listOf(
            "↿", "B", "A", "C", "K", "", "T", "O", "", "T", "O", "P"
        )
        textList.forEach { char ->
            SpanText(
                char,
                modifier = HoverPulseStyle.toModifier()
                    .color(if(colormode.isDark) Res.Theme.THEME_GREEN_NEON.color else Res.Theme.GREY_BACKGROUND.color)
                    .fontFamily("Share Tech Mono")
                    .fontSize(1.em)
                    .textAlign(TextAlign.Start)
                    .fontWeight(FontWeight.SemiBold)
                    .padding(topBottom = 1.px)
            )
        }
    }
}

