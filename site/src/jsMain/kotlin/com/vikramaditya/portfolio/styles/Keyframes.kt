package com.vikramaditya.portfolio.styles

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.silk.style.animation.Keyframes
import org.jetbrains.compose.web.css.percent

/**
 * Terminal caret blink. Shared by the typewriter in the code box and the
 * carousel's empty state, so there is one definition rather than a `blink`
 * name referenced from CSS that nothing ever declared.
 */
val CaretBlink = Keyframes {
    0.percent { Modifier.opacity(1) }
    50.percent { Modifier.opacity(0) }
    100.percent { Modifier.opacity(1) }
}
