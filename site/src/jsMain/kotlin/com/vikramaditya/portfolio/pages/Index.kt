package com.vikramaditya.portfolio.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.ProfileCard
import com.vikramaditya.portfolio.components.SkillCard
import com.vikramaditya.portfolio.components.SkillSection
import com.vikramaditya.portfolio.components.ThemeSwitchButton
import com.vikramaditya.portfolio.utils.Res
import kotlinx.browser.localStorage


@Page
@Composable
fun HomePage() {

    var colorMode by ColorMode.currentState
    val breakpoint = rememberBreakpoint()


    LaunchedEffect(colorMode) {
        val savedTheme = localStorage.getItem(Res.String.SAVED_THEME) ?: ColorMode.LIGHT.name
        colorMode = ColorMode.valueOf(savedTheme)
    }

    ThemeSwitchButton(
        colorMode = colorMode,
        onClick = {
            colorMode = colorMode.opposite
            localStorage.setItem(Res.String.SAVED_THEME, colorMode.name)
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ProfileCard(colorMode, breakpoint)
        SkillSection(colorMode, breakpoint)
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
//    @Composable
//    fun ProjectCard(title: String, description: String, tech: String, icon: String) {
//        Box(
//            Modifier
//                .padding(16.px)
//                .googleCardStyle()
//        ) {
//            Column(
//                Modifier.padding(24.px),
//                horizontalAlignment = Alignment.Start
//            ) {
//                Row(
//                    Modifier.margin(bottom = 16.px),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    I(
//                        attrs = Modifier
//                            .fontSize(24.px)
//                            .color(GoogleColors.Blue)
//                            .margin(right = 12.px)
//                            .classNames(icon)
//                            .toAttrs()
//                    )
//                    H3(
//                        Modifier
//                            .fontSize(24.px)
//                            .fontWeight(FontWeight.Bold)
//                            .toAttrs()
//                    ) { Text(title) }
//                }
//                P(
//                    Modifier
//                        .margin(bottom = 12.px)
//                        .fontSize(16.px)
//                        .lineHeight(1.6)
//                        .toAttrs()
//                ) { Text(description) }
//                P(
//                    Modifier
//                        .color(if (colorMode.isLight) Colors.DarkGray else Colors.LightGray)
//                        .fontSize(14.px)
//                        .toAttrs()
//                ) { Text(tech) }
//            }
//        }
//    }
//
//    // Update Projects Section
//    SimpleGrid(numColumns = numColumns(2), modifier = Modifier.maxWidth(1200.px).gap(24.px)) {
//        ProjectCard(
//            "BoothMap",
//            "Election booth locator app serving 2,000+ users with precise navigation and booth information retrieval.",
//            "Kotlin, Jetpack Compose, Google Maps API, Firebase",
//            "fas fa-map-marker-alt"
//        )
//        ProjectCard(
//            "ClockInPro",
//            "Automated attendance system with geofencing, saving 10 hours/month in manual tracking.",
//            "Kotlin, Jetpack Compose, MySQL, CPanel, Google Maps API",
//            "fas fa-clock"
//        )
//        ProjectCard(
//            "SGGSIDcard",
//            "ID card generator for 1,500+ students, automating generation by 80% with live image capture.",
//            "Python, Flask, Firebase, OpenCV, PIL",
//            "fas fa-id-card"
//        )
//        ProjectCard(
//            "ExcelSync",
//            "Utility app for bulk uploading data from Excel/CSV files to Firebase with validation.",
//            "Kotlin, Firebase, Excel API",
//            "fas fa-file-excel"
//        )
//    }
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