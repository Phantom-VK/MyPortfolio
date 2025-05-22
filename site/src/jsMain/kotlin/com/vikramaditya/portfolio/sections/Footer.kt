package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.Width
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.framework.annotations.DelicateApi
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.ContactMeButton
import com.vikramaditya.portfolio.utils.Res
import kotlinx.browser.window
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px

@OptIn(DelicateApi::class)
@Composable
fun Footer() {
    val breakpoint = rememberBreakpoint()
    val isMobile = breakpoint <= Breakpoint.SM

    Column(
        modifier = Modifier
            .id("contact")
            .fillMaxWidth()
            .backgroundColor(Color.rgba(0, 255, 65, 0.06f))
            .padding(topBottom = 1.cssRem)
            .borderTop(1.px, LineStyle.Solid, Res.Theme.THEME_GREEN_NEON.color),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isMobile) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(1.cssRem),
            ) {
                ContactMeButton(email = Res.String.MY_EMAIL)
                KotlinText()
                MadeWithKobweb()
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ContactMeButton(email = Res.String.MY_EMAIL)
                KotlinText(
                    modifier = Modifier.margin(left = 4.cssRem)
                )
                MadeWithKobweb()
            }
        }
    }
}

@Composable
private fun MadeWithKobweb() {
    val colorMode by ColorMode.currentState
    Row(
        modifier = Modifier
            .borderRadius(8.px)
            .padding(leftRight = 1.cssRem, topBottom = 0.5.cssRem)
            .onClick {
                window.open("https://kobweb.varabyte.com/", "_blank")
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        SpanText(
            "Built with",
            Modifier
                .fontFamily("Share Tech Mono")
                .fontWeight(FontWeight.Bold)
                .fontSize(1.5.em)
                .color(
                    if (colorMode.isDark)
                        Res.Theme.THEME_GREEN_NEON.color
                    else
                        Res.Theme.GREY_BACKGROUND.color
                )
        )
        Image(
            src = Res.Logo.KOBWEB_LOGO,
            modifier = Modifier.height(40.px)
                .width(Width.FitContent)
                .margin(left = 1.cssRem)
        )
    }
}

@Composable
fun KotlinText(modifier: Modifier = Modifier) {
    val colorMode by ColorMode.currentState
    SpanText(
        "Developed entirely with Kotlin",
        modifier = modifier.then(
            Modifier
                .fontFamily("Share Tech Mono")
                .textAlign(TextAlign.Center)
                .fontWeight(FontWeight.Bold)
                .fontSize(1.2.em)
                .color(
                    if (colorMode.isDark)
                        Res.Theme.THEME_GREEN_NEON.color
                    else
                        Res.Theme.GREY_BACKGROUND.color
                )
        )
    )
}
