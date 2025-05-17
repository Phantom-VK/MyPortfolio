package com.vikramaditya.portfolio.components

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Animation
import com.varabyte.kobweb.compose.css.MixBlendMode
import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.w3c.dom.events.MouseEvent

@Composable
fun MatrixCursorFollower() {
    var posX by remember { mutableStateOf(0.0) }
    var posY by remember { mutableStateOf(0.0) }

    LaunchedEffect(Unit) {
        window.addEventListener("mousemove", { e ->
            val event = e as MouseEvent
            posX = event.clientX.toDouble()
            posY = event.clientY.toDouble()
        })
    }

    Box(
        modifier = Modifier
            .position(Position.Fixed)
            .left(posX.px - 2.px)
            .top(posY.px - 2.px)
            .size(7.px)
            .zIndex(9999)
            .backgroundColor(Color.rgb(0x00ff41))
            .borderRadius(50.percent)
            .pointerEvents(PointerEvents.None)
            .animation(
                Animation.of(
                    name = "fade",
                    duration = 0.5.s,
                    direction = AnimationDirection("forwards")
                )
            )
            .mixBlendMode(MixBlendMode.Screen)
            .styleModifier {
                property("transition", "top 0.05s, left 0.05s")
            }
    )
}
