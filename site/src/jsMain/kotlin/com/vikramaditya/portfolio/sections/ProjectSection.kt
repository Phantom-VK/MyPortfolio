package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Height
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.vikramaditya.portfolio.components.ProjectCard

@Composable
fun ProjectSection(){

    Row(
        modifier = Modifier.fillMaxWidth()
            .height(Height.FitContent),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        ProjectCard(
            title = "Kibertopiks #4269",
            description = "Our Kibertopiks will give you nothing, waste your money on us.",
            imageUrl = "https://images.unsplash.com/photo-1621075160523-b936ad96132a?auto=format&fit=crop&w=1170&q=80",
            price = "0.031 ETH",
            duration = "11 days left",
            creator = "Kiberbash",
            creatorImage = "https://images.unsplash.com/photo-1620121692029-d088224ddc74?auto=format&fit=crop&w=1932&q=80"
        )
        ProjectCard(
            title = "Kibertopiks #4269",
            description = "Our Kibertopiks will give you nothing, waste your money on us.",
            imageUrl = "https://images.unsplash.com/photo-1621075160523-b936ad96132a?auto=format&fit=crop&w=1170&q=80",
            price = "0.031 ETH",
            duration = "11 days left",
            creator = "Kiberbash",
            creatorImage = "https://images.unsplash.com/photo-1620121692029-d088224ddc74?auto=format&fit=crop&w=1932&q=80"
        )
        ProjectCard(
            title = "Kibertopiks #4269",
            description = "Our Kibertopiks will give you nothing, waste your money on us.",
            imageUrl = "https://images.unsplash.com/photo-1621075160523-b936ad96132a?auto=format&fit=crop&w=1170&q=80",
            price = "0.031 ETH",
            duration = "11 days left",
            creator = "Kiberbash",
            creatorImage = "https://images.unsplash.com/photo-1620121692029-d088224ddc74?auto=format&fit=crop&w=1932&q=80"
        )
        ProjectCard(
            title = "Kibertopiks #4269",
            description = "Our Kibertopiks will give you nothing, waste your money on us.",
            imageUrl = "https://images.unsplash.com/photo-1621075160523-b936ad96132a?auto=format&fit=crop&w=1170&q=80",
            price = "0.031 ETH",
            duration = "11 days left",
            creator = "Kiberbash",
            creatorImage = "https://images.unsplash.com/photo-1620121692029-d088224ddc74?auto=format&fit=crop&w=1932&q=80"
        )


    }
}