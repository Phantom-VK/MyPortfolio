package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.vikramaditya.portfolio.components.ProjectCard
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.cssRem

@Composable
fun ProjectSection(){
    val ctx = rememberPageContext()


    SimpleGrid(
        modifier = Modifier.fillMaxWidth().padding( 2.cssRem),
        numColumns = numColumns(base = 1, sm = 1, md = 2, lg = 4)
    ) {
        ProjectCard(
            title = "Vyom Assist",
            description = "Business Analytics-Based Appointment Management System.",
            imageUrl = "https://images.unsplash.com/photo-1621075160523-b936ad96132a?auto=format&fit=crop&w=1170&q=80",
            mainTechStack = "Kotlin-Jetpack Compose",
            otherTechStack = "PHP, SQL",
            iconsList = listOf(
                Res.Logo.KOTLIN_LOGO,
                Res.Logo.ANDROID_LOGO,
                Res.Logo.FIREBASE_LOGO,
                Res.Logo.MYSQL_LOGO
            ),
            onClick = {
                ctx.router.navigateTo("https://github.com/Phantom-VK/Vyom-Assist")
            }
        )
        ProjectCard(
            title = "IP Utils",
            description = "Multiplatform educational software to showcase detailed information of a particular IP address.",
            imageUrl = "https://images.unsplash.com/photo-1621075160523-b936ad96132a?auto=format&fit=crop&w=1170&q=80",
            mainTechStack = "Kotlin Multiplatform",
            otherTechStack = "Compose Multiplatform",
            iconsList = listOf(
                Res.Logo.KOTLIN_LOGO,
                Res.Logo.ANDROID_LOGO,
                Res.Logo.INTELLIJ_LOGO,
                Res.Logo.GIT_LOGO
            ),
            onClick = {
                ctx.router.navigateTo("https://github.com/Phantom-VK/IPUtils")
            }
        )

        ProjectCard(
            title = "Id Card Generator",
            description = "Web Application to generate ID Card for SGGSIE&T,Nanded.",
            imageUrl = "https://images.unsplash.com/photo-1621075160523-b936ad96132a?auto=format&fit=crop&w=1170&q=80",
            mainTechStack = "Python",
            otherTechStack = "Flask, Firebase",
            iconsList = listOf(
                Res.Logo.PYTHON_LOGO,
                Res.Logo.FIREBASE_LOGO
            ),
            onClick = {
                ctx.router.navigateTo("https://github.com/Phantom-VK/sggs-id")
            }
        )
        ProjectCard(
            title = "SRT Slicer",
            description = "A powerful tool to generate word-level timestamps from SRT subtitle files with advanced customization options. ",
            imageUrl = "https://images.unsplash.com/photo-1621075160523-b936ad96132a?auto=format&fit=crop&w=1170&q=80",
            mainTechStack = "Python",
            otherTechStack = "Inno Setup",
            iconsList = listOf(
                Res.Logo.PYTHON_LOGO,
                Res.Logo.ANDROID_LOGO,
                Res.Logo.FIREBASE_LOGO,
                Res.Logo.MYSQL_LOGO
            ),
            onClick = {
                ctx.router.navigateTo("https://github.com/Phantom-VK/SRTSlicer")
            }
        )
        ProjectCard(
            title = "Customer Care Chatbot",
            description = "A Customer Care chatbot for banks",
            imageUrl = "https://images.unsplash.com/photo-1621075160523-b936ad96132a?auto=format&fit=crop&w=1170&q=80",
            mainTechStack = "Python",
            otherTechStack = "Transformers, Torch, Flask",
            iconsList = listOf(
                Res.Logo.PYTHON_LOGO,
                Res.Logo.ANDROID_LOGO,
                Res.Logo.FIREBASE_LOGO,
                Res.Logo.MYSQL_LOGO
            ),
            onClick = {
                ctx.router.navigateTo("https://github.com/Phantom-VK/CustomerCare-Chatbot")
            }
        )
    }


}