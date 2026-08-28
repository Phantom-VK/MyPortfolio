package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import androidx.compose.web.events.SyntheticMouseEvent
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.functions.dropShadow
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.focusVisible
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.IconButton
import com.vikramaditya.portfolio.components.SocialIcon
import com.vikramaditya.portfolio.styles.SocialIconStyle
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.utils.theme.Font
import com.vikramaditya.portfolio.utils.theme.Radius
import com.vikramaditya.portfolio.utils.theme.Space
import com.vikramaditya.portfolio.utils.theme.Type
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.fontFace
import com.vikramaditya.portfolio.utils.theme.textStyle
import com.vikramaditya.portfolio.widgets.CodeBox
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1

/**
 * The hero, as one flex container that changes direction at MD rather than two
 * separately-composed branches behind `rememberBreakpoint()`. Same layout, but
 * emitted as static CSS: no recomposition on resize, and it is correct in the
 * statically exported HTML before any JavaScript runs.
 */
val HeroStyle = CssStyle {
    base {
        Modifier.fillMaxWidth().styleModifier {
            property("display", "flex")
            property("flex-direction", "column")
            property("align-items", "center")
            property("gap", "24px")
            property("padding", "40px 16px")
        }
    }
    Breakpoint.MD {
        Modifier.styleModifier {
            property("flex-direction", "row")
            property("align-items", "center")
            property("justify-content", "space-between")
            property("gap", "32px")
            property("padding", "64px 16px")
        }
    }
}

/** `min-width: 0` is what stops the code box from forcing the flex row wider than the page. */
val HeroCopyStyle = CssStyle {
    base {
        Modifier.fillMaxWidth().styleModifier {
            property("display", "flex")
            property("flex-direction", "column")
            property("gap", "16px")
            property("min-width", "0")
        }
    }
    Breakpoint.MD {
        Modifier.styleModifier { property("flex", "1 1 auto") }
    }
}

/**
 * `order: -1` floats the photo above the copy on phones while keeping the
 * headline first in the DOM, which is the order a screen reader should hear.
 */
val HeroMediaStyle = CssStyle {
    base {
        Modifier
            .size(240.px)
            .borderRadius(Radius.default)
            .overflow(Overflow.Hidden)
            .filter(dropShadow(0.px, 0.px, 12.px, colors(colorMode).accent))
            .styleModifier {
                property("order", "-1")
                property("flex", "0 0 auto")
            }
    }
    Breakpoint.MD {
        Modifier.size(280.px).styleModifier { property("order", "0") }
    }
    Breakpoint.LG {
        Modifier.size(360.px)
    }
}

val HeroPortraitStyle = CssStyle {
    base {
        Modifier.fillMaxSize().styleModifier {
            property("object-fit", "cover")
            property("object-position", "center top")
        }
    }
    Breakpoint.MD {
        Modifier.styleModifier {
            property("object-fit", "contain")
            property("object-position", "center")
        }
    }
}

val HeroNameStyle = CssStyle {
    base { Modifier.textStyle(Type.Display) }
    Breakpoint.MD { Modifier.textStyle(Type.Hero) }
}

/** The row of actions under the hero copy: primary CTA first, then social links. */
val HeroActionsStyle = CssStyle.base {
    Modifier.fillMaxWidth().styleModifier {
        property("display", "flex")
        property("flex-wrap", "wrap")
        property("align-items", "center")
        property("gap", "12px")
    }
}

val AccentButtonStyle = CssStyle {
    base {
        val c = colors(colorMode)
        Modifier
            .cursor(Cursor.Pointer)
            .backgroundColor(c.accent)
            .color(c.onAccent)
            .padding(leftRight = Space.xl, topBottom = Space.md)
            .borderRadius(Radius.default)
            .border(0.px)
            .transition(Transition.of("background-color", 200.ms))
    }
    hover {
        Modifier.backgroundColor(colors(colorMode).signal)
    }
    focusVisible {
        Modifier
            .outline(2.px, LineStyle.Solid, colors(colorMode).signal)
            .styleModifier { property("outline-offset", "2px") }
    }
}

@Composable
fun ProfileCard() {
    val c = colors(ColorMode.current)
    val ctx = rememberPageContext()

    Div(attrs = HeroStyle.toModifier().id("home").toAttrs()) {
        Div(attrs = HeroCopyStyle.toModifier().toAttrs()) {
            H1(
                attrs = HeroNameStyle.toModifier()
                    .fillMaxWidth()
                    .margin(0.px)
                    .fontFace(Font.DISPLAY)
                    .color(c.textPrimary)
                    .styleModifier { property("overflow-wrap", "break-word") }
                    .toAttrs()
            ) {
                org.jetbrains.compose.web.dom.Text("Vikramaditya Khupse")
            }

            // `display: block` plus an explicit width is what actually forces this
            // to wrap: a bare inline SpanText inside a flex column can end up
            // sized to its own text run rather than the container, and a mono
            // string this long then runs straight off the edge on a phone.
            SpanText(
                "Full Stack · AI/ML · DevOps & Cloud",
                Modifier
                    .fillMaxWidth()
                    .textStyle(Type.Title)
                    .fontFace(Font.BODY)
                    .color(c.accent)
                    .styleModifier {
                        property("display", "block")
                        property("overflow-wrap", "break-word")
                    }
            )

            CodeBox()

            Div(attrs = HeroActionsStyle.toModifier().toAttrs()) {
                AccentButton(text = "Resume") { ctx.router.navigateTo(Res.String.RESUME_URL) }
                SocialLinks()
            }
        }

        ProfileImage()
    }
}

@Composable
private fun ProfileImage() {
    val colorMode = ColorMode.current

    Box(modifier = HeroMediaStyle.toModifier()) {
        Image(
            modifier = HeroPortraitStyle.toModifier(),
            src = if (colorMode.isDark) Res.Image.PROFILE_PHOTO_GREEN else Res.Image.PROFILE_PHOTO_REGULAR,
            alt = "Photo of Vikramaditya Khupse"
        )
    }
}

@Composable
fun AccentButton(text: String, modifier: Modifier = Modifier, onClick: (SyntheticMouseEvent) -> Unit) {
    Button(
        onClick = onClick,
        modifier = AccentButtonStyle.toModifier().then(modifier)
    ) {
        SpanText(text, Modifier.fontFace(Font.DISPLAY).textStyle(Type.Small))
    }
}

@Composable
fun SocialLinks() {
    val colorMode = ColorMode.current

    Box(
        modifier = Modifier.styleModifier {
            property("display", "flex")
            property("align-items", "center")
            property("gap", "12px")
        }
    ) {
        SocialIcon.entries.filter {
            // Each social icon ships in two colourways; pick the one that reads
            // against the current ground.
            if (colorMode.isLight) !it.name.contains("Light") else it.name.contains("Light")
        }.forEach {
            IconButton(
                modifier = SocialIconStyle.toModifier().cursor(Cursor.Pointer),
                icon = it.icon,
                link = it.link,
                label = it.label
            )
        }
    }
}
