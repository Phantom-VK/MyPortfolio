package com.vikramaditya.portfolio.pages


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.ContactMeButton
import com.vikramaditya.portfolio.layouts.PageLayout
import com.vikramaditya.portfolio.sections.*
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.widgets.SectionTitle
import kotlinx.browser.document


@Page
@Composable
fun HomePage() {
    var colorMode by ColorMode.currentState
    val breakpoint = rememberBreakpoint()





    PageLayout(
        title = "Home"
    ) {
        Column(
            modifier = Modifier
                .zIndex(1)
                .fillMaxWidth()
                .color(
                    if (colorMode.isDark) Res.Theme.DARK_THEME_BACKGROUND.color
                    else Res.Theme.LIGHT_THEME_BACKGROUND.color
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {


            ProfileCard(colorMode, breakpoint)
            SectionTitle("About Me", id = "about-me")
            AboutMe()
            SectionTitle("What I do?", id = "what-i-do")
            WhatIDo()
            SectionTitle("Programming Language Proficiency", id = "languages")
            MySkillsSection()
            SectionTitle("Tools & Technologies", id = "tech-stack")
            TechStackCubes()
            SectionTitle("Projects", id = "projects")
            ProjectSection()
            Footer()


        }
    }


}


