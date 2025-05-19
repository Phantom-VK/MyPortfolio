package com.vikramaditya.portfolio.components

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
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.HeaderItemStyle
import com.vikramaditya.portfolio.styles.MatrixGlow1
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun HeaderItem(
    label: String,
    isOnline: Boolean = true,
    breakpoint: Breakpoint,
    onClick: (SyntheticMouseEvent) -> Unit
) {
    val colorMode by ColorMode.currentState

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(
                topBottom = when (breakpoint) {
                    Breakpoint.SM -> 2.px
                    Breakpoint.MD -> 6.px
                    else -> 10.px
                }
            )
    ) {
        Box(
            modifier = HeaderItemStyle.toModifier().then(
                Modifier
                    .size(
                        when (breakpoint) {
                            Breakpoint.SM -> 20.px
                            Breakpoint.MD -> 34.px
                            else -> 46.px
                        }
                    )
                    .onClick { evt -> onClick(evt) }
            )
        ) {
            Box(
                modifier = Modifier
                    .size(
                        when (breakpoint) {
                            Breakpoint.SM -> 3.px
                            Breakpoint.MD -> 8.px
                            else -> 10.px
                        }
                    )
                    .borderRadius(50.percent)
                    .boxShadow(
                        if (isOnline) {
                            BoxShadow.of(
                                color = MatrixGlow1,
                                blurRadius = when (breakpoint) {
                                    Breakpoint.SM -> 2.px
                                    else -> 4.px
                                },
                                spreadRadius = when (breakpoint) {
                                    Breakpoint.SM -> 1.px
                                    else -> 2.px
                                }
                            )
                        } else {
                            BoxShadow.None
                        }
                    )
                    .backgroundColor(
                        if (isOnline) Res.Theme.THEME_GREEN_NEON.color
                        else Res.Theme.CARD_BORDER_LIGHT.color
                    )
            )
        }

        SpanText(
            label,
            modifier = Modifier
                .margin(top = when (breakpoint) {
                    Breakpoint.SM -> 3.px
                    Breakpoint.MD -> 5.px
                    else -> 8.px
                })
                .fontFamily("Share Tech Mono")
                .fontSize(
                    when (breakpoint) {
                        Breakpoint.SM -> 5.px
                        Breakpoint.MD -> 10.px
                        else -> 14.px
                    }
                )
                .color(if (colorMode.isDark) Colors.White else Res.Theme.DARK_THEME_BACKGROUND.color)
                .fontWeight(FontWeight.Bold)
        )
    }
}