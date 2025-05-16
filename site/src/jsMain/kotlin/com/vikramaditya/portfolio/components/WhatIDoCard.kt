package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import androidx.compose.web.events.SyntheticMouseEvent
import com.varabyte.kobweb.compose.css.ScrollSnapAlign
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.HorizontalDivider
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.*

@Composable
fun WhatIDoCard(iconImage:String, description:String, isSelected:Boolean, onClick: (SyntheticMouseEvent) -> Unit = {  }){

    val colorMode = ColorMode.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .borderRadius(7.px)
            .background(color = Res.Theme.GREY_BACKGROUND.color)
            .onClick(onClick)

    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.percent),
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



        HorizontalDivider(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .borderTop(
                    3.px, LineStyle.Ridge
                )
                .color(if(isSelected) Res.Theme.PRIMARY_BUTTON.color else Color.transparent)
        )

    }



}