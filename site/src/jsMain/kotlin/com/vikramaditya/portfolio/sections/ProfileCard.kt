package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.objectFit
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.LeftSide
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.px

@Composable
fun ProfileCard(colorMode: ColorMode, breakpoint: Breakpoint) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .thenIf(
                breakpoint > Breakpoint.MD,
                other = Modifier.height(Res.Dimens.MAX_CARD_HEIGHT.px)
            )
            .padding(all = 12.px)
            .backgroundColor(if (colorMode.isLight) Res.Theme.LIGHT_THEME_BACKGROUND.color else Res.Theme.DARK_THEME_BACKGROUND.color)
            .gap(24.px),
        horizontalArrangement = if (breakpoint > Breakpoint.MD)
            Arrangement.Start else Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LeftSide(colorMode = colorMode, breakpoint = breakpoint)

        if (breakpoint > Breakpoint.SM) {
            RightSide(breakpoint = breakpoint)
        }
    }
}





@Composable
private fun RightSide(breakpoint: Breakpoint) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .thenIf(
                condition = breakpoint > Breakpoint.MD,
                other = Modifier.height((Res.Dimens.MAX_CARD_HEIGHT - 24).px)
            )
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .objectFit(ObjectFit.Contain),
            src = Res.Image.PROFILE_PHOTO
        )
    }
}
