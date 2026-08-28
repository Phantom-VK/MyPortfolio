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
        title = "NoRefund",
        description = "Offline desktop app that counts tokens and estimates LLM API cost across 21 models before you spend a cent — analysis never leaves your machine.",
        imageUrl = "images/projectthumbnails/norefund.png",
        mainTechStack = "React · TypeScript",
        otherTechStack = "Python, pywebview, Vite",
        icons = listOf(
            Res.Logo.REACT_LOGO,
            Res.Logo.TYPESCRIPT_LOGO,
            Res.Logo.PYTHON_LOGO,
            Res.Logo.VITE_LOGO,
            Res.Logo.GITHUB_LOGO,
        ),
        href = "https://github.com/Phantom-VK/NoRefund",
    ),
    Project(
        title = "RFPilot",
        description = "Agentic Map→Reduce→Consolidate pipeline that turns messy RFP PDFs and HTML bids into clean, structured JSON.",
        imageUrl = "images/projectthumbnails/rfpilot.png",
        mainTechStack = "Python",
        otherTechStack = "Docling, DeepSeek, asyncio",
        icons = listOf(Res.Logo.PYTHON_LOGO, Res.Logo.DEEPSEEK_LOGO, Res.Logo.PYCHARM_LOGO, Res.Logo.GITHUB_LOGO),
        href = "https://github.com/Phantom-VK/RFPilot",
    ),
    Project(
        title = "VR-ETL",
        description = "Vectorless, agentic RAG over long reports — a PageIndex tree, LangGraph orchestration, and streamed answers with citations, no vector database.",
        imageUrl = "images/projectthumbnails/vretl.png",
        mainTechStack = "FastAPI · LangGraph",
        otherTechStack = "PageIndex, DeepSeek, Sympy",
        icons = listOf(
            Res.Logo.FASTAPI_LOGO,
            Res.Logo.LANGGRAPH_LOGO,
            Res.Logo.DEEPSEEK_LOGO,
            Res.Logo.PYTHON_LOGO,
            Res.Logo.GITHUB_LOGO,
        ),
        href = "https://github.com/Phantom-VK/VR-ETL",
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
        title = "ICRS",
        description = "AI grievance platform with RAG + pgvector semantic search, role-based React/Spring portals, and JWT security.",
        imageUrl = "images/projectthumbnails/icrs.png",
        mainTechStack = "Spring Boot · React",
        otherTechStack = "PostgreSQL + pgvector, RAG, JWT",
        icons = listOf(Res.Logo.JAVA_LOGO, Res.Logo.INTELLIJ_LOGO, Res.Logo.GITHUB_LOGO),
        href = "https://github.com/Phantom-VK/icrs",
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
