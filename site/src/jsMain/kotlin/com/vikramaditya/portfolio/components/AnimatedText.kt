package com.vikramaditya.portfolio.components

import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.CSSPosition
import com.varabyte.kobweb.compose.css.functions.RadialGradient
import com.varabyte.kobweb.compose.css.functions.radialGradient
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.backgroundImage
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.AlignContent
import org.jetbrains.compose.web.css.AlignSelf
import org.jetbrains.compose.web.css.alignContent
import org.jetbrains.compose.web.css.alignSelf
import org.jetbrains.compose.web.css.background
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fontFamily
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Code
import org.jetbrains.compose.web.dom.Pre
import org.jetbrains.compose.web.dom.Text
import kotlin.text.trimIndent

@Composable
fun AnimatedText() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .borderRadius(12.px),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            KotlinCode(
                modifier = Modifier
                    .color(Colors.White)
                    .lineHeight(1.5.cssRem)
                    .padding(0.75.cssRem)
                    .background(ColorMode.DARK)
                    .borderRadius(topRight = 12.px, bottomRight = 12.px),
                code = """
                    @Composable
                    fun AboutMe(){
                    println(Motivated IT student with strong proficiency in Android development 
                    using Kotlin and Jetpack Compose, advanced
                    skills in Python and Java.)
                    }
                """.trimIndent(),
                codeClass = "language-kotlin"
            )
        }
    }
}

@Composable
fun KotlinCode(code: String, modifier: Modifier = Modifier,codeClass: String) {
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
            classes(codeClass).also {
                style {
                    fontFamily("Menlo", "monospace")
                    background("transparent")
                    alignSelf(AlignSelf.Center)
                    alignContent(AlignContent.Center)
                }
            }
        }) {
            Text(code)
        }
    }
}



private fun Modifier.background(colorMode: ColorMode) =
    this.then(when (colorMode) {
        ColorMode.DARK -> Modifier.backgroundImage(
            radialGradient(RadialGradient.Shape.Circle, Color.rgb(41, 41, 46), Color.rgb(25, 25, 28), CSSPosition.Top)
        )
        ColorMode.LIGHT -> Modifier.backgroundColor(Colors.White)
    })