package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.widgets.ThemeSwitchButton
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.widgets.HeaderItem
import kotlinx.browser.window
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px







@Composable
fun Header(modifier: Modifier){

    var colorMode by ColorMode.currentState
    val (rowColor, textColor) = if (colorMode.isDark) {
        Res.Theme.DARK_THEME_BACKGROUND.color to Res.Theme.LIGHT_THEME_BACKGROUND.color
    } else {
        Res.Theme.LIGHT_THEME_BACKGROUND.color to Res.Theme.DARK_THEME_BACKGROUND.color
    }
    val ctx = rememberPageContext()
    var selectedItem by remember { mutableIntStateOf(-1) }

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
                    .fontFamily("Share Tech Mono")
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
                HeaderItem(
                    "About Me",
                    isOnline = selectedItem == 0
                ){
                    window.location.href = "#about-me"
                    selectedItem = 0
                }
                HeaderItem(
                    "Skills",
                    isOnline = selectedItem == 1
                ){
                    window.location.href = "#skills"
                    selectedItem = 1
                }
                HeaderItem(
                    "Contact Me",
                    isOnline = selectedItem == 2
                ){
                    window.location.href = "#contact"
                    selectedItem = 2
                }

//                HangingText(
//                    text = "About me!",
//                    onClick = { window.location.href = "#about-me" }
//                )
//                HangingText(
//                    text = "Skills",
//                    onClick = { window.location.href = "#what-i-do" }
//                )
//                HangingText(
//                    text = "Contact Me!",
//                    onClick = { window.location.href = "#contact" }
//                )
//                HangingText(
//                    text = "Resume",
//                    onClick = {ctx.router.navigateTo(Res.String.RESUME_URL) }
//                )

                ThemeSwitchButton(
                    colorMode = colorMode,
                    onClick = { colorMode = colorMode.opposite }
                )


            }

        }
}