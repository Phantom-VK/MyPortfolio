package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Height
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.vikramaditya.portfolio.components.WhatIDoCard
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.widgets.SectionTitle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent

@Composable
fun WhatIDo(){

    var selectedCard by remember { mutableIntStateOf(-1) }

    Column(
        modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally){

        SectionTitle("What I do?")

        SimpleGrid(
            numColumns = numColumns(base = 1, sm = 1, md = 3),
            modifier = Modifier.fillMaxWidth()
                .padding(5.percent)
                .height(Height.FitContent)
                .id("what_i_do_grid")
        ){
            WhatIDoCard(
                iconImage = Res.Icon.HEXAWEB,
                description = "Software Development",
                isSelected = selectedCard == 0
            ){
                selectedCard = 0
            }
            Spacer()
            WhatIDoCard(
                iconImage = Res.Icon.DEV,
                description = "App Development",
                isSelected = selectedCard == 1
            ){
                selectedCard = 1
            }
            Spacer()
            WhatIDoCard(
                iconImage = Res.Icon.CUBOID,
                description = "UI/UX And" +
                        " Software Design",
                isSelected = selectedCard == 2
            ){
                selectedCard = 2
            }
        }
    }


}