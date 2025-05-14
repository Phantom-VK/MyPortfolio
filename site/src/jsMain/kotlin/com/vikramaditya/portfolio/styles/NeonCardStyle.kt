
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.CssName
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.selectors.after
import com.varabyte.kobweb.silk.style.selectors.before
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.JustifyContent

// This needs the following code in a css file:
// @property --rotate {
//    syntax: "<angle>";
//    initial-value: 132deg;
//    inherits: false;
//}
val RotateVar = StyleVariable.PropertyValue<CSSAngleNumericValue>("rotate")

@InitSilk
fun initStyles(ctx: InitSilkContext) = ctx.stylesheet.apply {

    registerStyleBase("a") {
        Modifier
            .color(Color.rgb(0x21253))
            .fontFamily("sans-serif")
            .fontWeight(FontWeight.Bold)
            .margin(top = 2.cssRem)
    }
}

val CardHeight = 65.vh

@CssName("my-card") // renamed to not intersect with the one defined in CSS; can be removed if the CSS one is removed
val CardStyle = CssStyle {
    base {
        Modifier
            .background(Color.rgb(0x191c29))
            .height(CardHeight)
            .width(CardHeight / 1.5)
            .padding(3.px)
            .position(Position.Relative)
            .borderRadius(6.px)
            .justifyContent(JustifyContent.Center)
            .alignItems(AlignItems.Center)
            .textAlign(TextAlign.Center)
            .display(DisplayStyle.Flex)
            .color(Color.rgba(88, 199, 250, 0f))
            .cursor(Cursor.Pointer)
    }
    before {
        Modifier
            .width(104.percent)
            .content("")
            .height(102.percent)
            .borderRadius(8.px)
            .backgroundImage(
                linearGradient(RotateVar.value()) {
                    add(Color.rgb(0x5ddcff))
                    add(Color.rgb(0x3c67e3), 43.percent)
                    add(Color.rgb(0x4e00c2))
                }
            )
            .position(Position.Absolute)
            .zIndex(-1)
            .top((-1).percent)
            .left((-2).percent)
            .animation(
                Spin.toAnimation(
                    duration = 5.s,  // Slightly increased duration for a smoother effect
                    timingFunction = AnimationTimingFunction.Linear,
                    iterationCount = AnimationIterationCount.Infinite
                )
            )

    }
    after {
        Modifier
            .position(Position.Absolute)
//            .content("")
            .top(CardHeight / 6)
            .zIndex(-1)
            .margin(0.px, autoLength)
            .filter(blur(CardHeight / 6))
            .transform { scale(0.8) }
            .backgroundImage(
                linearGradient(RotateVar.value()) {
                    add(Color.rgb(0x5ddcff))
                    add(Color.rgb(0x3c67e3), 43.percent)
                    add(Color.rgb(0x4e00c2))
                }
            )
            .opacity(1)
            .transition(Transition.of("opacity", 0.5.s))
            .animation(
                Spin.toAnimation(
                    duration = 3.s,  // Slightly increased duration for a smoother effect
                    timingFunction = AnimationTimingFunction.Linear,
                    iterationCount = AnimationIterationCount.Infinite
                )
            )

            .fillMaxSize()
    }
}

@CssName("my-spin")
val Spin = Keyframes {
    0.percent {
        Modifier.setVariable(RotateVar, 0.deg)
    }
    12.percent {
        Modifier.setVariable(RotateVar, 45.deg)
    }
    25.percent {
        Modifier.setVariable(RotateVar, 90.deg)
    }
    37.percent {
        Modifier.setVariable(RotateVar, 135.deg)
    }
    50.percent {
        Modifier.setVariable(RotateVar, 180.deg)
    }
    62.percent {
        Modifier.setVariable(RotateVar, 225.deg)
    }
    75.percent {
        Modifier.setVariable(RotateVar, 270.deg)
    }
    87.percent {
        Modifier.setVariable(RotateVar, 315.deg)
    }
    100.percent {
        Modifier.setVariable(RotateVar, 360.deg)
    }
}

