package com.vikramaditya.portfolio

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.style.toModifier
import com.vikramaditya.portfolio.utils.Res
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
    SilkApp {


        Surface(SmoothColorStyle.toModifier()
            .minHeight(100.vh)) {
            content()
        }
    }
}


