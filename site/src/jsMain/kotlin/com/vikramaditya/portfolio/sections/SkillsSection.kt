package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.MixBlendMode
import com.varabyte.kobweb.compose.css.mixBlendMode
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.style.toModifier
import com.vikramaditya.portfolio.components.GlassBox
import com.vikramaditya.portfolio.styles.ExperienceStyle
import com.vikramaditya.portfolio.utils.Res
import com.vikramaditya.portfolio.widgets.SectionTitle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px


@Composable
fun SkillsAndTools() {
    Column(
        modifier = ExperienceStyle.toModifier().id("skills_and_tools"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SectionTitle(Res.String.SKILLS_TITLE)

        SimpleGrid(
            modifier = Modifier.fillMaxWidth().margin(top = 0.5.cssRem),
            numColumns = numColumns(base = 1, sm = 2)
        ) {
            GlassBox(
                modifier = Modifier.margin(all = 2.cssRem)
            ) {
                SimpleGrid(
                    modifier = Modifier.padding(all = 1.cssRem),
                    numColumns = numColumns(base = 2, sm = 2, md = 3, lg = 4)
                ) {
                    GlassBox(
                        modifier = Modifier.size(65.px)
                            .margin(all = 0.6.cssRem)

                    ) {
                        Image(
                            src = Res.Logo.PYTHON_LOGO,
                            modifier = Modifier.size(42.px)
                        )
                    }
                    GlassBox(
                        modifier = Modifier.size(65.px)
                            .margin(all = 0.6.cssRem)
                    ) {
                        Image(
                            src = Res.Logo.JAVA_LOGO,
                            modifier = Modifier.size(42.px)
                                .styleModifier {
                                    mixBlendMode(MixBlendMode.Normal)
                                }
                        )
                    }
                    GlassBox(
                        modifier = Modifier.size(65.px)
                            .margin(all = 0.6.cssRem)

                    ) {
                        Image(
                            src = Res.Logo.KOTLIN_LOGO,
                            modifier = Modifier.size(42.px)
                        )
                    }
                    GlassBox(
                        modifier = Modifier.size(65.px)
                            .margin(all = 0.6.cssRem)

                    ) {
                        Image(
                            src = Res.Logo.MYSQL_LOGO,
                            modifier = Modifier.size(42.px)
                        )
                    }



                }
            }

            GlassBox(
                modifier = Modifier.margin(all = 2.cssRem)
            ) {
                SimpleGrid(
                    modifier = Modifier.padding(all = 1.cssRem),
                    numColumns = numColumns(base = 2, sm = 2, md = 3, lg = 4)
                ) {
                    GlassBox(
                        modifier = Modifier.size(65.px)
                            .margin(all = 0.6.cssRem)

                    ) {
                        Image(
                            src = Res.Logo.ANDROID_LOGO,
                            modifier = Modifier.size(42.px)
                        )
                    }
                    GlassBox(
                        modifier = Modifier.size(65.px)
                            .margin(all = 0.6.cssRem)

                    ) {
                        Image(
                            src = Res.Logo.INTELLIJ_LOGO,
                            modifier = Modifier.size(42.px)
                        )
                    }
                    GlassBox(
                        modifier = Modifier.size(65.px)
                            .margin(all = 0.6.cssRem)

                    ) {
                        Image(
                            src = Res.Logo.FIGMA_LOGO,
                            modifier = Modifier.size(42.px)
                        )
                    }
                    GlassBox(
                        modifier = Modifier.size(65.px)
                            .margin(all = 0.6.cssRem)

                    ) {
                        Image(
                            src = Res.Logo.FIREBASE_LOGO,
                            modifier = Modifier.size(42.px)
                        )
                    }

                    GlassBox(
                        modifier = Modifier.size(65.px)
                            .margin(all = 0.6.cssRem)

                    ) {
                        Image(
                            src = Res.Logo.VSCODE_LOGO,
                            modifier = Modifier.size(42.px)
                        )
                    }
                    GlassBox(
                        modifier = Modifier.size(65.px)
                            .margin(all = 0.6.cssRem)

                    ) {
                        Image(
                            src = Res.Logo.GIT_LOGO,
                            modifier = Modifier.size(42.px)
                        )
                    }

                }
            }
        }
    }
}