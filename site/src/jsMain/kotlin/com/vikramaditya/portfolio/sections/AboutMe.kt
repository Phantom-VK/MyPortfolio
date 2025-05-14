package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import androidx.compose.web.events.SyntheticMouseEvent
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.font
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.text.SpanText
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button

@Composable
fun AboutMe() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(leftRight = 10.percent),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GreenButton(
            "Resume"
        ) {}

        SpanText(
            Res.String.KOBWEB_CODE,
            modifier = Modifier
                .color(Color.white)
                .fontFamily("DM Sans")
                .textAlign(TextAlign.Center)
        )
    }
}

@Composable
fun GreenButton(text: String, onClick: (SyntheticMouseEvent) -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .backgroundColor(Color("#27AE60")) // Your specified green
            .color(Color.white) // White text
            .padding(12.px, 24.px)
            .borderRadius(1.px) // Sharp corners
            .border(width = 0.px) // Remove default border
    ) {
        SpanText(text)
    }
}