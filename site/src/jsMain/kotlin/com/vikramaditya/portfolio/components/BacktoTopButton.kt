package com.vikramaditya.portfolio.components


import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.utils.theme.Font
import com.vikramaditya.portfolio.utils.theme.Space
import com.vikramaditya.portfolio.utils.theme.Type
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.fontFace
import com.vikramaditya.portfolio.utils.theme.textStyle
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.w3c.dom.SMOOTH
import org.w3c.dom.ScrollBehavior
import org.w3c.dom.ScrollToOptions
import org.w3c.dom.events.Event

/**
 * Nudges the column of letters sideways under the cursor.
 *
 * The previous version animated vertical padding, which re-laid-out twelve
 * elements every frame of the hover. `transform` is composited, so this costs
 * nothing on the main thread.
 */
val LetterNudgeStyle = CssStyle {
    base {
        Modifier.transition(Transition.of("transform", 200.ms))
    }
    hover {
        Modifier.transform { translateX((-4).px) }
    }
}

val BackToTopStyle = CssStyle.base {
    Modifier
        .position(Position.Fixed)
        .right(Space.sm)
        .bottom(Space.lg)
        .padding(Space.xs)
        .zIndex(5)
        .transition(Transition.of("opacity", 220.ms))
}

@Composable
fun BackToTopButton() {
    val c = colors(ColorMode.current)
    var show by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        var ticking = false

        fun evaluate() {
            ticking = false
            val top = document.documentElement?.scrollTop ?: 0.0
            val next = top > 400.0
            // Only writes state when the button actually crosses the threshold,
            // so scrolling does not recompose on every event.
            if (next != show) show = next
        }

        val listener: (Event) -> Unit = {
            if (!ticking) {
                ticking = true
                window.requestAnimationFrame { evaluate() }
            }
        }
        window.addEventListener("scroll", listener)
        evaluate()
        // The previous version never removed this listener, so every mount
        // added another one for the life of the page.
        onDispose { window.removeEventListener("scroll", listener) }
    }

    fun scrollToTop() {
        document.documentElement?.scroll(
            ScrollToOptions(top = 0.0, behavior = ScrollBehavior.SMOOTH)
        )
    }

    Column(
        modifier = BackToTopStyle.toModifier()
            .opacity(if (show) 1f else 0f)
            .pointerEvents(if (show) PointerEvents.Auto else PointerEvents.None)
            .role("button")
            .tabIndex(if (show) 0 else -1)
            .ariaLabel("Back to top")
            .ariaHidden(!show)
            .onClick { scrollToTop() }
            .onKeyDown { event ->
                val key = event.nativeEvent.asDynamic().key as? String
                if (key == "Enter" || key == " ") {
                    event.preventDefault()
                    scrollToTop()
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        listOf("↑", "B", "A", "C", "K", " ", "T", "O", " ", "T", "O", "P").forEach { char ->
            SpanText(
                char,
                modifier = LetterNudgeStyle.toModifier()
                    .ariaHidden()
                    .color(c.accent)
                    .fontFace(Font.DISPLAY)
                    .textStyle(Type.Micro)
                    .styleModifier { property("line-height", "1.15") }
            )
        }
    }
}
