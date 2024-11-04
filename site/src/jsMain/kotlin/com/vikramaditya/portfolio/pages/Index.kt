package com.vikramaditya.portfolio.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Page
@Composable
fun HomePage() {
    var colorMode by ColorMode.currentState

    // Helper function for consistent shadow styling
    fun getBoxShadow() = if (colorMode.isLight) {
        BoxShadow.of(0.px, 4.px, 8.px, color = rgba(0, 0, 0, 0.1))
    } else {
        BoxShadow.of(0.px, 4.px, 8.px, color = rgba(0, 0, 0, 0.3))
    }

    Column(
        Modifier.fillMaxWidth().minHeight(100.vh),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Enhanced Header with Theme Toggle
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.px)
                .backgroundColor(if (colorMode.isLight) Colors.White else Colors.Black)
                .boxShadow(getBoxShadow()),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            P(
                attrs = Modifier
                    .fontSize(44.px)
                    .fontWeight(FontWeight.Bold)
                    .color(if (colorMode.isLight) Colors.Black else Colors.White)
                    .toAttrs()
            ) {
                Text("VK")
            }

            Button(
                onClick = { colorMode = colorMode.opposite },
                Modifier
                    .borderRadius(50.percent)
                    .padding(8.px)
                    .backgroundColor(if (colorMode.isLight) Colors.LightGray else Colors.DarkSlateGray)
            ) {
                if (colorMode.isLight) Text("🌙") else Text("☀️")
            }
        }

        // Enhanced Hero Section
        Section(
            Modifier
                .fillMaxWidth()
                .padding(48.px)
                .align(Alignment.CenterHorizontally)
                .toAttrs(),
        ) {
            H1(
                Modifier
                    .margin(bottom = 16.px)
                    .fontSize(56.px)
                    .fontWeight(FontWeight.Bold)
                    .color(if (colorMode.isLight) Colors.Black else Colors.White)
                    .toAttrs()
            ) {
                Text("Vikramaditya Khupse")
            }
            P(
                Modifier
                    .maxWidth(700.px)
                    .textAlign(TextAlign.Center)
                    .fontSize(20.px)
                    .margin(bottom = 24.px)
                    .color(if (colorMode.isLight) Colors.DarkGray else Colors.LightGray)
                    .toAttrs()
            ) {
                Text("Third Year B.Tech. Information Technology student at SGGSIE&T, Nanded")
            }
            P(
                Modifier
                    .maxWidth(700.px)
                    .textAlign(TextAlign.Center)
                    .fontSize(18.px)
                    .color(if (colorMode.isLight) Colors.DarkGray else Colors.LightGray)
                    .toAttrs()
            ) {
                Text("Motivated IT student with strong proficiency in Android development, Python programming, and modern development tools. Currently maintaining a CGPA of 7.92.")
            }
        }

        // Enhanced Skills Section
        Section(
            Modifier
                .fillMaxWidth()
                .padding(32.px)
                .align(Alignment.CenterHorizontally)
                .backgroundColor(if (colorMode.isLight) Colors.WhiteSmoke else Colors.DarkSlateGray)
                .toAttrs()
        ) {
            H2(
                Modifier
                    .margin(bottom = 24.px)
                    .fontSize(36.px)
                    .fontWeight(FontWeight.Bold)
                    .textAlign(TextAlign.Center)
                    .toAttrs()
            ) { Text("Technical Skills") }

            SimpleGrid(numColumns = numColumns(3), modifier = Modifier.maxWidth(1000.px).gap(24.px)) {
                SkillCard("Programming", "Python (Advanced), Java (Intermediate), Kotlin (Intermediate), OOP")
                SkillCard("Development", "Android Development, Jetpack Compose, Kobweb, Firebase")
                SkillCard("Tools", "Android Studio, Git, GitHub, PyCharm, IntelliJ IDEA, Figma, VSCode")
            }
        }

        // Enhanced Projects Section
        Section(
            Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(32.px)
                .toAttrs()
        ) {
            H2(
                Modifier
                    .margin(bottom = 24.px)
                    .fontSize(36.px)
                    .fontWeight(FontWeight.Bold)
                    .textAlign(TextAlign.Center)
                    .toAttrs()
            ) { Text("Featured Projects") }

            SimpleGrid(numColumns = numColumns(2), modifier = Modifier.maxWidth(1200.px).gap(24.px)) {
                ProjectCard(
                    "BoothMap",
                    "Election booth locator app serving 2,000+ users with precise navigation and booth information retrieval. Increased management efficiency by 30% through admin app.",
                    "Kotlin, Jetpack Compose, Google Maps API, Firebase"
                )
                ProjectCard(
                    "ClockInPro",
                    "Automated attendance system with geofencing, saving 10 hours/month in manual tracking for 100+ employees. Achieved 95% accuracy in location tracking.",
                    "Kotlin, Jetpack Compose, MySQL, CPanel, Google Maps API"
                )
                ProjectCard(
                    "SGGSIDcard",
                    "ID card generator for 1,500+ students, automating generation by 80% with live image capture. Enhanced verification accuracy by 30% with QR/barcode integration.",
                    "Python, Flask, Firebase, OpenCV, PIL"
                )
                ProjectCard(
                    "ExcelSync",
                    "Utility app for bulk uploading data from Excel/CSV files to Firebase with validation.",
                    "Kotlin, Firebase, Excel API"
                )
            }
        }

        // Leadership Section (New)
        Section(
            Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(32.px)
                .backgroundColor(if (colorMode.isLight) Colors.WhiteSmoke else Colors.DarkSlateGray)
                .toAttrs()
        ) {
            H2(
                Modifier
                    .margin(bottom = 24.px)
                    .fontSize(36.px)
                    .fontWeight(FontWeight.Bold)
                    .textAlign(TextAlign.Center)
                    .toAttrs()
            ) { Text("Leadership & Experience") }

            SimpleGrid(numColumns = numColumns(2), modifier = Modifier.maxWidth(1000.px).gap(24.px)) {
                ExperienceCard(
                    "Co-Organizer",
                    "Google Developer Group on Campus",
                    "Sep 2024 - Present",
                    "Lead campus workshops and events, facilitate knowledge sharing among student developers"
                )
                ExperienceCard(
                    "Vice President",
                    "SWAG Developer's Club",
                    "Feb 2023 - Present",
                    "Lead team development and organize technical workshops, manage app development initiatives"
                )
            }
        }

        // Enhanced Contact Section
        Section(
            Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(48.px)
                .backgroundColor(if (colorMode.isLight) Colors.White else Colors.Black)
                .toAttrs()
        ) {
            H2(
                Modifier
                    .margin(bottom = 24.px)
                    .fontSize(36.px)
                    .fontWeight(FontWeight.Bold)
                    .textAlign(TextAlign.Center)
                    .toAttrs()
            ) { Text("Get In Touch") }

            Row(
                Modifier.gap(24.px),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ContactLink("https://www.linkedin.com/in/vikramaditya-khupse-04838a259/", "LinkedIn")
                ContactLink("https://github.com/Phantom-VK", "GitHub")
                ContactLink("mailto:vikramadityakhupse@gmail.com", "Email")
                P(
                    Modifier
                        .color(if (colorMode.isLight) Colors.DarkGray else Colors.LightGray)
                        .toAttrs()
                ) {
                    Text("+91-8530292951")
                }
            }
        }
    }
}

@Composable
fun SkillCard(title: String, description: String) {
    var colorMode by ColorMode.currentState

    Box(
        Modifier
            .padding(16.px)
            .backgroundColor(if (colorMode.isLight) Colors.White else Colors.Black)
            .borderRadius(12.px)
            .boxShadow(BoxShadow.of(0.px, 4.px, 8.px, color = rgba(0, 0, 0, 0.1)))
    ) {
        Column(Modifier.padding(24.px)) {
            H3(
                Modifier
                    .margin(bottom = 12.px)
                    .fontSize(24.px)
                    .fontWeight(FontWeight.Bold)
                    .toAttrs()
            ) { Text(title) }
            P(
                Modifier
                    .fontSize(16.px)
                    .lineHeight(1.6)
                    .color(if (colorMode.isLight) Colors.DarkGray else Colors.LightGray)
                    .toAttrs()
            ) { Text(description) }
        }
    }
}

@Composable
fun ProjectCard(title: String, description: String, tech: String) {
    var colorMode by ColorMode.currentState

    Box(
        Modifier
            .padding(16.px)
            .backgroundColor(if (colorMode.isLight) Colors.White else Colors.Black)
            .borderRadius(12.px)
            .boxShadow(BoxShadow.of(0.px, 4.px, 8.px, color = rgba(0, 0, 0, 0.1)))
    ) {
        Column(Modifier.padding(24.px)) {
            H3(
                Modifier
                    .margin(bottom = 12.px)
                    .fontSize(24.px)
                    .fontWeight(FontWeight.Bold)
                    .toAttrs()
            ) { Text(title) }
            P(
                Modifier
                    .margin(bottom = 12.px)
                    .fontSize(16.px)
                    .lineHeight(1.6)
                    .toAttrs()
            ) { Text(description) }
            P(
                Modifier
                    .color(if (colorMode.isLight) Colors.DarkGray else Colors.LightGray)
                    .fontSize(14.px)
                    .toAttrs()
            ) {
                Text(tech)
            }
        }
    }
}

@Composable
fun ExperienceCard(title: String, organization: String, duration: String, description: String) {
    var colorMode by ColorMode.currentState

    Box(
        Modifier
            .padding(16.px)
            .backgroundColor(if (colorMode.isLight) Colors.White else Colors.Black)
            .borderRadius(12.px)
            .boxShadow(BoxShadow.of(0.px, 4.px, 8.px, color = rgba(0, 0, 0, 0.1)))
    ) {
        Column(Modifier.padding(24.px)) {
            H3(
                Modifier
                    .margin(bottom = 8.px)
                    .fontSize(24.px)
                    .fontWeight(FontWeight.Bold)
                    .toAttrs()
            ) { Text(title) }
            P(
                Modifier
                    .margin(bottom = 8.px)
                    .fontSize(18.px)
                    .fontWeight(FontWeight.SemiBold)
                    .color(if (colorMode.isLight) Colors.DarkGray else Colors.LightGray)
                    .toAttrs()
            ) { Text(organization) }
            P(
                Modifier
                    .margin(bottom = 12.px)
                    .fontSize(14.px)
                    .color(if (colorMode.isLight) Colors.Gray else Colors.LightGray)
                    .toAttrs()
            ) { Text(duration) }
            P(
                Modifier
                    .fontSize(16.px)
                    .lineHeight(1.6)
                    .color(if (colorMode.isLight) Colors.DarkGray else Colors.LightGray)
                    .toAttrs()
            ) { Text(description) }
        }
    }
}

@Composable
fun ContactLink(href: String, text: String) {
    var colorMode by ColorMode.currentState

    Link(
        path = href,
        text = text,
        Modifier
            .padding(12.px, 16.px)
            .backgroundColor(if (colorMode.isLight) Colors.LightGray else Colors.DarkSlateGray)
            .color(if (colorMode.isLight) Colors.Black else Colors.White)
            .borderRadius(8.px)
            .textDecorationLine(TextDecorationLine.None)
            .fontSize(16.px)
            .fontWeight(FontWeight.Medium)
    )
}