package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.Width
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.focusVisible
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.ContactMeButton
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
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

/**
 * One flex row that wraps, instead of a `rememberBreakpoint()` branch between a
 * Column and a Row. Same result at both sizes, as static CSS, with no
 * recomposition on resize and no `@OptIn(DelicateApi::class)`.
 */
val FooterRowStyle = CssStyle {
    base {
        Modifier.fillMaxWidth().styleModifier {
            property("display", "flex")
            property("flex-direction", "column")
            property("align-items", "center")
            property("justify-content", "center")
            property("gap", "16px")
        }
    }
    Breakpoint.MD {
        Modifier.styleModifier {
            property("flex-direction", "row")
            property("flex-wrap", "wrap")
            property("justify-content", "space-evenly")
            property("gap", "24px")
        }
    }
}

val KobwebLinkStyle = CssStyle {
    base {
        Modifier
            .cursor(Cursor.Pointer)
            .borderRadius(Radius.default)
            .padding(leftRight = Space.md, topBottom = Space.sm)
    }
    focusVisible {
        Modifier
            .outline(2.px, LineStyle.Solid, colors(colorMode).signal)
            .styleModifier { property("outline-offset", "2px") }
    }
}

val FooterStyle = CssStyle.base {
    val c = colors(colorMode)
    Modifier
        .fillMaxWidth()
        .backgroundColor(c.surfaceRaised)
        .borderTop(Stroke.hairline, LineStyle.Solid, c.borderStrong)
        .padding(leftRight = Space.lg, topBottom = Space.xxl)
        .styleModifier { property("scroll-margin-top", "104px") }
}

@Composable
fun Footer() {
    Column(
        modifier = FooterStyle.toModifier().id("contact"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Div(attrs = FooterRowStyle.toModifier().toAttrs()) {
            ContactMeButton(email = Res.String.MY_EMAIL)
            KotlinText()
            MadeWithKobweb()
        }
    }
}

@Composable
private fun MadeWithKobweb() {
    val c = colors(ColorMode.current)

    fun openKobwebSite() {
        window.open("https://kobweb.varabyte.com/", "_blank")
    }

    Row(
        modifier = KobwebLinkStyle.toModifier()
            .role("link")
            .tabIndex(0)
            .ariaLabel("Built with Kobweb, opens in a new tab")
            .onClick { openKobwebSite() }
            .onKeyDown { event ->
                val key = event.nativeEvent.asDynamic().key as? String
                if (key == "Enter" || key == " ") {
                    event.preventDefault()
                    openKobwebSite()
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        SpanText(
            "Built with",
            Modifier
                .fontFace(Font.DISPLAY)
                .textStyle(Type.Small)
                .color(c.textSecondary)
        )
        Image(
            src = Res.Logo.KOBWEB_LOGO,
            alt = "",
            modifier = Modifier
                .height(28.px)
                .width(Width.FitContent)
                .margin(left = Space.sm)
        )
    }
}

@Composable
fun KotlinText(modifier: Modifier = Modifier) {
    val c = colors(ColorMode.current)
    SpanText(
        "Developed entirely with Kotlin",
        modifier = modifier.then(
            Modifier
                .fontFace(Font.DISPLAY)
                .textAlign(TextAlign.Center)
                .textStyle(Type.Small)
                .color(c.accent)
        )
    )
}
