package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.utils.Res
import kotlinx.browser.window
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px

@Composable
fun ContactMeButton(email: String, modifier: Modifier = Modifier) {
    val colorMode by ColorMode.currentState

    val bgColor =  Res.Theme.GREY_BACKGROUND.color
    val borderColor = if (colorMode.isDark) Res.Theme.THEME_GREEN_NEON.color else Res.Theme.GREY_BACKGROUND.color

    Row(
        modifier = modifier
            .borderRadius(8.px)
            .border(1.px, LineStyle.Solid, borderColor)
            .padding(leftRight = 1.cssRem, topBottom = 0.5.cssRem)
            .backgroundColor(bgColor)
            .cursor(Cursor.Pointer)
            .onClick {
                window.open(email, "_blank")
            }
                ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            src = Res.Icon.EMAIL_DARK,
            modifier = Modifier.size(24.px)
        )
        SpanText(
            "Contact Me",
            Modifier
                .margin(left = 8.px)
                .fontFamily("Share Tech Mono")
                .fontWeight(FontWeight.Medium)
                .fontSize(1.em)
                .color(Res.Theme.THEME_GREEN_NEON.color)
        )
    }
}
