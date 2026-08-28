package com.vikramaditya.portfolio.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.web.events.SyntheticMouseEvent
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.CaretBlink
import com.vikramaditya.portfolio.styles.LanguageButtonStyle
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.utils.theme.Font
import com.vikramaditya.portfolio.utils.theme.Radius
import com.vikramaditya.portfolio.utils.theme.Space
import com.vikramaditya.portfolio.utils.theme.Stroke
import com.vikramaditya.portfolio.utils.theme.Type
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.fontFace
import com.vikramaditya.portfolio.utils.theme.textStyle
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s
import org.jetbrains.compose.web.dom.Code
import org.jetbrains.compose.web.dom.Pre
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import kotlin.time.Duration.Companion.milliseconds

/**
 * Fluid, not pinned at 800px. The old fixed width plus a `<pre>` full of long
 * Python lines pushed the whole page into horizontal scroll on a phone; the
 * shell now caps at 800px but never exceeds the column it sits in.
 */
val CodeBoxStyle = CssStyle.base {
    val c = colors(colorMode)
    Modifier
        .fillMaxWidth()
        .backgroundColor(c.surfaceRaised)
        .border(Stroke.hairline, LineStyle.Solid, c.border)
        .borderRadius(Radius.default)
        .margin(bottom = Space.xl)
        .styleModifier {
            property("max-width", "min(100%, 800px)")
            // A flex/grid child will happily blow past its track unless told
            // it may shrink; this is what actually keeps the scroll internal.
            property("min-width", "0")
        }
}

/** The code viewport. Overflow is contained here rather than on the page. */
val CodeSurfaceStyle = CssStyle.base {
    val c = colors(colorMode)
    Modifier
        .fillMaxWidth()
        .height(300.px)
        .padding(Space.lg)
        .margin(0.px)
        .backgroundColor(c.surface)
        .overflow(Overflow.Auto)
        .fontFace(Font.BODY)
        .textStyle(Type.Small)
        .color(c.accent)
        .styleModifier {
            property("min-width", "0")
            property("font-variant-ligatures", "normal")
            property("tab-size", "4")
        }
}

private data class CodeTab(val label: String, val key: String, val code: String)

@Composable
fun CodeBox() {
    val c = colors(ColorMode.current)

    val tabs = remember {
        listOf(
            CodeTab("Python", "language-python", Res.String.PYTHON_CODE),
            CodeTab("Java", "language-java", Res.String.JAVA_CODE),
            CodeTab("Kotlin", "language-kotlin", Res.String.KOBWEB_CODE),
        )
    }

    var selectedKey by remember { mutableStateOf(tabs.first().key) }
    var typedText by remember { mutableStateOf("") }
    var isTyping by remember { mutableStateOf(false) }

    LaunchedEffect(selectedKey) {
        val fullCode = tabs.first { it.key == selectedKey }.code
        typedText = ""
        isTyping = true
        fullCode.forEachIndexed { index, _ ->
            typedText = fullCode.take(index + 1)
            delay(18.milliseconds)
        }
        isTyping = false
    }

    Column(
        modifier = CodeBoxStyle.toModifier(),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .backgroundColor(c.surfaceRaised)
                .borderBottom(Stroke.hairline, LineStyle.Solid, c.border),
            horizontalArrangement = Arrangement.Start
        ) {
            tabs.forEach { tab ->
                LanguageButton(
                    language = tab.label,
                    isSelected = selectedKey == tab.key,
                ) { selectedKey = tab.key }
            }
        }

        Pre(attrs = CodeSurfaceStyle.toModifier().toAttrs()) {
            Code(attrs = { classes(selectedKey) }) {
                Text(typedText)
                if (isTyping) {
                    // A separate caret element, so the animation is not fighting
                    // the text node that is being rewritten each keystroke.
                    Span(
                        attrs = Modifier
                            .color(c.signal)
                            .animation(
                                CaretBlink.toAnimation(
                                    duration = 1.s,
                                    timingFunction = AnimationTimingFunction.StepEnd,
                                    iterationCount = AnimationIterationCount.Infinite,
                                )
                            )
                            .toAttrs { attr("aria-hidden", "true") }
                    ) {
                        Text("█")
                    }
                }
            }
        }
    }
}

@Composable
private fun LanguageButton(language: String, isSelected: Boolean, onClick: (SyntheticMouseEvent) -> Unit) {
    val c = colors(ColorMode.current)
    Button(
        modifier = LanguageButtonStyle
            .toModifier()
            .cursor(Cursor.Pointer)
            .backgroundColor(if (isSelected) c.accent else Colors.Transparent)
            .color(if (isSelected) c.onAccent else c.textSecondary)
            .padding(topBottom = Space.md, leftRight = Space.lg)
            .borderRadius(0.px)
            .margin(0.px)
            .border(0.px)
            .fontFace(Font.DISPLAY)
            .textStyle(Type.Small),
        onClick = onClick
    ) {
        SpanText(text = language)
    }
}
