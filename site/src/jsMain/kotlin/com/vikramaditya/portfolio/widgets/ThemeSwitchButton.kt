package com.vikramaditya.portfolio.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
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
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px

@Composable
fun ThemeSwitchButton(
    colorMode: ColorMode,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.Companion
            .styleModifier {
                property("pointer-events", "auto")
            }
            .padding(all = 8.px)
            .backdropFilter(blur(2.px))
            .background(
                color = if (colorMode.isLight) Color.white
                else Res.Theme.THEME_GREEN.color
            )
            .borderRadius(r = Res.Dimens.BORDER_RADIUS.px)
            .cursor(Cursor.Companion.Pointer)
            .border(
                width = 1.px,
                style = LineStyle.Companion.Solid,
                color = if (colorMode.isLight) Res.Theme.CARD_BORDER_LIGHT.color
                else Res.Theme.CARD_BORDER_DARK.color
            )
            .onClick { onClick.invoke() }
    ) {
        Image(
            modifier = Modifier.Companion.size(Res.Dimens.ICON_SIZE.px),
            src = if (colorMode.isLight) Res.Icon.SUN else Res.Icon.MOON
        )
    }

}