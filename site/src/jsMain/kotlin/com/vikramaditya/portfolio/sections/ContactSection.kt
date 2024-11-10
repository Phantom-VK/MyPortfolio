package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.ContactLink
import com.vikramaditya.portfolio.utils.Res

@Composable
fun ContactSection(colorMode: ColorMode) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Res.Theme.BLUE.color)
    ) {
        // Left section
        Box(
            modifier = Modifier
                .background(
                    if(colorMode.isLight)
                        Res.Theme.LIGHT_THEME_BACKGROUND.color
                    else
                        Res.Theme.DARK_THEME_BACKGROUND.color
                )
        )

        // Middle section with contact links
        Box(
            modifier = Modifier
                .background(
                    if(colorMode.isLight)
                        Res.Theme.LIGHT_THEME_BACKGROUND.color
                    else
                        Res.Theme.DARK_THEME_BACKGROUND.color
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                ContactLink(
                    href = "https://www.linkedin.com/in/vikramaditya-khupse-04838a259/",
                    text = "LinkedIn",
                    colorMode = colorMode
                )
                ContactLink(
                    href = "https://github.com/Phantom-VK",
                    text = "GitHub",
                    colorMode = colorMode
                )
                ContactLink(
                    href = "mailto:vikramadityakhupse@gmail.com",
                    text = "Email",
                    colorMode = colorMode
                )
            }
        }

        // Right section
        Box(
            modifier = Modifier
                .background(
                    if(colorMode.isLight)
                        Res.Theme.LIGHT_THEME_BACKGROUND.color
                    else
                        Res.Theme.DARK_THEME_BACKGROUND.color
                )
        )
    }
}