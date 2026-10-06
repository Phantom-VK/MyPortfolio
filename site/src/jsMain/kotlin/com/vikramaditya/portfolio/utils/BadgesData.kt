package com.vikramaditya.portfolio.utils

/**
 * A single lightweight credential tile: no carousel caption, no issuer-year
 * line split — just enough to label the image. See [Recognition] for the
 * richer card model used by Achievements/Certifications.
 */
data class Badge(
    val id: String,
    val label: String,
    val issuer: String,
    val imageUrl: String,
    val intrinsicWidth: Int? = null,
    val intrinsicHeight: Int? = null,
)

/**
 * Lighter-weight credentials: job simulations, skill-test badges, workshop and
 * short-course completions, coding-streak badges. Shown in their own compact
 * grid below [CertificationsData], deliberately not mixed into that carousel —
 * these carry less weight than an exam-based certification and shouldn't
 * compete with it for the same attention.
 */
object BadgesData {
    val items: List<Badge> = listOf(
        Badge(
            id = "scaler-dsa-java",
            label = "DSA Problem Solving for Interviews using Java",
            issuer = "Scaler Topics",
            imageUrl = "images/badges/scaler-dsa-java.png",
            intrinsicWidth = 1000,
            intrinsicHeight = 665,
        ),
        Badge(
            id = "scaler-java-fundamentals",
            label = "Java Course — Mastering the Fundamentals",
            issuer = "Scaler Topics",
            imageUrl = "images/badges/scaler-java-fundamentals.png",
            intrinsicWidth = 999,
            intrinsicHeight = 656,
        ),
        Badge(
            id = "scaler-tensorflow",
            label = "Keras & TensorFlow for Deep Learning",
            issuer = "Scaler Topics",
            imageUrl = "images/badges/scaler-tensorflow.png",
            intrinsicWidth = 1000,
            intrinsicHeight = 639,
        ),
        Badge(
            id = "langgraph-dspy",
            label = "LangGraph & DSPy: Build Controllable AI Agents with Tools",
            issuer = "Udemy",
            imageUrl = "images/badges/langgraph-dspy.png",
            intrinsicWidth = 1000,
            intrinsicHeight = 721,
        ),
        Badge(
            id = "python-bootcamp",
            label = "The Complete Python Bootcamp From Zero to Hero",
            issuer = "Udemy",
            imageUrl = "images/badges/udemy-python-bootcamp.png",
            intrinsicWidth = 1000,
            intrinsicHeight = 721,
        ),
        Badge(
            id = "springpad-stock-market-ai",
            label = "Stock Market Using AI — Workshop",
            issuer = "SpringPad",
            imageUrl = "images/badges/springpad-stock-market-ai.png",
            intrinsicWidth = 1000,
            intrinsicHeight = 654,
        ),
        Badge(
            id = "simplilearn-genai-literacy",
            label = "SkillQuest — Generative AI Literacy",
            issuer = "Simplilearn SkillUp",
            imageUrl = "images/badges/simplilearn-genai-literacy.png",
            intrinsicWidth = 1000,
            intrinsicHeight = 706,
        ),
        Badge(
            id = "hack2skill-solution-challenge",
            label = "GDG on Campus Solution Challenge",
            issuer = "Hack2skill",
            imageUrl = "images/badges/hack2skill-solution-challenge.png",
            intrinsicWidth = 1000,
            intrinsicHeight = 750,
        ),
        Badge(
            id = "forage-aws-solutions-architecture",
            label = "Solutions Architecture Job Simulation",
            issuer = "AWS · Forage",
            imageUrl = "images/badges/forage-aws-solutions-architecture.png",
            intrinsicWidth = 1000,
            intrinsicHeight = 804,
        ),
        Badge(
            id = "forage-jpmorgan-swe",
            label = "Software Engineering Job Simulation",
            issuer = "JPMorgan Chase · Forage",
            imageUrl = "images/badges/forage-jpmorgan-swe.png",
            intrinsicWidth = 1000,
            intrinsicHeight = 804,
        ),
        Badge(
            id = "forage-tata-genai-analytics",
            label = "GenAI Powered Data Analytics Job Simulation",
            issuer = "Tata · Forage",
            imageUrl = "images/badges/forage-tata-genai-analytics.png",
            intrinsicWidth = 1000,
            intrinsicHeight = 804,
        ),
        Badge(
            id = "hackerrank-java-basic",
            label = "Java (Basic)",
            issuer = "HackerRank",
            imageUrl = "images/badges/hackerrank-java-basic.png",
            intrinsicWidth = 1000,
            intrinsicHeight = 725,
        ),
        Badge(
            id = "hackerrank-python-basic",
            label = "Python (Basic)",
            issuer = "HackerRank",
            imageUrl = "images/badges/hackerrank-python-basic.png",
            intrinsicWidth = 1000,
            intrinsicHeight = 723,
        ),
        Badge(
            id = "hackerrank-problem-solving-basic",
            label = "Problem Solving (Basic)",
            issuer = "HackerRank",
            imageUrl = "images/badges/hackerrank-problem-solving-basic.png",
            intrinsicWidth = 1000,
            intrinsicHeight = 723,
        ),
        Badge(
            id = "hackerrank-python-5star",
            label = "Python — 5 Stars",
            issuer = "HackerRank",
            imageUrl = "images/badges/hackerrank-python-5star.png",
            intrinsicWidth = 379,
            intrinsicHeight = 401,
        ),
        Badge(
            id = "leetcode-50days-2024",
            label = "50 Days Badge 2024",
            issuer = "LeetCode",
            imageUrl = "images/badges/leetcode-50days-2024.png",
            intrinsicWidth = 661,
            intrinsicHeight = 1000,
        ),
        Badge(
            id = "leetcode-50days-2025",
            label = "50 Days Badge 2025",
            issuer = "LeetCode",
            imageUrl = "images/badges/leetcode-50days-2025.png",
            intrinsicWidth = 687,
            intrinsicHeight = 1000,
        ),
        Badge(
            id = "leetcode-100days-2024",
            label = "100 Days Badge 2024",
            issuer = "LeetCode",
            imageUrl = "images/badges/leetcode-100days-2024.png",
            intrinsicWidth = 661,
            intrinsicHeight = 1000,
        ),
        Badge(
            id = "leetcode-100days-2025",
            label = "100 Days Badge 2025",
            issuer = "LeetCode",
            imageUrl = "images/badges/leetcode-100days-2025.png",
            intrinsicWidth = 687,
            intrinsicHeight = 1000,
        ),
    )
}
