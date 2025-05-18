package com.vikramaditya.portfolio.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.web.events.SyntheticMouseEvent
import com.varabyte.kobweb.compose.css.BoxShadow
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.HeaderItemStyle
import com.vikramaditya.portfolio.styles.MatrixGlow1
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px


@Composable
fun HeaderItem(label: String, isOnline: Boolean = true, onClick:(SyntheticMouseEvent)-> Unit) {
    val colorMode by ColorMode.currentState
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(16.px)
    ) {
        Box(
            modifier = HeaderItemStyle.toModifier().then(
                Modifier
                    .onClick { evt->
                        onClick(evt) }
            )
        ) {
            Box(
                modifier = Modifier
                    .size(12.px)
                    .borderRadius(50.percent)
                    .boxShadow(if(isOnline){
                        BoxShadow.of(
                            color = MatrixGlow1,
                            blurRadius = 5.px,
                            spreadRadius = 2.px
                        )
                    } else {
                        BoxShadow.None
                    }
                    )
                    .backgroundColor(if (isOnline) Res.Theme.THEME_GREEN_NEON.color else Res.Theme.CARD_BORDER_LIGHT.color)
            )
        }

        SpanText(
            label,
            modifier = Modifier
                .margin(top = 8.px)
                .fontFamily("Share Tech Mono")
                .color(if(colorMode.isDark) Colors.White else Res.Theme.DARK_THEME_BACKGROUND.color)
                .fontWeight(FontWeight.Bold)
        )
    }
}
