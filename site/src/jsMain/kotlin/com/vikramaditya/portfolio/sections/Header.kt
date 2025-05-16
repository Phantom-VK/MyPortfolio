package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FontSize
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.navigation.UndecoratedLinkVariant
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.HangingText
import com.vikramaditya.portfolio.components.ThemeSwitchButton
import com.vikramaditya.portfolio.utils.Res
import kotlinx.browser.window
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px


@Composable
private fun NavLink(path: String, text: String) {
    Link(path, text, variant = UndecoratedLinkVariant.then(UncoloredLinkVariant))
}

@Composable
private fun MenuItems() {
    NavLink("#home", "Home")
    NavLink("#skills_and_tools", "Skills & Tools")
    NavLink("#projects", "Projects")
    NavLink("#contact_me", "Contact Me")
}



@Composable
fun Header(modifier: Modifier){

    var colorMode by ColorMode.currentState
    val (rowColor, textColor) = if (colorMode.isDark) {
        Res.Theme.DARK_THEME_BACKGROUND.color to Res.Theme.LIGHT_THEME_BACKGROUND.color
    } else {
        Res.Theme.LIGHT_THEME_BACKGROUND.color to Res.Theme.DARK_THEME_BACKGROUND.color
    }

    Row(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .padding(leftRight = 5.percent)
                .backdropFilter(blur(4.px))
                ),
            verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            SpanText(
                text = "Hi, I am ${Res.String.NAME}",
                modifier = Modifier
                    .fontFamily("DM Sans")
                    .color(textColor)
                    .fontSize(FontSize.XXLarge)
                    .fontWeight(FontWeight.Bold)
                    .textAlign(
                        TextAlign.Start
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                HangingText(
                    text = "About me!",
                    onClick = { window.location.href = "#contact" }
                )
                HangingText(
                    text = "Skills",
                    onClick = { window.location.href = "#contact" }
                )
                HangingText(
                    text = "Contact Me!",
                    onClick = { window.location.href = "#contact" }
                )

                ThemeSwitchButton(
                    colorMode = colorMode,
                    onClick = { colorMode = colorMode.opposite }
                )


            }

        }
}