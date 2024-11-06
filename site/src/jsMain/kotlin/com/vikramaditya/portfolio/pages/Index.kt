package com.vikramaditya.portfolio.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.sections.ProfileCard
import com.vikramaditya.portfolio.sections.SkillSection
import com.vikramaditya.portfolio.components.ThemeSwitchButton
import com.vikramaditya.portfolio.sections.ProjectsSection
import com.vikramaditya.portfolio.utils.Res
import kotlinx.browser.sessionStorage


@Page
@Composable
fun HomePage() {

    var colorMode by ColorMode.currentState
    val breakpoint = rememberBreakpoint()




    ThemeSwitchButton(
        colorMode = colorMode,
        onClick = {
            colorMode = colorMode.opposite
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ProfileCard(colorMode, breakpoint)
        SkillSection(colorMode, breakpoint)
        ProjectsSection(colorMode)
    }


}

//@Page
//@Composable
//fun HomePage() {
//
//
//    var colorMode by ColorMode.currentState
//
//    // Enhanced box shadow with transition
//    fun Modifier.googleCardStyle() = this.then(
//
//    )
//
//    // ... [Previous Column and Header code remains the same]
//
//
//
//    // Update Skills Section with icons

//
//

//
//    // Enhanced Contact Links with animations
//    @Composable
//    fun ContactLink(href: String, text: String, icon: String) {
//        Link(
//            path = href,
//            modifier = Modifier
//                .padding(12.px, 16.px)
//                .backgroundColor(if (colorMode.isLight) Colors.LightGray else Colors.DarkSlateGray)
//                .color(if (colorMode.isLight) Colors.Black else Colors.White)
//                .borderRadius(8.px)
//                .textDecorationLine(TextDecorationLine.None)
//                .fontSize(16.px)
//                .fontWeight(FontWeight.Medium)
//                .transition(Transition.of("all", 300.ms))
//                .onMouseEnter {
//                    Modifier
//                        .backgroundColor(GoogleColors.Blue)
//                        .color(Colors.White)
//                        .transform { scale(1.05) }
//                }
//        ) {
//            Row(
//                Modifier.gap(8.px),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                I(attrs = Modifier.classNames(icon).toAttrs())
//                Text(text)
//            }
//        }
//    }
//
//    // Update Contact Section
//    Row(
//        Modifier.gap(24.px),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        ContactLink("https://www.linkedin.com/in/vikramaditya-khupse-04838a259/", "LinkedIn", "fab fa-linkedin")
//        ContactLink("https://github.com/Phantom-VK", "GitHub", "fab fa-github")
//        ContactLink("mailto:vikramadityakhupse@gmail.com", "Email", "fas fa-envelope")
//        P(
//            Modifier
//                .color(if (colorMode.isLight) Colors.DarkGray else Colors.LightGray)
//                .toAttrs()
//        ) {
//            I(attrs = Modifier.classNames("fas fa-phone").margin(right = 8.px).toAttrs())
//            Text("+91-8530292951")
//        }
//    }
//}