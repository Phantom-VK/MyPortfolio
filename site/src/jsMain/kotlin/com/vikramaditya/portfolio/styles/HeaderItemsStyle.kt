package com.vikramaditya.portfolio.styles

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.style.CssStyle
import org.jetbrains.compose.web.css.*

val HeaderItemStyle = CssStyle {
    base {
        Modifier
            .size(48.px)
            .borderRadius(50.percent)
            .backgroundColor(Color.rgba(30, 30, 30, a = 0.4f))
            .display(DisplayStyle.Flex)
            .alignItems(AlignItems.Center)
            .justifyContent(JustifyContent.Center)

    }

}
