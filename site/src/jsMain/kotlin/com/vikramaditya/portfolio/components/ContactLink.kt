package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.ContactLinkButtonStyle
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text

@Composable
fun ContactLink(
    href: String,
    text: String,
    colorMode: ColorMode
) {
    Link(
        path = href,
        modifier = ContactLinkButtonStyle.toModifier()
            .padding(8.px)
            .color(
                if (colorMode.isLight) Res.Theme.SKILL_LABEL_GRADIENT_END.color
                else Res.Theme.SKILL_LABEL_GRADIENT_DARK_END.color
            )
    ) {
        Text(text)
    }
}
