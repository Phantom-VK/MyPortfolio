package com.vikramaditya.portfolio.components


import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.fontFamily
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Code
import org.jetbrains.compose.web.dom.Pre
import org.jetbrains.compose.web.dom.Text

@Composable
fun AnimatedText(code: String, codeClass: String = "language-kotlin") {
//    var displayedCode by remember { mutableStateOf("") }
//    var showCursor by remember { mutableStateOf(true) }
//
//    LaunchedEffect(code) {
//        displayedCode = ""
//        for (char in code) {
//            displayedCode = displayedCode.dropLastWhile { it == '|' }
//            displayedCode += char
//            displayedCode += if (showCursor) '|' else ""
//            showCursor = !showCursor
//            delay(50)
//        }
//        displayedCode = displayedCode.dropLastWhile { it == '|' }
//    }

    Pre(
        attrs = Modifier
            .fillMaxWidth()
            .height(300.px)
            .overflow(Overflow.Auto)
            .borderRadius(12.px)
            .toAttrs()


    ) {
        Code(attrs = {
            classes(codeClass).also {
                style {
                    fontFamily("Menlo", "monospace")

                }
            }
        }) {
            Text(code)

        }
    }
}



//private fun Modifier.background(colorMode: ColorMode) =
//    this.then(when (colorMode) {
//        ColorMode.DARK -> Modifier.backgroundImage(
//            radialGradient(RadialGradient.Shape.Circle, Color.rgb(41, 41, 46), Color.rgb(25, 25, 28), CSSPosition.Top)
//        )
//        ColorMode.LIGHT -> Modifier.backgroundColor(Colors.White)
//    })