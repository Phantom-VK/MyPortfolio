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
            imageUrl = "images/projectthumbnails/vyomassist.png",
            mainTechStack = "Kotlin-Jetpack Compose",
            otherTechStack = "PHP, SQL",
            iconsList = listOf(
                Res.Logo.KOTLIN_LOGO,
                Res.Logo.ANDROID_LOGO,
                Res.Logo.FIREBASE_LOGO,
                Res.Logo.MYSQL_LOGO,
                Res.Logo.FIGMA_LOGO
            ),
            onClick = {
                ctx.router.navigateTo("https://github.com/Phantom-VK/Vyom-Assist")
            }
        )

        ProjectCard(
            title = "AirSage AI",
            description = "Android application that visualizes live gas readings (CO, CO₂, NH₃, NOx, etc.) transmitted from a physical hardware device. ",
            imageUrl = "images/projectthumbnails/airsage.png",
            mainTechStack = "Kotlin-Jetpack Compose",
            otherTechStack = "Kotlin, Jetpack Compose, Firebase, Retrofit",
            iconsList = listOf(
                Res.Logo.KOTLIN_LOGO,
                Res.Logo.ANDROID_LOGO,
                Res.Logo.FIREBASE_LOGO,
                Res.Logo.MYSQL_LOGO,
                Res.Logo.FIGMA_LOGO
            ),
            onClick = {
                ctx.router.navigateTo("https://github.com/Phantom-VK/AirSageAi")
            }
        )
        ProjectCard(
            title = "IP Utils",
            description = "Multiplatform educational software to showcase detailed information of a particular IP address.",
            imageUrl = "images/projectthumbnails/iputils.png",
            mainTechStack = "Kotlin Multiplatform",
            otherTechStack = "Compose Multiplatform",
            iconsList = listOf(
                Res.Logo.CMP_LOGO,
                Res.Logo.KOTLIN_LOGO,
                Res.Logo.ANDROID_LOGO,
                Res.Logo.INTELLIJ_LOGO
            ),
            onClick = {
                ctx.router.navigateTo("https://github.com/Phantom-VK/IPUtils")
            }
        )

        ProjectCard(
            title = "Figma Designs",
            description = "Designed unique and visually compelling interfaces from scratch in Figma, focusing on clean UX and aesthetic UI.",
            imageUrl = "images/projectthumbnails/figma.png",
            mainTechStack = "Figma",
            otherTechStack = "",
            iconsList = listOf(
                Res.Logo.FIGMA_LOGO
            ),
            onClick = {
                ctx.router.navigateTo("https://www.figma.com/files/team/1234540862577380109/user/1219532462863680561?fuid=1219532462863680561")
            }
        )
        ProjectCard(
            title = "SRT Slicer",
            description = "A powerful tool to generate word-level timestamps from SRT subtitle files with advanced customization options. ",
            imageUrl = "images/projectthumbnails/srtslicer.png",
            mainTechStack = "Python",
            otherTechStack = "Inno Setup",
            iconsList = listOf(
                Res.Logo.PYTHON_LOGO,
                Res.Logo.PYCHARM_LOGO

            ),
            onClick = {
                ctx.router.navigateTo("https://github.com/Phantom-VK/SRTSlicer")
            }
        )
        ProjectCard(
            title = "Customer Care Chatbot",
            description = "A Customer Care chatbot for banks",
            imageUrl = "images/projectthumbnails/chatbot.jpg",
            mainTechStack = "Python",
            otherTechStack = "Transformers, Torch, Flask",
            iconsList = listOf(
                Res.Logo.PYTHON_LOGO,
                Res.Logo.PYCHARM_LOGO,
                Res.Logo.PYTORCH_LOGO
            ),
            onClick = {
                ctx.router.navigateTo("https://github.com/Phantom-VK/CustomerCare-Chatbot")
            }
        )
        ProjectCard(
            title = "Credit Checker",
            description = "A utility python project for studios and companies , to check if a particular channel has given them right credits or not in each video of the channel. ",
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSqtpKHYwjMNEl4NZmecZZNkUcFC3xsJ9AjzilzMyy56kJMPCVDGs0KUoI13D8-m-8z34k&usqp=CAU",
            mainTechStack = "Python",
            otherTechStack = "Pandas, Requests",
            iconsList = listOf(
                Res.Logo.PYTHON_LOGO,
                Res.Logo.PYCHARM_LOGO
            ),
            onClick = {
                ctx.router.navigateTo("https://github.com/Phantom-VK/CreditChekcr")
            }
        )

        ProjectCard(
            title = "Portfolio",
            description = "Portfolio Website using Kobweb framework, Kotlin language only.",
            imageUrl = "images/projectthumbnails/portfolio.png",
            mainTechStack = "Kobweb",
            otherTechStack = "Kotlin",
            iconsList = listOf(
                Res.Logo.KOTLIN_LOGO,
                Res.Logo.INTELLIJ_LOGO,
                Res.Logo.CMP_LOGO
            ),
            onClick = {
                ctx.router.navigateTo("https://github.com/Phantom-VK/MyPortfolio")
            }
        )
    }


}