package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.varabyte.kobweb.compose.css.BackgroundBlendMode
import com.varabyte.kobweb.compose.css.MixBlendMode
import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.utils.Res
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.w3c.dom.events.MouseEvent

@Composable
fun MatrixCursor() {
    val cursorX = remember { mutableStateOf(0.0) }
    val cursorY = remember { mutableStateOf(0.0) }
    val colorMode by ColorMode.currentState

    LaunchedEffect(Unit) {
        window.addEventListener("mousemove", { event ->
            val e = event as MouseEvent
            cursorX.value = e.clientX.toDouble()
            cursorY.value = e.clientY.toDouble()
        })
    }

    // The custom cursor circle
    Box(
        Modifier
            .position(position = Position.Fixed)
            .left(cursorX.value.px - 10.px)
            .top(cursorY.value.px - 10.px)
            .size(40.px)
            .zIndex(9999)
            .border(2.px, LineStyle.Solid, if(colorMode.isDark) Res.Theme.THEME_GREEN_NEON.color else Res.Theme.GREY_BACKGROUND.color)
            .backgroundColor(

                Color.rgba(255, 255, 255, 0.1f)
            )
            .borderRadius(50.percent)
            .pointerEvents(PointerEvents.None)
            .mixBlendMode(MixBlendMode.Difference)
            .transition(Transition.of(
                "transform",
                duration = 150.ms,
                timingFunction = TransitionTimingFunction.Ease
            ))

    )
}
