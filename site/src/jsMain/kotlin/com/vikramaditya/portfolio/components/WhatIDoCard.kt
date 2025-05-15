package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun WhatIDoCard(iconImage:String, description:String){

    val colorMode = ColorMode.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .borderRadius(7.px)
            .background(color = Res.Theme.GREY_BACKGROUND.color)
            .padding(5.percent)

    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ){
            Image(
                width = 50,
                height = 50,
                src = iconImage
            )

            SpanText(
                text = description,
                modifier = Modifier
                    .textAlign(TextAlign.Start)
                    .fontFamily("DM Sans")
                    .fontSize(2.5.em)
                    .color(if(colorMode.isDark) Res.Theme.GLASS_BOX_BORDER_COLOR_DARK.color else Color.white)
            )
        }
    }


}