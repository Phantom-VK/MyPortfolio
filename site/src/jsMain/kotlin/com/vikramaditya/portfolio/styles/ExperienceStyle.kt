package com.vikramaditya.portfolio.styles

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.vh

val ExperienceStyle = CssStyle {
    base {
        Modifier
            .width(100.percent)
            .fontSize(3.cssRem)
            .minHeight(100.vh)
    }
    Breakpoint.ZERO {
        Modifier
            .width(100.percent)
            .fontSize(2.5.cssRem)
    }
    Breakpoint.SM {
        Modifier
            .width(100.percent)
            .fontSize(2.5.cssRem)
    }
    Breakpoint.MD {
        Modifier
            .width(100.percent)
            .fontSize(2.75.cssRem)
    }
    Breakpoint.LG {
        Modifier
            .width(100.percent)
            .fontSize(3.cssRem)
    }
}