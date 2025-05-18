package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.web.events.SyntheticMouseEvent
import com.varabyte.kobweb.compose.css.Height
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.functions.dropShadow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.widgets.CodeBox
import com.vikramaditya.portfolio.components.IconButton
import com.vikramaditya.portfolio.components.SocialIcon
import com.vikramaditya.portfolio.styles.SocialIconStyle
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.AlignSelf
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun ProfileCard(colorMode: ColorMode, breakpoint: Breakpoint) {
    val ctx = rememberPageContext()
    val isMobile = breakpoint <= Breakpoint.MD



    if (isMobile) {
        Column(
            modifier = Modifier
                .id("home")
                .fillMaxWidth()
                .padding(leftRight = 5.percent, topBottom = 10.percent)
                .gap(24.px),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image on top
            ProfileImage()

            // Code box below image
            CodeBox(colorMode = colorMode)
            SocialLinks(colorMode, breakpoint)
        }
    } else {
        // Desktop or Tablet layout: Row-based
        Row(
            modifier = Modifier
                .id("home")
                .fillMaxWidth()
                .height(Height.FitContent)
                .padding(leftRight = 5.percent, topBottom = 10.percent)
                .gap(24.px),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(if (breakpoint >= Breakpoint.LG) 70.percent else 100.percent)
            ) {
                CodeBox(colorMode = colorMode)
                SocialLinks(colorMode, breakpoint)
            }

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
                        .size(if (breakpoint >= Breakpoint.LG) 365.px else 265.px)
                        .objectFit(ObjectFit.Cover),
                    src = if (colorMode.isDark) Res.Image.PROFILE_PHOTO_GREEN else Res.Image.PROFILE_PHOTO_REGULAR
                )

                GreenButton(text = "Resume", modifier = Modifier.align(Alignment.BottomCenter)) {
                    ctx.router.navigateTo(Res.String.RESUME_URL)
                }
            }
        }
    }
}
@Composable
fun ProfileImage() {
    val colorMode by ColorMode.currentState
    val ctx = rememberPageContext()
    Box(
        modifier = Modifier
            .size(260.px)
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
                .size(245.px)
                .objectFit(ObjectFit.Cover),
            src = if (colorMode.isDark) Res.Image.PROFILE_PHOTO_GREEN else Res.Image.PROFILE_PHOTO_REGULAR
        )

        GreenButton(text = "Resume", modifier = Modifier.align(Alignment.BottomCenter)) {
            ctx.router.navigateTo(Res.String.RESUME_URL)
        }
    }
}


@Composable
fun GreenButton(modifier: Modifier,text: String, onClick: (SyntheticMouseEvent) -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.then(
            Modifier
                .backgroundColor(Res.Theme.THEME_GREEN.color)
                .color(Res.Theme.LIGHT_THEME_BACKGROUND.color)
                .padding(12.px, 24.px)
                .borderRadius(6.px)
        )

    ) {
        SpanText(text)
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