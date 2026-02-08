package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.JustifyItems
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.justifyItems
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
        modifier = Modifier
            .fillMaxWidth()
            .justifyItems(JustifyItems.Center)
            .padding( 2.cssRem),
        numColumns = numColumns(base = 1, sm = 1, md = 2, lg = 4)
    ) {
        ProjectCard(
            title = "Vyom Assist",
            description = "AI-powered banking support system; special winner @ PSB iDEA Hackathon 2025 (₹1,00,000).",
            imageUrl = "images/projectthumbnails/vyomassist.png",
            mainTechStack = "Kotlin-Jetpack Compose",
            otherTechStack = "Android, Firebase, Hackathon build",
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
            title = "Production ML: Phishing Detection",
            description = "End-to-end ML pipeline with 97% accuracy, automated ETL, drift checks, and CI/CD to AWS.",
            imageUrl = "images/projectthumbnails/mlpipeline.jpg",
            mainTechStack = "Python · FastAPI",
            otherTechStack = "AWS, Docker, GitHub Actions",
            iconsList = listOf(
                Res.Logo.PYTHON_LOGO,
                Res.Logo.PYCHARM_LOGO,
                Res.Logo.GITHUB_LOGO
            ),
            onClick = {
                ctx.router.navigateTo("https://github.com/Phantom-VK")
            }
        )
        ProjectCard(
            title = "AgentTuring Math Tutor",
            description = "Developed AI-powered tutoring agent with RAG + MCP for math problem solving and web retrieval.",
            imageUrl = "images/projectthumbnails/agentturing.png",
            mainTechStack = "AI-ML",
            otherTechStack = "LangGraph, Qdrant,Tavily MCP, LLM, FastAPI",
            iconsList = listOf(
                Res.Logo.PYTHON_LOGO,
                Res.Logo.PYCHARM_LOGO,
            ),
            onClick = {
                ctx.router.navigateTo("https://github.com/Phantom-VK/agentturing")
            }
        )

        ProjectCard(
            title = "HR Chacha - AI-Powered Hiring Assistant",
            description = "LLM-driven hiring assistant automating 80% candidate screening with role-specific Q&A.",
            imageUrl = "images/projectthumbnails/hrchacha.jpg",
            mainTechStack = "AI-ML",
            otherTechStack = "Python, Streamlit, FastAPI, MongoDB, AWS",
            iconsList = listOf(
                Res.Logo.PYTHON_LOGO,
                Res.Logo.PYCHARM_LOGO
            ),
            onClick = {
                ctx.router.navigateTo("https://github.com/phantom-vk/HRChacha")
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
            title = "ICRS – Intelligent College Redressal System",
            description = "AI-powered grievance platform with RAG + pgvector semantic search, role-based React/Spring portals, JWT security, and email notifications.",
            imageUrl = "images/projectthumbnails/chatbot.jpg",
            mainTechStack = "Java Spring Boot · React · PostgreSQL + pgvector",
            otherTechStack = "RAG, JWT auth, email notifications",
            iconsList = listOf(
                Res.Logo.JAVA_LOGO,
                Res.Logo.INTELLIJ_LOGO,
                Res.Logo.GITHUB_LOGO
            ),
            onClick = {
                ctx.router.navigateTo("https://github.com/Phantom-VK/icrs")
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
