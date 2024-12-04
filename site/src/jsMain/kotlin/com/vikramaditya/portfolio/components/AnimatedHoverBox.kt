package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.varabyte.kobweb.compose.css.BoxShadow
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.utils.Res
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.px
import com.varabyte.kobweb.compose.ui.graphics.Color
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnimatedHoverBox(colorMode: ColorMode) {
    val time = remember { mutableStateOf(0f) }
    val angle = remember { mutableStateOf(0.0) }

    val radius = 4.px
    val offsetX = remember { mutableStateOf(0.px) }
    val offsetY = remember { mutableStateOf(0.px) }

    val red = remember { mutableStateOf(0) }
    val green = remember { mutableStateOf(0) }
    val blue = remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            time.value += 0.3f
            if (time.value >= 360f) time.value = 0f
            angle.value = time.value * (PI / 180)

            offsetX.value = (radius.value * cos(angle.value)).px
            offsetY.value = (radius.value * sin(angle.value)).px

            red.value = ((128 + 127 * cos(angle.value)).toInt().coerceIn(0, 255))
            green.value = ((128 + 127 * cos(angle.value + 2 * PI / 3)).toInt().coerceIn(0, 255))
            blue.value = ((128 + 127 * cos(angle.value + 4 * PI / 3)).toInt().coerceIn(0, 255))

            delay(1L)
        }
    }

    Box(
        modifier = Modifier
            .size(200.px)
            .background(
                color = if (colorMode.isLight)
                    Res.Theme.LIGHT_THEME_BACKGROUND.color
                else
                    Res.Theme.DARK_THEME_BACKGROUND.color
            )
            .borderRadius(12.px)
            .boxShadow(
                BoxShadow.of(
                    offsetX = offsetX.value,
                    offsetY = offsetY.value,
                    blurRadius = 5.px,
                    inset = true,
                    color = Color.rgb(r = red.value, g = green.value, b = blue.value)

                )
            )
    )
}
