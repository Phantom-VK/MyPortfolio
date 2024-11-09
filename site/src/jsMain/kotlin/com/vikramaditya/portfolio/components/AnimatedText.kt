package com.vikramaditya.portfolio.components

import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.background
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fontFamily
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Code
import org.jetbrains.compose.web.dom.Pre
import org.jetbrains.compose.web.dom.Text
import kotlin.text.trimIndent

@Composable
fun AnimatedText(){
    Column(
        modifier = Modifier.displayIfAtLeast(Breakpoint.MD),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        KotlinCode(

            modifier = Modifier
                // Choose a background color that's dark-ish but not as dark as the hero example itself, so it
                // stands out
                .lineHeight(1.5.cssRem)
                .padding(0.75.cssRem)
                .borderRadius(12.px),
            code = """Motivated IT student with strong proficiency in Android development using Kotlin and Jetpack Compose, advanced
skills in Python and Java. Distinguished by exceptional problem-solving abilities and rapid technology adaptation.
Experienced in building Android apps and Python projects, with multiple certifications and HackerRank
achievements. Demonstrates leadership through roles in Google Developer Group and SWAG Developer’s Club,
SGGSIE&T.""".trimIndent()
        )
    }
}
@Composable
fun KotlinCode(code: String, modifier: Modifier = Modifier) {
    var displayedCode: String by remember { mutableStateOf("") }
    var showCursor by remember { mutableStateOf(true) }

    LaunchedEffect(code) {
        displayedCode = ""
        for (i in code.indices) {
            displayedCode = displayedCode.dropLastWhile { it == '|' }
            displayedCode += code[i]
            displayedCode += if (showCursor) '|' else ""
            showCursor = !showCursor
            delay(50)
        }
        displayedCode = displayedCode.dropLastWhile { it == '|' }
    }

    Pre(attrs = modifier.toAttrs()) {
        Code(attrs = {
            classes("language-kotlin").also {
                style {
                    fontFamily("Menlo", "monospace")
                    background("transparent")
                }
            }
        }) {
            Text(displayedCode)
        }
    }
}

