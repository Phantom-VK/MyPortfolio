package com.vikramaditya.portfolio.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.ThemeSwitchButton
import com.vikramaditya.portfolio.sections.ContactSection
import com.vikramaditya.portfolio.sections.ProfileCard
import com.vikramaditya.portfolio.sections.ProjectsSection
import com.vikramaditya.portfolio.sections.SkillSection
import kotlinx.browser.window


@Page
@Composable
fun HomePage() {
    var colorMode by ColorMode.currentState
    val breakpoint = rememberBreakpoint()

    ThemeSwitchButton(
        colorMode = colorMode,
        onClick = { colorMode = colorMode.opposite }
    )

        LaunchedEffect(window.location.href) {
        // See kobweb config in build.gradle.kts which sets up highlight.js
        js("hljs.highlightAll()")
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileCard(colorMode, breakpoint)
        SkillSection(colorMode, breakpoint)
        ProjectsSection(colorMode)
    }
}


