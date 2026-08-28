package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Height
import com.varabyte.kobweb.compose.css.JustifyItems
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.justifyItems
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.widgets.Cube3D
import org.jetbrains.compose.web.css.cssRem

@Composable
fun TechStackCubes(){

    SimpleGrid(
        modifier = Modifier
            .fillMaxWidth()
            .height(Height.FitContent)
            .justifyItems(JustifyItems.Center)
            .padding(topBottom = 2.cssRem)
            // Row gap matters most in the single-column mobile layout, where the
            // three cubes would otherwise stack with only their own internal
            // padding between them.
            .styleModifier {
                property("row-gap", "56px")
                property("column-gap", "24px")
            },
        numColumns = numColumns(base = 1, sm = 1, lg = 3)

    ) {
        Cube3D(
            icons = listOf(
                Res.Logo.LANGGRAPH_LOGO to "LangGraph",
                Res.Logo.KOTLIN_LOGO to "Kotlin",
                Res.Logo.CHATGPT_LOGO to "ChatGPT",
                Res.Logo.PYTHON_LOGO to "Python",
                Res.Logo.JAVA_LOGO to "Java",
                Res.Logo.POSTGRESQL_LOGO to "PostgreSQL",
            )
        )

        Cube3D(
            icons = listOf(
                Res.Logo.UBUNTU_LOGO to "Ubuntu",
                Res.Logo.VSCODE_LOGO to "VS Code",
                Res.Logo.ANDROID_LOGO to "Android",
                Res.Logo.FIGMA_LOGO to "Figma",
                Res.Logo.INTELLIJ_LOGO to "IntelliJ IDEA",
                Res.Logo.PYCHARM_LOGO to "PyCharm"
            )
        )

        Cube3D(
            icons = listOf(
                Res.Logo.CMP_LOGO to "Compose Multiplatform",
                Res.Logo.FIREBASE_LOGO to "Firebase",
                Res.Logo.GIT_LOGO to "Git",
                Res.Logo.DJANGO_LOGO to "Django",
                Res.Logo.GITHUB_LOGO to "GitHub",
                Res.Logo.FLASK_LOGO to "Flask"
            )
        )
    }

}
