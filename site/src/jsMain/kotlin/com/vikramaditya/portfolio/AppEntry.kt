package com.vikramaditya.portfolio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.loadFromLocalStorage
import com.varabyte.kobweb.silk.theme.colors.saveToLocalStorage
import com.varabyte.kobweb.silk.theme.colors.systemPreference
import com.vikramaditya.portfolio.utils.theme.Font
import com.vikramaditya.portfolio.utils.theme.Type
import com.vikramaditya.portfolio.utils.theme.fontFace
import com.vikramaditya.portfolio.utils.theme.textStyle

/**
 * Honour the visitor's saved choice, falling back to their OS preference rather
 * than forcing dark. Previously this hardcoded DARK, so the theme toggle reset
 * on every reload.
 */
@InitSilk
fun initColorMode(ctx: InitSilkContext) {
    ctx.config.initialColorMode = ColorMode.loadFromLocalStorage() ?: ColorMode.systemPreference
}

/**
 * Give the document a real base font. Without this, any node that does not set
 * `fontFamily` itself falls back to the browser default, which is what several
 * sections were silently doing.
 */
@InitSilk
fun initBaseStyles(ctx: InitSilkContext) {
    ctx.stylesheet.registerStyleBase("body") {
        Modifier
            .fontFace(Font.BODY)
            .textStyle(Type.Body)
    }
}

/**
 * Root entry point required by Kobweb. It boots Silk (the UI toolkit) and lets
 * the generated router render any @Page composables we define.
 */
@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
    SilkApp {
        val colorMode = ColorMode.current
        LaunchedEffect(colorMode) { colorMode.saveToLocalStorage() }
        content()
    }
}
