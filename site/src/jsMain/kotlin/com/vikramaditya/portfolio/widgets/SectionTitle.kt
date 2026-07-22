package com.vikramaditya.portfolio.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.SectionTitleStyle
import com.vikramaditya.portfolio.styles.SubheadlineTextStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text

@Composable
fun SectionTitle(
    sectionTitleText: String,
    id: String
) {
    Row(modifier = Modifier
        .id(id)
        .padding(10.px)){
        Div(attrs = SubheadlineTextStyle.toAttrs()) {
            H2(
                attrs = SectionTitleStyle.toModifier()
                    .align(Alignment.Bottom)
                    .fontFamily("Share Tech Mono")
                    .fontWeight(FontWeight.Bold)
                    .margin(0.px)
                    .color(
                        when (ColorMode.current) {
                            ColorMode.LIGHT -> Colors.Black
                            ColorMode.DARK -> Colors.White
                        }
                    )
                    .toAttrs()
            ) {
                Text(sectionTitleText)
            }
        }

    }
}