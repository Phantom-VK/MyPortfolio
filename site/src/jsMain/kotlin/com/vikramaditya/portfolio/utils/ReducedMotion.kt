package com.vikramaditya.portfolio.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.browser.window
import org.w3c.dom.events.Event

/** One-shot read, for imperative code that runs outside composition. */
fun prefersReducedMotion(): Boolean =
    window.matchMedia("(prefers-reduced-motion: reduce)").matches

/**
 * Tracks the visitor's reduced-motion preference, including changes made while
 * the page is open.
 *
 * Starts `false` on purpose. Kobweb renders these composables headless at export
 * time, and whatever the initial value is gets baked into the static HTML shipped
 * to every visitor. Correcting it client-side keeps the export neutral.
 */
@Composable
fun rememberPrefersReducedMotion(): Boolean {
    var reduced by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val query = window.matchMedia("(prefers-reduced-motion: reduce)")
        reduced = query.matches
        val listener: (Event) -> Unit = { reduced = query.matches }
        query.addEventListener("change", listener)
        onDispose { query.removeEventListener("change", listener) }
    }

    return reduced
}
