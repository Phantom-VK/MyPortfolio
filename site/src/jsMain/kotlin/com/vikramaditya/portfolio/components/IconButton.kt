package com.vikramaditya.portfolio.components


import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.toModifier
import com.vikramaditya.portfolio.styles.IconButtonStyle
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.px

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    link: String = "",
    icon: String,
    label: String = "",
    iconSize: CSSSizeValue<CSSUnit.px> = 20.px,
    onClick: (() -> Unit)? = null
) {
    Link(path = link) {
        Box(
            modifier = IconButtonStyle.toModifier()
                .then(modifier)
                .ariaLabel(label)
                .onClick { onClick?.invoke() }
        ) {
            Image(
                modifier = Modifier.size(iconSize),
                src = icon,
                alt = label
            )
        }
    }
}


enum class SocialIcon(
    val icon: String,
    val link: String,
    val label: String
) {
    Github(
        icon = Res.Icon.GITHUB,
        link = "https://github.com/Phantom-VK",
        label = "GitHub profile"
    ),
    GithubLight(
        icon = Res.Icon.GITHUB_LIGHT,
        link = "https://github.com/Phantom-VK",
        label = "GitHub profile"
    ),
    LinkedIn(
        icon = Res.Icon.LINKEDIN,
        link = "https://www.linkedin.com/in/vikramaditya-khupse-04838a259",
        label = "LinkedIn profile"
    ),
    LinkedInLight(
        icon = Res.Icon.LINKEDIN_LIGHT,
        link = "https://www.linkedin.com/in/vikramaditya-khupse-04838a259",
        label = "LinkedIn profile"
    ),
    Instagram(
        icon = Res.Icon.INSTAGRAM,
        link = "https://www.instagram.com/_vikramaditya__",
        label = "Instagram profile"
    ),
    InstagramLight(
        icon = Res.Icon.INSTAGRAM_LIGHT,
        link = "https://www.instagram.com/_vikramaditya__",
        label = "Instagram profile"
    )
}
