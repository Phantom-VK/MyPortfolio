package com.vikramaditya.portfolio.styles

import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.AlignContent
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.css.functions.toImage
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.AlignItems

private val frameSize = 15.px
private val border = 2.px
private val imgWidth = 200.px
private var styleColor = Color.rgb(0x7B3B3B)
private var i: CSSCalcValue<CSSUnitLengthOrPercentage>
    get() {
        TODO()
    }
    set(value) {}


val ImgStyle = CssStyle {
    base {
        Modifier
            .width(imgWidth)
            .aspectRatio(1)
            .objectFit(ObjectFit.Cover)
            .padding(2*frameSize)
            .background(
                Background.of(
                    image = linearGradient(90.deg) {
                        add(styleColor, 2.px)
                        add(Color.rgba(0x00000000), 0.percent, 100.percent - 2.px)
                        add(styleColor, 0.percent)
                    }.toImage()
                ),
                Background.of(
                    image = linearGradient {
                        add(styleColor, 2.px)
                        add(Color.rgba(0x00000000), 0.percent, 100.percent - 2.px)
                        add(styleColor, 0.percent)
                    }.toImage(),
                )
            )
            .outline(width = imgWidth / 2, LineStyle.Solid, Color.rgba(0x00000099))
            .outlineOffset(((imgWidth / -2)) - 2*frameSize)
            .transition(Transition.of("all", 0.4.s))
            .cursor(Cursor.Pointer)
    }
    hover {
        i = 100.percent - 2*frameSize
        Modifier
            .outline(border, LineStyle.Solid, styleColor)
            .outlineOffset(frameSize / -2)


    }
}
val BodyStyle = CssStyle {
    base {
        Modifier
            .margin(0.px)
            .minHeight(100.vh)
            .display(DisplayStyle.Grid)
            .placeContent(alignContent = AlignContent.Center, justifyContent = JustifyContent.Center)
            .alignItems(AlignItems.Center)
            .gridAutoFlow(GridAutoFlow.Column)
            .gap(20.px)
            .background(Color.rgb(0xe8e8e8))
    }

}
