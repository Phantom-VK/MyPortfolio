package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Height
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.widgets.Cube3D
import org.jetbrains.compose.web.css.cssRem

@Composable
fun TechStackCubes(){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Height.FitContent)
            .padding(topBottom = 2.cssRem),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Cube3D(
            icons = listOf(
                Res.Logo.KOTLIN_LOGO,
                Res.Logo.JAVA_LOGO,
                Res.Logo.GIT_LOGO,
                Res.Logo.VSCODE_LOGO,
                Res.Logo.UBUNTU_LOGO,
                Res.Logo.MYSQL_LOGO
            ),
            modifier = Modifier.margin(right = 5.cssRem)
        )

        Cube3D(
            icons = listOf(
                Res.Logo.PYTHON_LOGO,
                Res.Logo.DJANGO_LOGO,
                Res.Logo.FLASK_LOGO,
                Res.Logo.ANDROID_LOGO,
                Res.Logo.FIGMA_LOGO,
                Res.Logo.INTELLIJ_LOGO
            ),
            modifier = Modifier.margin(right = 5.cssRem)
        )

        Cube3D(
            icons = listOf(
                Res.Logo.C_LOGO,
                Res.Logo.CPP_LOGO,
                Res.Logo.FIREBASE_LOGO,
                Res.Logo.PYCHARM_LOGO,
                Res.Logo.CMP_LOGO,
                Res.Logo.GITHUB_LOGO
            )
        )
    }

}
