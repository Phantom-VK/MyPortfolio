package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.css.BoxShadow
import com.varabyte.kobweb.compose.css.CSSTimeNumericValue
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.utils.Res
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.AnimationPlayState
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.Color.blue
import org.jetbrains.compose.web.css.Color.green
import org.jetbrains.compose.web.css.Color.red
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.time.Duration


//Here I create my keyframe
val SmoothShiftCircularKeyframes = Keyframes {
    val radius = 10.px
    val blurRadius = 10.px

    0.percent {
        Modifier.boxShadow(
            BoxShadow.of(
                offsetX = radius,
                offsetY = 0.px,
                blurRadius =blurRadius,
                inset = true,
                color = Color.rgb(r = 255, g = 0, b = 0)
            )
        )
    }
    12.5.percent {
        Modifier.boxShadow(
            BoxShadow.of(
                offsetX = (radius.value * 0.923).px, // cos(22.5°) ≈ 0.923
                offsetY = (radius.value * 0.382).px, // sin(22.5°) ≈ 0.382
                blurRadius = blurRadius,
                inset = true,
                color = Color.rgb(r = 255, g = 127, b = 0)
            )
        )
    }
    25.percent {
        Modifier.boxShadow(
            BoxShadow.of(
                offsetX = (radius.value * 0.707).px, // cos(45°) ≈ 0.707
                offsetY = (radius.value * 0.707).px, // sin(45°) ≈ 0.707
                blurRadius = blurRadius,
                inset = true,
                color = Color.rgb(r = 0, g = 255, b = 0)
            )
        )
    }
    37.5.percent {
        Modifier.boxShadow(
            BoxShadow.of(
                offsetX = (radius.value * 0.382).px, // cos(67.5°) ≈ 0.382
                offsetY = (radius.value * 0.923).px, // sin(67.5°) ≈ 0.923
                blurRadius = blurRadius,
                inset = true,
                color = Color.rgb(r = 0, g = 255, b = 127)
            )
        )
    }
    50.percent {
        Modifier.boxShadow(
            BoxShadow.of(
                offsetX = 0.px,
                offsetY = radius,
                blurRadius = blurRadius,
                inset = true,
                color = Color.rgb(r = 0, g = 0, b = 255)
            )
        )
    }
    62.5.percent {
        Modifier.boxShadow(
            BoxShadow.of(
                offsetX = (-radius.value * 0.382).px, // -cos(67.5°) ≈ -0.382
                offsetY = (radius.value * 0.923).px, // sin(67.5°) ≈ 0.923
                blurRadius = blurRadius,
                inset = true,
                color = Color.rgb(r = 127, g = 0, b = 255)
            )
        )
    }
    75.percent {
        Modifier.boxShadow(
            BoxShadow.of(
                offsetX = (-radius.value * 0.707).px, // -cos(45°) ≈ -0.707
                offsetY = (radius.value * 0.707).px, // sin(45°) ≈ 0.707
                blurRadius = blurRadius,
                inset = true,
                color = Color.rgb(r = 255, g = 255, b = 0)
            )
        )
    }
    87.5.percent {
        Modifier.boxShadow(
            BoxShadow.of(
                offsetX = (-radius.value * 0.923).px, // -cos(22.5°) ≈ -0.923
                offsetY = (radius.value * 0.382).px, // sin(22.5°) ≈ 0.382
                blurRadius = blurRadius,
                inset = true,
                color = Color.rgb(r = 255, g = 0, b = 127)
            )
        )
    }
    100.percent {
        Modifier.boxShadow(
            BoxShadow.of(
                offsetX = radius,
                offsetY = 0.px,
                blurRadius = blurRadius,
                inset = true,
                color = Color.rgb(r = 255, g = 0, b = 0)
            )
        )
    }
}


//My composable in which I want to add the animation
@Composable
fun AnimatedHoverBox(colorMode: ColorMode) {
    //Just some required variables for Maths approach
    val time = remember { mutableStateOf(0f) }
    val angle = remember { mutableStateOf(0.0) }

    val radius = 4.px
    //offsets for box shadow
    val offsetX = remember { mutableStateOf(0.px) }
    val offsetY = remember { mutableStateOf(0.px) }

    val red = remember { mutableStateOf(0) }
    val green = remember { mutableStateOf(0) }
    val blue = remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {

        //Here I am using a while loop to increase time manually

        while (true) {
            time.value += 0.4f
            // Reset time when it completes a full cycle (360 degrees)
            if (time.value >= 360f) time.value = 0f
            //angle will change as the time changes
            // Calculate the current angle in radians using the formula: radians = degrees * (PI / 180)
            angle.value = time.value * (PI / 180)

            // Using the mathematical equations of a circle (x = r * cos(θ), y = r * sin(θ)) to calculate
            // the x and y offsets of the box shadow. Here, 'radius' represents the distance from the center.
            offsetX.value = (radius.value * cos(angle.value)).px
            offsetY.value = (radius.value * sin(angle.value)).px

            // To create a dynamic color-changing effect like a gaming keyboard, we calculate the RGB (First I used single color, took help of AI for RGB)
            // values using cosine functions:
            // - Red: varies with cos(angle)
            // - Green: offset by 120° (2π/3 radians)
            // - Blue: offset by 240° (4π/3 radians)
            // This ensures smooth transitions between colors in a visually appealing gradient.
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

            //Used boxShadow to apply math approach (applied values of rgb and offsets that are changing with time)
            .boxShadow(
                BoxShadow.of(
                    offsetX = offsetX.value,
                    offsetY = offsetY.value,
                    blurRadius = 5.px,
                    inset = true,
                    color = Color.rgb(r = red.value, g = green.value, b = blue.value)

                )
            )

        //Used animation with keyframes
//            .animation(
//                SmoothShiftCircularKeyframes.toAnimation(
//                    duration = 2.s,
//                    timingFunction = AnimationTimingFunction.EaseInOut,
//                    iterationCount = AnimationIterationCount.Infinite
//                )
//            )
    )
}
