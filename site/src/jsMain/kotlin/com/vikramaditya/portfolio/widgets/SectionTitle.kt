package com.vikramaditya.portfolio.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.SectionTitleStyle
import com.vikramaditya.portfolio.styles.SubheadlineTextStyle
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

@Composable
fun SectionTitle(
    sectionTitleText: String
) {
    Row {
        Div(attrs = SubheadlineTextStyle.toAttrs()) {
            SpanText(
                text = sectionTitleText,
                modifier = SectionTitleStyle.toModifier()
                    .align(Alignment.Bottom)
                    .color(
                        when (ColorMode.current) {
                            ColorMode.LIGHT -> Colors.Black
                            ColorMode.DARK -> Colors.White
                        }
                    )
                    .fontWeight(FontWeight.Bold)
            )
        }
        Image(
            src = Res.Icon.PORTAL_STAR,
            modifier = Modifier
                .align(Alignment.Top)
                .size(22.px)
        )
    }
}