package com.vikramaditya.portfolio.pages


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignContent
import com.varabyte.kobweb.compose.ui.modifiers.alignSelf
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.layouts.PageLayout
import com.vikramaditya.portfolio.sections.*
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.widgets.Cube3D
import com.vikramaditya.portfolio.widgets.SectionTitle
import org.jetbrains.compose.web.css.AlignContent
import org.jetbrains.compose.web.css.AlignSelf
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px


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
            SectionTitle("My Skills", id = "skills")
            MySkillsSection()
            SectionTitle("Tech Stack", id = "tech-stack")
            SimpleGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(left = 350.px),
                numColumns = numColumns(base = 1, lg = 2)
            ){
                Cube3D()
                Cube3D()
            }
            SectionTitle("Project", id = "projects")
            ProjectSection()

        }
    }


}


