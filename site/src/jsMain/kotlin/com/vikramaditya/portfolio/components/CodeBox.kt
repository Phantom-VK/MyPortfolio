package com.vikramaditya.portfolio.components

import androidx.compose.runtime.*
import androidx.compose.web.events.SyntheticMouseEvent
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.LanguageButtonStyle
import com.vikramaditya.portfolio.utils.Res
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import kotlin.time.Duration.Companion.milliseconds
import com.varabyte.kobweb.compose.ui.graphics.Color


@Composable
fun CodeBox(colorMode: ColorMode) {
    var selectedLang by remember { mutableStateOf("language-python") }
    var typedText by remember { mutableStateOf("") }
    var isTyping by remember { mutableStateOf(false) }

    // Reset typing animation when language changes
    LaunchedEffect(selectedLang) {
        typedText = ""
        isTyping = true
        val fullCode = when (selectedLang) {
            "language-python" -> Res.String.PYTHON_CODE
            "language-java" -> Res.String.JAVA_CODE
            "language-kotlin" -> Res.String.KOBWEB_CODE
            else -> Res.String.PYTHON_CODE
        }

        fullCode.forEachIndexed { index, _ ->
            typedText = fullCode.take(index + 1)
            delay(30.milliseconds) // Adjust typing speed
        }
        isTyping = false
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .maxWidth(800.px)
            .background(Res.Theme.GREY_BACKGROUND.color)
            .borderRadius(12.px)
            .boxShadow(
                BoxShadow.of(
                    0.px, 4.px, 8.px,
                    color = Color.rgba(r = 0, g = 0, b = 0, a= 0.1f)
                )
            )
            .margin(bottom = 24.px),
        verticalArrangement = Arrangement.Top
    ) {
        // Language selector tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (colorMode.isLight) Res.Theme.LIGHT_THEME_BACKGROUND.color
                    else Res.Theme.DARK_THEME_BACKGROUND.color
                )
                .borderRadius(topLeft = 12.px, topRight = 12.px),
            horizontalArrangement = Arrangement.Start
        ) {
            LanguageButton(
                language = "Python",
                isSelected = selectedLang == "language-python"
            ) {
                selectedLang = "language-python"
            }

            LanguageButton(
                language = "Java",
                isSelected = selectedLang == "language-java"
            ) {
                selectedLang = "language-java"
            }

            LanguageButton(
                language = "Kotlin",
                isSelected = selectedLang == "language-kotlin"
            ) {
                selectedLang = "language-kotlin"
            }
        }

        // Code block with typing effect
        Pre(
            attrs = Modifier
                .fillMaxWidth()
                .height(300.px)
                .padding(16.px)
                .background(Res.Theme.GREY_BACKGROUND.color)
                .borderRadius(bottomLeft = 12.px, bottomRight = 12.px)
                .overflow(Overflow.Auto)
                .toAttrs()
        ) {
            Code(attrs = {
                classes(selectedLang).also {
                    style {
                        fontFamily("JetBrains Mono")
                        property("font-variant-ligatures", "normal")
                        background("transparent")
                        color( Color.rgb(0x27AE60) )
                        lineHeight("1.5")
                    }
                }
            }) {
                // Blinking cursor effect
                if (isTyping) {
                    Span(
                        attrs = Modifier
                            .styleModifier {
                                property("border-right", "2px solid ${if (colorMode.isLight) "#2D3748" else "#E2E8F0"}")
                                property("animation", "blink 1s step-end infinite")
                            }
                            .toAttrs()
                    ) {
                        Text(typedText)
                    }
                } else {
                    Text(typedText)
                }
            }
        }
    }
}

@Composable
fun LanguageButton(language: String, isSelected: Boolean, onClick: (SyntheticMouseEvent) -> Unit) {
    Button(
        modifier = LanguageButtonStyle
            .toModifier()
            .background(if (isSelected) Res.Theme.PRIMARY_BUTTON.color else Colors.Transparent)
            .color(if (isSelected) Colors.White else Res.Theme.PRIMARY_BUTTON.color)
            .padding(12.px, 16.px)
            .borderRadius(topLeft = 10.px, topRight = 10.px)
            .margin(0.px)
            .border(width = 0.px)
            .fontWeight(FontWeight.Medium)
            .fontSize(14.px),
        onClick = onClick
    ) {
        SpanText(text = language)
    }
}