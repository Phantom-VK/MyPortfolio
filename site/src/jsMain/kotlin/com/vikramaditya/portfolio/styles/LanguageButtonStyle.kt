package com.vikramaditya.portfolio.styles

import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import org.jetbrains.compose.web.css.ms

// Per-language accent color is applied inline (see CodeBox.kt) so it stays in
// sync with actual selection state; this style only owns the shared hover/transition.
val LanguageButtonStyle = CssStyle {
    base {
        Modifier
            .transition(
                Transition.of(property = "scaleY", duration = 300.ms),
            )
    }
    hover {
        Modifier.opacity(0.85f)
    }
}
