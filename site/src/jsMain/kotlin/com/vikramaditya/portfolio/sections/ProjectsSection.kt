package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignContent
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.components.ProjectCard
import org.jetbrains.compose.web.css.AlignContent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Section


@Composable
fun ProjectsSection(colorMode: ColorMode) {
    Section(
        Modifier
            .fillMaxWidth()
            .padding(15.px)
            .alignContent(AlignContent.Center)
            .toAttrs()
    ) {
        SimpleGrid(
            numColumns = numColumns(base = 1,sm = 2, md = 4),
            modifier = Modifier.fillMaxWidth().gap(24.px)
        ) {
            ProjectCard(
                "BoothMap",
                "Election booth locator app serving 2,000+ users with precise navigation and booth information retrieval.",
                "Kotlin, Jetpack Compose, Google Maps API, Firebase",
                colorMode = colorMode
            )
            ProjectCard(
                "ClockInPro",
                "Automated attendance system with geofencing, saving 10 hours/month in manual tracking.",
                "Kotlin, Jetpack Compose, MySQL, CPanel, Google Maps API",
                colorMode = colorMode
            )
            ProjectCard(
                "SGGSIDcard",
                "ID card generator for 1,500+ students, automating generation by 80% with live image capture.",
                "Python, Flask, Firebase, OpenCV, PIL",
                colorMode = colorMode
            )
            ProjectCard(
                "ExcelSync",
                "Utility app for bulk uploading data from Excel/CSV files to Firebase with validation.",
                "Kotlin, Firebase, Excel API",
                colorMode = colorMode
            )
        }
    }
}

