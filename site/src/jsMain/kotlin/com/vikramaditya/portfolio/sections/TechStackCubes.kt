package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Height
import com.varabyte.kobweb.compose.css.JustifyItems
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.justifyItems
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
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
            .padding(topBottom = 2.cssRem),
        numColumns = numColumns(base = 1, sm = 1, lg = 3)

    ) {
        Cube3D(
            icons = listOf(
                Res.Logo.KOTLIN_LOGO,
                Res.Logo.JAVA_LOGO,
                Res.Logo.MYSQL_LOGO,
                Res.Logo.PYTHON_LOGO,
                Res.Logo.C_LOGO,
                Res.Logo.CPP_LOGO,
            )
        )

        Cube3D(
            icons = listOf(
                Res.Logo.VSCODE_LOGO,
                Res.Logo.UBUNTU_LOGO,
                Res.Logo.ANDROID_LOGO,
                Res.Logo.FIGMA_LOGO,
                Res.Logo.INTELLIJ_LOGO,
                Res.Logo.PYCHARM_LOGO
            )
        )

        Cube3D(
            icons = listOf(
                Res.Logo.GIT_LOGO,
                Res.Logo.DJANGO_LOGO,
                Res.Logo.FIREBASE_LOGO,
                Res.Logo.CMP_LOGO,
                Res.Logo.GITHUB_LOGO,
                Res.Logo.FLASK_LOGO
            )
        )
    }

}
