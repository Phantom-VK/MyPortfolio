package com.vikramaditya.portfolio.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.TextDecorationLine
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

    Column(
        Modifier.fillMaxWidth().minHeight(100.vh),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header with Theme Toggle
        Row(
            Modifier.fillMaxWidth().padding(16.px),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            P(
                attrs = Modifier
                    .fontSize(44.px)
                    .fontStyle(FontStyle.Normal)
                    .toAttrs()

            ){
                Text(value = "VK"
                )
            }

            Button(
                onClick = { colorMode = colorMode.opposite },
                Modifier.borderRadius(50.percent).padding(8.px)
            ) {
                if (colorMode.isLight) Text("🌙") else Text("☀️")
            }
        }

        // Hero Section
        Section(
            Modifier.fillMaxWidth().padding(32.px).align(Alignment.CenterHorizontally).toAttrs(),
        ) {
            H1(
                Modifier.margin(bottom = 16.px)
                    .fontSize(48.px)
                    .fontWeight(FontWeight.Bold)
                    .color(if (colorMode.isLight) Colors.Black else Colors.White)
                    .toAttrs()
            ) {
                Text("Vikramaditya Khupse")
            }
            P(
                Modifier.maxWidth(600.px).textAlign(TextAlign.Center)
                    .color(if (colorMode.isLight) Colors.DarkGray else Colors.LightGray).toAttrs()
            ) {
                Text("Motivated IT student with strong proficiency in Android development using Kotlin and Jetpack Compose. Building innovative solutions with exceptional problem-solving abilities.")
            }
        }

        // Skills Section
        Section(
            Modifier.fillMaxWidth()
                .padding(32.px)
                .align(Alignment.CenterHorizontally)
                .backgroundColor(if (colorMode.isLight) Colors.LightGray else Colors.DarkSlateGray)
                .toAttrs()
        ) {
            H2(Modifier.margin(bottom = 24.px).toAttrs()) { Text("Technical Skills") }

            SimpleGrid(numColumns = numColumns(3), modifier = Modifier.maxWidth(800.px).gap(16.px)) {
                SkillCard("Android Development", "Kotlin, Jetpack Compose")
                SkillCard("Programming", "Python, Java")
                SkillCard("Tools", "Android Studio, Git, Firebase")
            }
        }

        // Projects Section
        Section(
            Modifier.fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(32.px)
                .toAttrs()
        ) {
            H2(Modifier.margin(bottom = 24.px).toAttrs()) { Text("Featured Projects") }

            SimpleGrid(numColumns = numColumns(2), modifier = Modifier.maxWidth(1000.px).gap(24.px)) {
                ProjectCard(
                    "BoothMap",
                    "Election booth locator app with Google Maps integration",
                    "Kotlin, Jetpack Compose, Firebase"
                )
                ProjectCard(
                    "ClockInPro",
                    "Automated attendance system with geofencing",
                    "Kotlin, MySQL, Google Maps API"
                )
            }
        }

        // Contact Section
        Section(
            Modifier.fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(32.px)
                .backgroundColor(if (colorMode.isLight) Colors.LightGray else Colors.DarkSlateGray)
                .toAttrs()
        ) {
            H2(Modifier.margin(bottom = 24.px).toAttrs()) { Text("Get In Touch") }

            Row(Modifier.gap(16.px)) {
                Link(
                    "https://linkedin.com/in/vikramaditya-khupse",
                    "LinkedIn",
                    Modifier.textDecorationLine(TextDecorationLine.None)
                )
                Link(
                    "https://github.com/Phantom-VK",
                    "GitHub",
                    Modifier.textDecorationLine(TextDecorationLine.None)
                )
                Link(
                    "mailto:vikramadityakhupse@gmail.com",
                    "Email",
                    Modifier.textDecorationLine(TextDecorationLine.None)
                )
            }
        }
    }
}

@Composable
fun SkillCard(title: String, description: String) {
    var colorMode by ColorMode.currentState

    Box(
        Modifier.padding(16.px)
            .backgroundColor(if (colorMode.isLight) Colors.White else Colors.Black)
            .borderRadius(8.px)
        //.boxShadow(0.px, 2.px, 4.px, Colors.Gray.copy(alpha = 0.2f))
    ) {
        Column(Modifier.padding(16.px)) {
            H3(Modifier.margin(bottom = 8.px).toAttrs()) { Text(title) }
            P { Text(description) }
        }
    }
}

@Composable
fun ProjectCard(title: String, description: String, tech: String) {
    var colorMode by ColorMode.currentState

    Box(
        Modifier.padding(16.px)
            .backgroundColor(if (colorMode.isLight) Colors.White else Colors.Black)
            .borderRadius(8.px)
        //.boxShadow(0.px, 2.px, 4.px, Colors.Gray.copy(alpha = 0.2f))
    ) {
        Column(Modifier.padding(16.px)) {
            H3(Modifier.margin(bottom = 8.px).toAttrs()) { Text(title) }
            P(Modifier.margin(bottom = 8.px).toAttrs()) { Text(description) }
            P(
                Modifier.color(if (colorMode.isLight) Colors.DarkGray else Colors.LightGray).toAttrs()
            ) {
                Text(tech)
            }
        }
    }
}

