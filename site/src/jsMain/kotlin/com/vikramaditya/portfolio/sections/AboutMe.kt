package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import androidx.compose.web.events.SyntheticMouseEvent
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.widgets.SectionTitle
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.silk.components.layout.HorizontalDivider
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

@Composable
fun AboutMe() {
    val colorMode = ColorMode.current
    val breakpoint = rememberBreakpoint()

    val fontSize = when (breakpoint) {
        Breakpoint.SM, Breakpoint.XL -> 24.px
        Breakpoint.MD -> 28.px
        else -> 30.px
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.percent),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        // Matching card styling from WhatIDoCard
        Box(
            modifier = Modifier
                .fillMaxSize()
                .borderRadius(7.px)
                .background(color = Res.Theme.GREY_BACKGROUND.color)
                .padding(3.percent)
        ) {
            SpanText(
                Res.String.ABOUT_ME,
                modifier = Modifier
                    .fillMaxWidth()
                    .textAlign(TextAlign.Justify)
                    .color(
                        if (colorMode.isDark)
                            Res.Theme.GLASS_BOX_BORDER_COLOR_LIGHT.color
                        else
                            Colors.White
                    )
                    .fontFamily("VT323")
                    .fontSize(fontSize)
            )
        }

    }
}


@Composable
fun GreenButton(text: String, onClick: (SyntheticMouseEvent) -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .backgroundColor(Res.Theme.THEME_GREEN.color)
            .color(Res.Theme.LIGHT_THEME_BACKGROUND.color)
            .padding(12.px, 24.px)
            .borderRadius(6.px)
    ) {
        SpanText(text)
    }
}