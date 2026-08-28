package com.vikramaditya.portfolio.utils

/**
 * One felicitation, award, or recognition.
 *
 * Only [id] and [title] are required, so entries can be added now and their
 * images supplied later without touching the carousel. A null [imageUrl] renders
 * a designed typographic face rather than a broken image.
 */
data class Achievement(
    val id: String,
    val title: String,
    val issuer: String? = null,
    val year: String? = null,
    /** One line shown under the carousel while this card is active. */
    val caption: String = "",
    /** Path relative to `resources/public`, e.g. "images/achievements/foo.webp". */
    val imageUrl: String? = null,
    /** Leave empty for a decorative image; the card is already labelled by [title]. */
    val imageAlt: String = "",
    /** Optional link to a certificate or write-up. Rendered only on the active card. */
    val href: String? = null,
    /** Feeds the `<img>` width/height attributes to reserve space and avoid layout shift. */
    val intrinsicWidth: Int? = null,
    val intrinsicHeight: Int? = null,
)

/**
 * Add entries here as felicitation images land in
 * `site/src/jsMain/resources/public/images/achievements/`.
 *
 * Empty is a supported state: the section renders its empty panel and the page
 * height does not change when the first entry is added.
 */
object AchievementsData {
    val items: List<Achievement> = listOf(
        Achievement(
            id = "hackfusion-bog",
            title = "HackFusion — Felicitated by Board of Governance",
            issuer = "HackFusion National Hackathon",
            caption = "Served as Vice President and co-organizer; felicitated by the Board of Governance.",
            imageUrl = "images/achievements/BOG_hackfusion_pic.jpeg",
        ),
        Achievement(
            id = "psb-idea-hackathon",
            title = "PSBs iDEA Hackathon — Winner, ₹1,00,000",
            issuer = "Union Bank of India · PSBs Hackathon Series",
            year = "2025",
            caption = "Led the team to win the Special Category, awarded a ₹1 lakh prize.",
            imageUrl = "images/achievements/PSB_Hackathon_win.jpeg",
        ),
        Achievement(
            id = "ieee-rcsm-paper",
            title = "Published Paper — IEEE RCSM 2025",
            issuer = "IEEE Madhya Pradesh Section · MANIT Bhopal",
            year = "2025",
            caption = "Presented research paper at the 1st International Conference on Recent Trends in Computing and Smart Mobility, MANIT Bhopal.",
            imageUrl = "images/achievements/ResearchPaper1.jpeg",
        ),
        Achievement(
            id = "best-performer-club",
            title = "Best Performer Club of the Year",
            caption = "Won against clubs a decade older, just two years after founding.",
            imageUrl = "images/achievements/WellPerformerClubPic.jpeg",
        ),
    )
}
