package com.vikramaditya.portfolio

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.theme.colors.ColorMode

@InitSilk
fun setInitialColorMode(ctx: InitSilkContext) {
    ctx.config.initialColorMode = ColorMode.DARK
}

/**
 * Root entry point required by Kobweb. It boots Silk (the UI toolkit) and lets
 * the generated router render any @Page composables we define.
 */
@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
    SilkApp {
        content()
    }
}
