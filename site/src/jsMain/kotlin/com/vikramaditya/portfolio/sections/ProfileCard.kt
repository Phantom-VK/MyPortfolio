package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Height
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.functions.dropShadow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.CodeBox
import com.vikramaditya.portfolio.components.IconButton
import com.vikramaditya.portfolio.components.SocialIcon
import com.vikramaditya.portfolio.styles.SocialIconStyle
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.AlignSelf
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun ProfileCard(colorMode: ColorMode, breakpoint: Breakpoint) {

        Row(
            modifier = Modifier
                .id("home")
                .fillMaxWidth()
                .height(Height.FitContent)
                .padding(leftRight = 5.percent, topBottom = 10.percent)
                .gap(24.px),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (breakpoint <= Breakpoint.MD)
                Arrangement.Center else Arrangement.SpaceBetween
        ) {
            // Left Side (Code Box) - Takes 60-70% width on larger screens
            Box(
                modifier = Modifier
                    .fillMaxWidth(if (breakpoint >= Breakpoint.LG) 70.percent else 100.percent)
            ) {
                CodeBox(colorMode = colorMode)
                SocialLinks(colorMode, breakpoint)
            }

            // Right Side (Profile Photo) - Only visible on larger screens
            if (breakpoint >= Breakpoint.MD) {
                Box(
                    modifier = Modifier
                        .size(if (breakpoint >= Breakpoint.LG) 380.px else 280.px)
                        .borderRadius(12.px)
                        .overflow(Overflow.Hidden)
                        .filter(
                            dropShadow(
                                offsetX = 0.px,
                                offsetY = 0.px,
                                blurRadius = 20.px,
                                color = Res.Theme.THEME_GREEN.color
                            )
                        )
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .objectFit(ObjectFit.Cover),
                        src = if(colorMode.isDark) Res.Image.PROFILE_PHOTO_GREEN else Res.Image.PROFILE_PHOTO_REGULAR
                    )
                }
            }


        }




}

@Composable
 fun SocialLinks(colorMode: ColorMode, breakpoint: Breakpoint) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .alignSelf(AlignSelf.SelfEnd)
            .padding(leftRight = 3.percent)
            .gap(12.px),
        horizontalArrangement = if (breakpoint <= Breakpoint.SM)
            Arrangement.Center else Arrangement.Start
    ) {
        SocialIcon.entries.filter {
            if (colorMode.isLight) !it.name.contains("Light")
            else it.name.contains("Light")
        }.forEach {
            IconButton(
                modifier = SocialIconStyle.toModifier(),
                colorMode = colorMode,
                icon = it.icon,
                link = it.link
            )
        }
    }
}