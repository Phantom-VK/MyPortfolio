package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.LanguageButtonStyle
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.background
import org.jetbrains.compose.web.css.fontFamily
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Code
import org.jetbrains.compose.web.dom.Pre
import org.jetbrains.compose.web.dom.Text

@Composable
fun CodeBox(colorMode: ColorMode) {
    var selectedLang = remember { mutableStateOf(Res.Selected.LANGUAGE) }

    fun updateSelectedLanguage(newLanguage: String) {
        selectedLang.value = newLanguage
        Res.Selected.LANGUAGE = newLanguage
    }

    Column(
        modifier = Modifier
            .displayIfAtLeast(Breakpoint.MD)
            .fillMaxWidth()
            .background(Res.Theme.GREY_BACKGROUND.color)
            .borderRadius(topLeft = 0.px, topRightAndBottomLeft = 12.px, bottomRight = 12.px),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .backgroundColor(
                    if (colorMode.isLight) Res.Theme.LIGHT_THEME_BACKGROUND.color
                    else Res.Theme.DARK_THEME_BACKGROUND.color
                ),
            horizontalArrangement = Arrangement.Start
        ) {
            LanguageButton(
                language = "Python"
            ) {
                updateSelectedLanguage("language-python")
            }

            LanguageButton(
                language = "Java"
            ) {
                updateSelectedLanguage("language-java")
            }

            LanguageButton(
                language = "Kotlin"
            ) {
                updateSelectedLanguage("language-kotlin")
            }
        }

        CodeBlock(selectedLang.value)
    }
}

@Composable
fun LanguageButton(language: String, onClick: () -> Unit) {
    Button(
        modifier = LanguageButtonStyle
            .toModifier()
            .background(Res.Theme.GREY_BACKGROUND.color)
            .borderRadius(topLeft = 20.px, topRight = 20.px, bottomLeft = 0.px, bottomRight = 0.px),
        onClick = { onClick() }
    ) {
        SpanText(
            text = language,
            modifier = Modifier.color(Colors.White)
        )
    }
}

@Composable
fun CodeBlock(codeClass:String) {
    Pre(
        attrs = Modifier
            .fillMaxWidth()
            .height(300.px)
            .background(Res.Theme.GREY_BACKGROUND.color)
            .overflow(Overflow.Auto)
            .toAttrs()
    ) {

        Code(attrs = {
            classes(codeClass).also {
                style {
                    fontFamily("Menlo", "monospace")
                    background("transparent")
                }
            }
        }) {



            Text(
                when (codeClass) {

                    "language-python" -> Res.String.PYTHON_CODE
                    "language-java" -> Res.String.JAVA_CODE
                    "language-kotlin" -> Res.String.KOBWEB_CODE
                    else -> Res.String.PYTHON_CODE
                }
            )
        }
    }
}