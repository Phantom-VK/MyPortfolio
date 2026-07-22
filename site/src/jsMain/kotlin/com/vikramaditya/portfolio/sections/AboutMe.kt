package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.framework.annotations.DelicateApi
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.utils.Res
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

@OptIn(DelicateApi::class)
@Composable
fun AboutMe() {
    val colorMode = ColorMode.current
    val breakpoint = rememberBreakpoint()

    val fontSize = when (breakpoint) {
        Breakpoint.SM ->15.px
        Breakpoint.MD -> 28.px
        Breakpoint.XL -> 30.px
        else -> 24.px
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.percent),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .borderRadius(Res.Dimens.BORDER_RADIUS.px)
                .background(color = Res.Theme.GREY_BACKGROUND.color)
                .padding(3.percent)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                H1(
                    attrs = Modifier
                        .fillMaxWidth()
                        .margin(0.px)
                        .textAlign(TextAlign.Center)
                        .color(
                            if (colorMode.isDark)
                                Res.Theme.GLASS_BOX_BORDER_COLOR_LIGHT.color
                            else
                                Colors.White
                        )
                        .fontFamily("VT323")
                        .fontSize(fontSize)
                        .fontWeight(FontWeight.Normal)
                        .toAttrs()
                ) {
                    Text("Hello, I am Vikramaditya. IT Engineer.")
                }
                SpanText(
                    "I love computers and make them work for me.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .textAlign(TextAlign.Center)
                        .color(
                            if (colorMode.isDark)
                                Res.Theme.GLASS_BOX_BORDER_COLOR_LIGHT.color
                            else
                                Colors.White
                        )
                        .fontFamily("VT323")
                        .fontSize(fontSize)
                        .padding(top = 8.px)
                )
                SpanText(
                    text = "I like to develop all kinds of stuff on computers. I hate web development though, (HTML, CSS, JS...). Currently interning at Emplay Analytics as an Agentic AI Automation Engineer, building enterprise AI copilots with LLMs and agentic workflows. My main focus areas are AI/ML applications, GenAI, Agentic AI, and full stack development.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .textAlign(TextAlign.Center)
                        .color(
                            if (colorMode.isDark)
                                Res.Theme.GLASS_BOX_BORDER_COLOR_LIGHT.color
                            else
                                Colors.White
                        )
                        .fontFamily("VT323")
                        .fontSize(fontSize)
                        .padding(top = 8.px)
                )
            }
        }
    }
}
