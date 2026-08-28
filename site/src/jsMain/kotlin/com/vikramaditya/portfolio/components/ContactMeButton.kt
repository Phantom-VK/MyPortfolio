package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.selectors.focusVisible
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.utils.theme.Font
import com.vikramaditya.portfolio.utils.theme.Radius
import com.vikramaditya.portfolio.utils.theme.Space
import com.vikramaditya.portfolio.utils.theme.Stroke
import com.vikramaditya.portfolio.utils.theme.Type
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.fontFace
import com.vikramaditya.portfolio.utils.theme.textStyle
import kotlinx.browser.window
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px

val ContactButtonStyle = CssStyle {
    base {
        val c = colors(colorMode)
        Modifier
            .cursor(Cursor.Pointer)
            .borderRadius(Radius.default)
            .border(Stroke.hairline, LineStyle.Solid, c.borderStrong)
            .padding(leftRight = Space.lg, topBottom = Space.md)
            .backgroundColor(c.surfaceRaised)
            .transition(
                Transition.of("border-color", 200.ms),
                Transition.of("background-color", 200.ms),
            )
    }
    hover {
        val c = colors(colorMode)
        Modifier.border(Stroke.hairline, LineStyle.Solid, c.signal).backgroundColor(c.surface)
    }
    focusVisible {
        Modifier
            .outline(2.px, LineStyle.Solid, colors(colorMode).signal)
            .styleModifier { property("outline-offset", "2px") }
    }
}

/**
 * Only two mail icons exist in the assets: white and neon green. Neither is
 * legible on the light surface, so light mode takes the white one and knocks it
 * to black with a filter rather than shipping a third file.
 */
val ContactIconStyle = CssStyle.base {
    Modifier
        .size(20.px)
        .thenIf(!colorMode.isDark) {
            Modifier.styleModifier { property("filter", "brightness(0)") }
        }
}

@Composable
fun ContactMeButton(email: String, modifier: Modifier = Modifier) {
    val colorMode = ColorMode.current
    val c = colors(colorMode)

    fun openEmail() {
        window.open(email, "_blank")
    }

    Row(
        modifier = ContactButtonStyle.toModifier()
            .then(modifier)
            .role("link")
            .tabIndex(0)
            .ariaLabel("Contact me by email")
            .onClick { openEmail() }
            .onKeyDown { event ->
                val key = event.nativeEvent.asDynamic().key as? String
                if (key == "Enter" || key == " ") {
                    event.preventDefault()
                    openEmail()
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            src = if (colorMode.isDark) Res.Icon.EMAIL_DARK else Res.Icon.EMAIL_LIGHT,
            alt = "",
            modifier = ContactIconStyle.toModifier()
        )
        SpanText(
            "Contact Me",
            Modifier
                .margin(left = Space.sm)
                .fontFace(Font.DISPLAY)
                .textStyle(Type.Small)
                .color(c.textPrimary)
        )
    }
}
