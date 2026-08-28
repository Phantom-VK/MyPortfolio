package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.varabyte.kobweb.browser.dom.observers.IntersectionObserver
import com.varabyte.kobweb.compose.dom.disposableRef
import com.varabyte.kobweb.compose.dom.registerRefScope
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.vikramaditya.portfolio.utils.rememberPrefersReducedMotion
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLElement

/**
 * The entry state: slightly low and transparent. The revealed state is applied
 * by adding [REVEALED_CLASS], which is a plain class toggle rather than Compose
 * state, so a section coming into view costs one class mutation and no
 * recomposition of its subtree.
 */
private const val REVEALED_CLASS = "is-revealed"

val RevealStyle = CssStyle {
    base {
        Modifier.fillMaxWidth().styleModifier {
            property("opacity", "0")
            property("transform", "translate3d(0, 16px, 0)")
            property("transition", "opacity 520ms cubic-bezier(0.16, 1, 0.3, 1), transform 520ms cubic-bezier(0.16, 1, 0.3, 1)")
            property("transition-delay", "var(--reveal-delay, 0ms)")
            // Hints the compositor without pinning a layer forever.
            property("will-change", "opacity, transform")
        }
    }
    cssRule(".$REVEALED_CLASS") {
        Modifier.styleModifier {
            property("opacity", "1")
            property("transform", "none")
            property("will-change", "auto")
        }
    }
}

/** No transform, no transition, nothing hidden. Used when JS or motion is unavailable. */
val RevealStaticStyle = CssStyle.base {
    Modifier.fillMaxWidth()
}

/**
 * Reveals [content] once, the first time it scrolls into view.
 *
 * Uses `IntersectionObserver` rather than a scroll listener: a scroll handler
 * runs on every scroll event for the life of the page, while the observer fires
 * once per element and is then disconnected.
 *
 * The observer also *guarantees* the reveal. If it never ran (no JavaScript, or
 * a browser that fails to construct it) the content would stay at `opacity: 0`
 * forever, so the reduced-motion and no-observer paths both render the plain
 * style with nothing hidden.
 */
@Composable
fun Reveal(
    delayMs: Int = 0,
    content: @Composable () -> Unit,
) {
    val reduced = rememberPrefersReducedMotion()
    val elState = remember { mutableStateOf<HTMLElement?>(null) }
    val el = elState.value

    DisposableEffect(el, reduced) {
        val target = el
        if (target == null || reduced) {
            onDispose { }
        } else {
            val observer = runCatching {
                IntersectionObserver(
                    IntersectionObserver.Options(thresholds = listOf(0.12))
                ) { entries, obs ->
                    entries.forEach { entry ->
                        if (entry.isIntersecting) {
                            entry.target.classList.add(REVEALED_CLASS)
                            obs.unobserve(entry.target)
                        }
                    }
                }
            }.getOrNull()

            if (observer == null) {
                // No observer means nothing would ever un-hide this, so show it.
                target.classList.add(REVEALED_CLASS)
                onDispose { }
            } else {
                observer.observe(target)
                onDispose { observer.disconnect() }
            }
        }
    }

    Div(
        attrs = (if (reduced) RevealStaticStyle.toModifier() else RevealStyle.toModifier())
            .styleModifier { property("--reveal-delay", "${delayMs}ms") }
            .toAttrs()
    ) {
        content()

        registerRefScope(
            disposableRef { element ->
                elState.value = element
                onDispose { elState.value = null }
            }
        )
    }
}
