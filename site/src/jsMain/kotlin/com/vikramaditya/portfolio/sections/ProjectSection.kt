package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.vikramaditya.portfolio.components.ProjectCard
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.utils.theme.Section
import com.vikramaditya.portfolio.utils.theme.Space
import org.jetbrains.compose.web.dom.Div

private data class Project(
    val title: String,
    val description: String,
    val imageUrl: String,
    val mainTechStack: String,
    val otherTechStack: String,
    val icons: List<String>,
    val href: String,
)

private val projects = listOf(
    Project(
        title = "Vyom Assist",
        description = "AI-powered banking support system; special winner @ PSB iDEA Hackathon 2025 (₹1,00,000).",
        imageUrl = "images/projectthumbnails/vyomassist.webp",
        mainTechStack = "Kotlin · Jetpack Compose",
        otherTechStack = "Android, Firebase, Hackathon build",
        icons = listOf(
            Res.Logo.KOTLIN_LOGO,
            Res.Logo.ANDROID_LOGO,
            Res.Logo.FIREBASE_LOGO,
            Res.Logo.MYSQL_LOGO,
            Res.Logo.FIGMA_LOGO,
        ),
        href = "https://github.com/Phantom-VK/Vyom-Assist",
    ),
    Project(
        title = "Production ML: Phishing Detection",
        description = "End-to-end ML pipeline with 97% accuracy, automated ETL, drift checks, and CI/CD to AWS.",
        imageUrl = "images/projectthumbnails/mlpipeline.webp",
        mainTechStack = "Python · FastAPI",
        otherTechStack = "AWS, Docker, GitHub Actions",
        icons = listOf(Res.Logo.PYTHON_LOGO, Res.Logo.PYCHARM_LOGO, Res.Logo.GITHUB_LOGO),
        href = "https://github.com/Phantom-VK",
    ),
    Project(
        title = "AgentTuring Math Tutor",
        description = "AI-powered tutoring agent with RAG + MCP for math problem solving and web retrieval.",
        imageUrl = "images/projectthumbnails/agentturing.webp",
        mainTechStack = "AI/ML",
        otherTechStack = "LangGraph, Qdrant, Tavily MCP, FastAPI",
        icons = listOf(Res.Logo.PYTHON_LOGO, Res.Logo.PYCHARM_LOGO),
        href = "https://github.com/Phantom-VK/agentturing",
    ),
    Project(
        title = "HR Chacha",
        description = "LLM-driven hiring assistant automating 80% of candidate screening with role-specific Q&A.",
        imageUrl = "images/projectthumbnails/hrchacha.webp",
        mainTechStack = "AI/ML",
        otherTechStack = "Python, Streamlit, FastAPI, MongoDB, AWS",
        icons = listOf(Res.Logo.PYTHON_LOGO, Res.Logo.PYCHARM_LOGO),
        href = "https://github.com/phantom-vk/HRChacha",
    ),
    Project(
        title = "ICRS",
        description = "AI grievance platform with RAG + pgvector semantic search, role-based React/Spring portals, and JWT security.",
        imageUrl = "images/projectthumbnails/chatbot.webp",
        mainTechStack = "Spring Boot · React",
        otherTechStack = "PostgreSQL + pgvector, RAG, JWT",
        icons = listOf(Res.Logo.JAVA_LOGO, Res.Logo.INTELLIJ_LOGO, Res.Logo.GITHUB_LOGO),
        href = "https://github.com/Phantom-VK/icrs",
    ),
    Project(
        title = "SRT Slicer",
        description = "Generates word-level timestamps from SRT subtitle files, with a packaged desktop installer.",
        imageUrl = "images/projectthumbnails/srtslicer.webp",
        mainTechStack = "Python",
        otherTechStack = "Inno Setup",
        icons = listOf(Res.Logo.PYTHON_LOGO, Res.Logo.PYCHARM_LOGO),
        href = "https://github.com/Phantom-VK/SRTSlicer",
    ),
    Project(
        title = "Portfolio",
        description = "This site. Built with the Kobweb framework, in Kotlin only, no HTML or JavaScript written by hand.",
        imageUrl = "images/projectthumbnails/portfolio.webp",
        mainTechStack = "Kobweb",
        otherTechStack = "Kotlin, Compose HTML",
        icons = listOf(Res.Logo.KOTLIN_LOGO, Res.Logo.INTELLIJ_LOGO, Res.Logo.CMP_LOGO),
        href = "https://github.com/Phantom-VK/MyPortfolio",
    ),
)

/**
 * Featured lead tile plus a grid. A uniform four-up grid gave every project the
 * same weight; the lead tile says which one to look at first.
 */
val ProjectGridStyle = CssStyle {
    base {
        Modifier.fillMaxWidth().styleModifier {
            property("display", "grid")
            property("grid-template-columns", "1fr")
            property("gap", "16px")
        }
    }
    Breakpoint.MD {
        Modifier.styleModifier {
            property("grid-template-columns", "repeat(2, 1fr)")
            property("gap", "20px")
        }
    }
    Breakpoint.LG {
        Modifier.styleModifier {
            property("grid-template-columns", "repeat(3, 1fr)")
            property("gap", "24px")
        }
    }
}

val ProjectFeaturedStyle = CssStyle {
    base { Modifier }
    Breakpoint.MD { Modifier.styleModifier { property("grid-column", "span 2") } }
    Breakpoint.LG { Modifier.styleModifier { property("grid-column", "span 3") } }
}

@Composable
fun ProjectSection() {
    val ctx = rememberPageContext()

    Div(
        attrs = ProjectGridStyle.toModifier()
            .padding(leftRight = Space.lg, topBottom = Section.gapSm)
            .toAttrs()
    ) {
        projects.forEachIndexed { index, project ->
            val isFeatured = index == 0
            ProjectCard(
                title = project.title,
                description = project.description,
                imageUrl = project.imageUrl,
                mainTechStack = project.mainTechStack,
                otherTechStack = project.otherTechStack,
                iconsList = project.icons,
                modifier = if (isFeatured) ProjectFeaturedStyle.toModifier() else Modifier,
                featured = isFeatured,
                onClick = { ctx.router.navigateTo(project.href) },
            )
        }
    }
}
