package com.vikramaditya.portfolio.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.ThemeSwitchButton
import com.vikramaditya.portfolio.sections.ProfileCard
import com.vikramaditya.portfolio.sections.ProjectsSection
import com.vikramaditya.portfolio.sections.SkillSection
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
fun HomePage() {
    var colorMode by ColorMode.currentState
    val breakpoint = rememberBreakpoint()

    ThemeSwitchButton(
        colorMode = colorMode,
        onClick = { colorMode = colorMode.opposite }
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileCard(colorMode, breakpoint)
        SkillSection(colorMode, breakpoint)
        ProjectsSection(colorMode)
        ContactSection(colorMode)
    }
}

@Composable
fun ContactSection(colorMode: ColorMode) {
    Row(
        modifier = Modifier.gap(24.px),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ContactLink(
            href = "https://www.linkedin.com/in/vikramaditya-khupse-04838a259/",
            text = "LinkedIn",
        )
        ContactLink(
            href = "https://github.com/Phantom-VK",
            text = "GitHub",
        )
        ContactLink(
            href = "mailto:vikramadityakhupse@gmail.com",
            text = "Email",
        )
        ContactInfo(
            text = "+91-8530292951"
        )
    }
}

@Composable
fun ContactLink(
    href: String,
    text: String,
) {
    Link(
        path = href,
        modifier = Modifier
            .padding(topBottom = 12.px, leftRight = 16.px)
            .background(if (ColorMode.current.isLight) Colors.WhiteSmoke else Colors.DarkSlateGray)
            .color(if (ColorMode.current.isLight) Colors.Black else Colors.White)



    ) {
        Row(
            modifier = Modifier.gap(8.px).alignItems(alignItems = AlignItems("center")),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text)
        }
    }
}

@Composable
fun ContactInfo(
    text: String
) {
    Text(
        value = text
    )
}