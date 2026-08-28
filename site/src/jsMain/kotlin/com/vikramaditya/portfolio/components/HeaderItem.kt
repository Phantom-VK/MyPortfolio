package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.web.events.SyntheticMouseEvent
import com.varabyte.kobweb.compose.css.BoxShadow
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.HeaderItemStyle
import com.vikramaditya.portfolio.utils.theme.Font
import com.vikramaditya.portfolio.utils.theme.Space
import com.vikramaditya.portfolio.utils.theme.Type
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.fontFace
import com.vikramaditya.portfolio.utils.theme.textStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s

/**
 * A single navigation entry: a status dot over a label.
 *
 * @param isOnline whether this is the section currently in view.
 */
@Composable
fun HeaderItem(
    label: String,
    isOnline: Boolean = true,
    breakpoint: Breakpoint,
    onClick: (SyntheticMouseEvent) -> Unit
) {
    val colorMode by ColorMode.currentState
    val c = colors(colorMode)

    // Comparison ladders, not exact matches. `when (breakpoint) { SM -> ...; MD -> ...; else -> ... }`
    // sent viewports *below* SM into the `else` branch and gave the smallest
    // screens the largest sizes.
    val isLarge = breakpoint >= Breakpoint.LG
    val isMedium = breakpoint >= Breakpoint.MD

    val hitSize = if (isLarge) 46.px else if (isMedium) 34.px else 26.px
    val dotSize = if (isLarge) 10.px else if (isMedium) 8.px else 6.px

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(topBottom = if (isMedium) Space.sm else Space.xs)
    ) {
        Box(
            modifier = HeaderItemStyle.toModifier().then(
                Modifier
                    .size(hitSize)
                    .onClick { evt -> onClick(evt) }
            )
        ) {
            Box(
                modifier = Modifier
                    .size(dotSize)
                    .borderRadius(50.percent)
                    .transition(
                        Transition.of("background-color", 0.25.s),
                        Transition.of("box-shadow", 0.25.s),
                    )
                    .boxShadow(
                        if (isOnline) {
                            BoxShadow.of(
                                color = c.signal,
                                blurRadius = if (isMedium) 4.px else 2.px,
                                spreadRadius = if (isMedium) 2.px else 1.px,
                            )
                        } else {
                            BoxShadow.None
                        }
                    )
                    .backgroundColor(if (isOnline) c.signal else c.textSecondary)
            )
        }

        SpanText(
            label,
            modifier = Modifier
                .margin(top = if (isMedium) Space.sm else Space.xs)
                // The old ladder bottomed out at 5px, which is not readable at
                // any size. 12px is the floor of the type scale.
                .textStyle(if (isLarge) Type.Small else Type.Micro)
                .fontFace(Font.DISPLAY)
                .transition(Transition.of("color", 0.25.s))
                .color(if (isOnline) c.textPrimary else c.textSecondary)
        )
    }
}
