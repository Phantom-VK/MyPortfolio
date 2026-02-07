package com.vikramaditya.portfolio

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp

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
