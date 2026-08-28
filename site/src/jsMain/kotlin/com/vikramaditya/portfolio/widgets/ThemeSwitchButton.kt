package com.vikramaditya.portfolio.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.IconButtonStyle
import com.vikramaditya.portfolio.styles.ThemeIconStyle
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.utils.theme.Space
import org.jetbrains.compose.web.css.px

/**
 * Sizes come from breakpoint blocks rather than `rememberBreakpoint()`, which
 * also fixes a real bug: the old `when (breakpoint)` matched exact values, so
 * `Breakpoint.LG` fell through to `else` and got the largest padding of all.
 */
val ThemeSwitchStyle = CssStyle {
    base { Modifier.padding(Space.sm).cursor(Cursor.Pointer).pointerEvents(PointerEvents.Auto) }
    Breakpoint.MD { Modifier.padding(10.px) }
}

val ThemeSwitchIconStyle = CssStyle {
    base { Modifier.size(18.px) }
    Breakpoint.MD { Modifier.size(22.px) }
}

@Composable
fun ThemeSwitchButton(
    colorMode: ColorMode,
    onClick: () -> Unit
) {
    Box(
        modifier = IconButtonStyle.toModifier()
            .then(ThemeIconStyle.toModifier())
            .then(ThemeSwitchStyle.toModifier())
            .role("button")
            .tabIndex(0)
            .ariaLabel(if (colorMode.isLight) "Switch to dark mode" else "Switch to light mode")
            .onClick { onClick() }
            .onKeyDown { event ->
                val key = event.nativeEvent.asDynamic().key as? String
                if (key == "Enter" || key == " ") {
                    event.preventDefault()
                    onClick()
                }
            }
    ) {
        Image(
            modifier = ThemeSwitchIconStyle.toModifier(),
            src = if (colorMode.isLight) Res.Icon.SUN else Res.Icon.MOON,
            alt = ""
        )
    }
}
