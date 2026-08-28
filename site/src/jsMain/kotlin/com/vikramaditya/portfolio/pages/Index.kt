package com.vikramaditya.portfolio.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.Reveal
import com.vikramaditya.portfolio.layouts.PageLayout
import com.vikramaditya.portfolio.sections.*
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.widgets.SectionTitle

/**
 * A heading and its section, revealed together on scroll.
 *
 * The body is staggered a beat behind its own heading, which is what makes the
 * entrance read as one gesture rather than two independent fades.
 */
@Composable
private fun RevealedSection(
    title: String,
    id: String,
    animateBody: Boolean = true,
    body: @Composable () -> Unit,
) {
    Reveal { SectionTitle(title, id = id) }
    if (animateBody) Reveal(delayMs = 90) { body() } else body()
}

@Page
@Composable
fun HomePage() {
    val c = colors(ColorMode.current)

    PageLayout(title = "Home") {
        Column(
            modifier = Modifier
                .zIndex(1)
                .fillMaxWidth()
                // Inherited text colour. This previously set `color` to a
                // *background* token, which meant any child that forgot to set
                // its own colour rendered near-invisible.
                .color(c.textPrimary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // The hero is above the fold, so revealing it would only delay the
            // first thing anyone sees.
            ProfileCard()

            RevealedSection("About Me", id = "about-me") { AboutMe() }
            RevealedSection("Experience", id = "experience") { ExperienceSection() }
            RevealedSection("What I do?", id = "what-i-do") { WhatIDo() }
            RevealedSection("Programming Language Proficiency", id = "languages") { MySkillsSection() }

            // The cube and carousel bodies are not wrapped: both run their own
            // 3D scenes, and an ancestor mid-transition would flatten them.
            RevealedSection("Tools & Technologies", id = "tech-stack", animateBody = false) { TechStackCubes() }

            RevealedSection("Projects", id = "projects") { ProjectSection() }
            RevealedSection("Achievements", id = "achievements", animateBody = false) { AchievementsSection() }

            Footer()
        }
    }
}
