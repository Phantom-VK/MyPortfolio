package com.vikramaditya.portfolio.components


import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.utils.Res.Dimens.BORDER_RADIUS
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    colorMode: ColorMode,
    link: String = "",
    icon: String,
    iconSize: CSSSizeValue<CSSUnit.px> = Res.Dimens.ICON_SIZE.px,
    onClick: (() -> Unit)? = null
) {
    Link(path = link) {
        Box(
            modifier = modifier
                .padding(all = 8.px)
                .backdropFilter(blur(2.px))
                .background(
                    color = if (colorMode.isLight) Color.white
                    else Res.Theme.THEME_GREEN.color
                )
                .borderRadius(r = BORDER_RADIUS.px)
                .cursor(Cursor.Pointer)
                .border(
                    width = 1.px,
                    style = LineStyle.Solid,
                    color = if (colorMode.isLight) Res.Theme.CARD_BORDER_LIGHT.color
                    else Res.Theme.CARD_BORDER_DARK.color
                )
                .onClick { onClick?.invoke() }
        ) {
            Image(
                modifier = Modifier.size(iconSize),
                src = icon
            )
        }
    }
}


enum class SocialIcon(
    val icon: String,
    val link: String
) {
    Github(
        icon = Res.Icon.GITHUB,
        link = "https://github.com/Phantom-VK"
    ),
    GithubLight(
        icon = Res.Icon.GITHUB_LIGHT,
        link = "https://github.com/Phantom-VK"
    ),
    LinkedIn(
        icon = Res.Icon.LINKEDIN,
        link = "https://www.linkedin.com/in/vikramaditya-khupse-04838a259"
    ),
    LinkedInLight(
        icon = Res.Icon.LINKEDIN_LIGHT,
        link = "https://www.linkedin.com/in/vikramaditya-khupse-04838a259"
    ),
    Instagram(
        icon = Res.Icon.INSTAGRAM,
        link = "https://www.instagram.com/_the_vikramaditya_"
    ),
    InstagramLight(
        icon = Res.Icon.INSTAGRAM_LIGHT,
        link = "https://www.instagram.com/_the_vikramaditya_"
    )
}