package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.vikramaditya.portfolio.components.RecognitionCoverflow
import com.vikramaditya.portfolio.utils.AchievementsData
import com.vikramaditya.portfolio.utils.theme.Section
import com.vikramaditya.portfolio.utils.theme.Space

/**
 * Felicitations and recognitions.
 *
 * Content lives in [AchievementsData]; this section renders correctly whether it
 * holds zero, one, or many entries.
 */
@Composable
fun AchievementsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(leftRight = Space.lg, topBottom = Section.gapSm),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RecognitionCoverflow(
            items = AchievementsData.items,
            label = "Achievements",
            emptyTitle = "Felicitations",
            emptySubtitle = "Awards and recognitions are being added here.",
        )
    }
}
