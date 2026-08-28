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
    val items: List<Achievement> = emptyList()
}
