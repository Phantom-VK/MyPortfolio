package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.vikramaditya.portfolio.components.RecognitionCoverflow
import com.vikramaditya.portfolio.utils.CertificationsData
import com.vikramaditya.portfolio.utils.theme.Section
import com.vikramaditya.portfolio.utils.theme.Space

/**
 * Top certifications.
 *
 * Content lives in [CertificationsData]; this section renders correctly whether it
 * holds zero, one, or many entries.
 */
@Composable
fun CertificationsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(leftRight = Space.lg, topBottom = Section.gapSm),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RecognitionCoverflow(
            items = CertificationsData.items,
            label = "Certifications",
            emptyTitle = "Certifications",
            emptySubtitle = "Top certifications are being added here.",
        )
    }
}
