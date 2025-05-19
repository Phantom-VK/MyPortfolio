package com.vikramaditya.portfolio.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backdropFilter
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.pointerEvents
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.framework.annotations.DelicateApi
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px

@OptIn(DelicateApi::class)
@Composable
fun ThemeSwitchButton(
    colorMode: ColorMode,
    onClick: () -> Unit
) {
    val breakpoint = rememberBreakpoint()

    // Responsive dimensions
    val padding = when (breakpoint) {
        Breakpoint.XL, Breakpoint.SM -> 6.px
        Breakpoint.MD -> 8.px
        else -> 10.px
    }

    val iconSize = when (breakpoint) {
         Breakpoint.SM -> 18.px
        Breakpoint.XL -> 24.px
        Breakpoint.MD -> 20.px
        else -> 22.px
    }

    val bgColor = if (colorMode.isLight) Color.white else Res.Theme.THEME_GREEN.color
    val borderColor = if (colorMode.isLight) Res.Theme.CARD_BORDER_LIGHT.color else Res.Theme.CARD_BORDER_DARK.color

    Box(
        modifier = Modifier
            .pointerEvents(PointerEvents.Auto)
            .padding(all = padding)
            .backdropFilter(blur(2.px))
            .background(bgColor)
            .borderRadius(Res.Dimens.BORDER_RADIUS.px)
            .border(width = 1.px, style = LineStyle.Solid, color = borderColor)
            .onClick { onClick() }
    ) {
        Image(
            modifier = Modifier.size(iconSize),
            src = if (colorMode.isLight) Res.Icon.SUN else Res.Icon.MOON
        )
    }
}
