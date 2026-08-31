package com.vikramaditya.portfolio.utils

/**
 * Top certifications, shown alongside felicitations in their own carousel.
 *
 * Empty is a supported state: the section renders its empty panel and the page
 * height does not change when the first entry is added.
 */
object CertificationsData {
    val items: List<Recognition> = listOf(
        Recognition(
            id = "aws-ai-practitioner",
            title = "AIF-C01",
            caption = "AWS Certified AI Practitioner",
            imageUrl = "images/certifications/awsai.png",
            intrinsicWidth = 960,
            intrinsicHeight = 600,
        ),
        Recognition(
            id = "oci-ai-foundations",
            title = "Oracle Certified Professional",
            caption = "AI Foundations Associate",
            imageUrl = "images/certifications/ociaifoundation.jpg",
            intrinsicWidth = 1600,
            intrinsicHeight = 1236,
        ),
        Recognition(
            id = "oci-data-science",
            title = "Oracle Certified Professional",
            caption = "Data Science Professional",
            imageUrl = "images/certifications/ocids.jpg",
            intrinsicWidth = 1600,
            intrinsicHeight = 1236,
        ),
        Recognition(
            id = "oci-generative-ai",
            title = "Oracle Certified Professional",
            caption = "Generative AI Professional",
            imageUrl = "images/certifications/ocigenai.jpg",
            intrinsicWidth = 1600,
            intrinsicHeight = 1236,
        ),
        Recognition(
            id = "agentic-ai-engineer",
            title = "Agentic AI Engineer",
            caption = "Agentic AI, MCP, AI Agents",
            imageUrl = "images/certifications/agenticai.jpg",
            intrinsicWidth = 1600,
            intrinsicHeight = 1190,
        ),
        Recognition(
            id = "complete-ai-ml",
            title = "Complete AI/ML",
            caption = "Data Science, Machine Learning, Deep Learning, Natural Language Processing",
            imageUrl = "images/certifications/aiml.jpg",
            intrinsicWidth = 1600,
            intrinsicHeight = 1190,
        ),
        Recognition(
            id = "android-development",
            title = "Android Development",
            caption = "Using Jetpack Compose and Kotlin",
            imageUrl = "images/certifications/android.jpg",
            intrinsicWidth = 1600,
            intrinsicHeight = 1190,
        )
    )
}
