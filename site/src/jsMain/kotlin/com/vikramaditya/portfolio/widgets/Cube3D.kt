package com.vikramaditya.portfolio.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.MixBlendMode
import com.varabyte.kobweb.compose.css.mixBlendMode
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignSelf
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import com.vikramaditya.portfolio.styles.BackStyle
import com.vikramaditya.portfolio.styles.BottomStyle
import com.vikramaditya.portfolio.styles.BoxCardStyle
import com.vikramaditya.portfolio.styles.ContainerStyle
import com.vikramaditya.portfolio.styles.FaceStyle
import com.vikramaditya.portfolio.styles.FrontStyle
import com.vikramaditya.portfolio.styles.LeftStyle
import com.vikramaditya.portfolio.styles.RightStyle
import com.vikramaditya.portfolio.styles.TopStyle
import com.vikramaditya.portfolio.utils.Res
import org.jetbrains.compose.web.css.AlignSelf
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

@Composable
fun Cube3D() {

    Box (
        modifier =
            Modifier
                .alignSelf(AlignSelf.Center)
                .padding(topBottom = 4.percent)
    ){

        Div(
            attrs = ContainerStyle.toModifier()
                .toAttrs()
        ) {
            Div(
                attrs = BoxCardStyle.toModifier()
                    .toAttrs()
            ) {
                // Front face
                Div(
                    attrs = FaceStyle.toModifier()
                        .then(FrontStyle.toModifier())
                        .toAttrs()
                ) {
                    Image(
                        src = Res.Logo.KOTLIN_LOGO,
                        modifier = Modifier.size(100.px)
                            .styleModifier {
                                mixBlendMode(MixBlendMode.Normal)
                            }
                    )
                }

                // Back face
                Div(
                    attrs = FaceStyle.toModifier()
                        .then(BackStyle.toModifier())
                        .toAttrs()
                ) {
                    Image(
                        src = Res.Logo.PYTHON_LOGO,
                        modifier = Modifier.size(100.px)
                            .styleModifier {
                                mixBlendMode(MixBlendMode.Normal)
                            }
                    )
                }

                // Right face
                Div(
                    attrs = FaceStyle.toModifier()
                        .then(RightStyle.toModifier())
                        .toAttrs()
                ) {
                    Image(
                        src = Res.Logo.ANDROID_LOGO,
                        modifier = Modifier.size(100.px)
                            .styleModifier {
                                mixBlendMode(MixBlendMode.Normal)
                            }
                    )
                }

                // Left face
                Div(
                    attrs = FaceStyle.toModifier()
                        .then(LeftStyle.toModifier())
                        .toAttrs()
                ) {
                    Image(
                        src = Res.Logo.MYSQL_LOGO,
                        modifier = Modifier.size(100.px)
                            .styleModifier {
                                mixBlendMode(MixBlendMode.Normal)
                            }
                    )
                }

                // Top face
                Div(
                    attrs = FaceStyle.toModifier()
                        .then(TopStyle.toModifier())
                        .toAttrs()
                ) {
                    Image(
                        src = Res.Logo.GIT_LOGO,
                        modifier = Modifier.size(100.px)
                            .styleModifier {
                                mixBlendMode(MixBlendMode.Normal)
                            }
                    )
                }

                // Bottom face
                Div(
                    attrs = FaceStyle.toModifier()
                        .then(BottomStyle.toModifier())
                        .toAttrs()
                ) {
                    Image(
                        src = Res.Logo.JAVA_LOGO,
                        modifier = Modifier.size(100.px)
                            .styleModifier {
                                mixBlendMode(MixBlendMode.Normal)
                            }
                    )
                }
            }
        }
    }

}
