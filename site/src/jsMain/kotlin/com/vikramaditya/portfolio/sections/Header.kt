package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FontSize
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.ScrollSnapAlign
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.RowDefaults
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.navigation.UndecoratedLinkVariant
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.base
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.ThemeSwitchButton
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba


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
                .padding(leftRight = 5.percent, topBottom = 1.percent)
                .backdropFilter(blur(4.px))),
            verticalAlignment = Alignment.CenterVertically

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
                horizontalArrangement = Arrangement.End
            ) {
                SpanText(
                    text = "About Me",
                    modifier = Modifier
                        .padding(leftRight = 5.px)
                        .fontFamily("DM Sans")
                        .color(textColor)
                        .fontSize(FontSize.Medium)
                        .fontWeight(FontWeight.Medium)

                )
                SpanText(
                    text = "Skills ",
                    modifier = Modifier
                        .padding(leftRight = 5.px)
                        .fontFamily("DM Sans")
                        .color(textColor)
                        .fontSize(FontSize.Medium)
                        .fontWeight(FontWeight.Medium)


                )
                SpanText(
                    text = "Contact me",
                    modifier = Modifier
                        .padding(left = 5.px, right = 40.px)
                        .fontFamily("DM Sans")
                        .color(textColor)
                        .fontSize(FontSize.Medium)
                        .fontWeight(FontWeight.Medium)

                )

                ThemeSwitchButton(
                    colorMode = colorMode,
                    onClick = { colorMode = colorMode.opposite }
                )


            }

        }
}