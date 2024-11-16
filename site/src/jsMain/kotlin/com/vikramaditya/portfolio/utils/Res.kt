package com.vikramaditya.portfolio.utils


import com.varabyte.kobweb.compose.ui.graphics.Color

object Res {


    enum class Theme(val color: Color) {
        // Backgrounds
        DARK_THEME_BACKGROUND(Color.rgb(r = 18, g = 18, b = 18)),
        LIGHT_THEME_BACKGROUND(Color.rgb(r = 245, g = 245, b = 245)),
        GREY_BACKGROUND(Color.rgb(r = 40, g = 44, b = 52)),

        // Cards/Containers
        DARK_CARD_BACKGROUND(Color.rgb(r = 25, g = 25, b = 50)),
        LIGHT_CARD_BACKGROUND(Color.rgb(r = 230, g = 240, b = 255)),

        // Headings (Titles and Section Headers)
        PRIMARY_HEADING_TEXT(Color.rgb(r = 33, g = 150, b = 243)),

        // Body Text
        BODY_TEXT_DARK(Color.rgb(r = 33, g = 33, b = 33)),
        BODY_TEXT_LIGHT(Color.rgb(r = 210, g = 210, b = 210)),

        GLASS_BOX_BORDER_COLOR_LIGHT(Color.rgb(0xE9E9E9)),
        GLASS_BOX_BORDER_COLOR_DARK(Color.rgb(0x4E4E4E)),

        // Buttons (e.g., Social Media Buttons)
        PRIMARY_BUTTON(Color.rgb(r = 56, g = 142, b = 60)),
        BUTTON_HOVER_LIGHT(Color.rgb(r = 0, g = 188, b = 212)),
        BUTTON_HOVER_DARK(Color.rgb(r = 38, g = 198, b = 218)),

        // Borders
        CARD_BORDER_LIGHT(Color.rgb(r = 180, g = 180, b = 200)),
        CARD_BORDER_DARK(Color.rgb(r = 50, g = 50, b = 100)),

        // Icon Colors
        SOCIAL_ICON_BACKGROUND_LIGHT(Color.rgb(r = 225, g = 235, b = 255)),
        SOCIAL_ICON_BACKGROUND_DARK(Color.rgb(r = 50, g = 60, b = 100)),

        // Project Titles and Links
        PROJECT_TITLE_LIGHT(Color.rgb(r = 25, g = 118, b = 210)),
        PROJECT_TITLE_DARK(Color.rgb(r = 33, g = 150, b = 243)),

        // Existing colors (from initial code)
        BLUE(Color.rgb(r = 33, g = 150, b = 243)),
        LIGHT_BLUE(Color.rgb(r = 168, g = 236, b = 255)),
        DARK_BLUE(Color.rgb(r = 34, g = 63, b = 94)),
        GoogleBlue(Color.rgb(r = 66, g = 103, b = 210)),
        JavaOrange(Color.rgb(r =255,g = 165 ,b = 0))
    }


    object Icon {
        const val EMAIL_LIGHT = "images/icons/mail.svg"
        const val EMAIL_DARK = "mail_light.svg"
        const val PORTAL_STAR = "images/tools/portal_star.svg"
        const val GITHUB = "images/icons/github.svg"
        const val GITHUB_LIGHT = "images/icons/github_light.svg"
        const val INSTAGRAM = "images/icons/instagram.svg"
        const val INSTAGRAM_LIGHT = "images/icons/instagram_light.svg"
        const val LINKEDIN = "images/icons/linkedin.svg"
        const val LINKEDIN_LIGHT = "images/icons/linkedin_light.svg"
        const val SUN = "images/icons/sun.svg"
        const val MOON = "images/icons/moon.svg"
    }

    object Image {
        const val PROFILE_PHOTO = "mypfp.jpeg"

    }
    object Logo{
        const val ANDROID_LOGO = "images/tools/Android.svg"
        const val FIGMA_LOGO = "images/tools/Figma.svg"
        const val FIREBASE_LOGO = "images/tools/Firebase.svg"
        const val GIT_LOGO = "images/tools/Git.svg"
        const val INTELLIJ_LOGO = "images/tools/IntelliJ.svg"
        const val VSCODE_LOGO = "images/tools/VSCode.svg"
        const val JAVA_LOGO = "images/skills/Java.svg"
        const val KOTLIN_LOGO = "images/skills/Kotlin.svg"
        const val PYTHON_LOGO = "images/skills/python.svg"
        const val MYSQL_LOGO = "images/skills/mysql.svg"
    }

    object String {
        const val NAME = "Vikramaditya Khupse"
        const val PROFESSION = "Third Year Information Technology Student"
        const val ROBOTO_CONDENSED = "RobotoCondensedBold"
        const val ROBOTO_REGULAR = "RobotoRegular"
        const val MY_EMAIL = "mailto:vikramadityakhupse@gmail.com"
        const val SKILLS_TITLE = "Skills & Tools"
        const val PYTHON_CODE = """def profile_page():

    def about_me():
        print("Hello there!! I am Vikramaditya Khupse")
        print("Ambitious Student | Programmer | Tech Enthusiast")

    on_a_journey = True
    while on_a_journey:
        return  "to lead, learn, inspire, and drive meaningful change in tech"

    with strong_skills in ("Python", "Java", "Kotlin - Jetpack Compose")

    for to_dive in new_technologies:
        with full dedication
        if especially:
            if it spark my curiosity:
                print("Embracing every challenge as an opportunity to grow and innovate.")
 """
        const val JAVA_CODE = """public class ProfilePage {

    public static void aboutMe() {
        System.out.println("Hello there!! I am Vikramaditya Khupse");
        System.out.println("Ambitious Student | Programmer | Tech Enthusiast");
    }

    public static String journey() {
        boolean onAJourney = true;
        while (onAJourney) {
            return "to lead, learn, inspire, and drive meaningful change in tech";
        }
        return ""; // Unreachable, but required for compilation
    }

    public static void diveIntoTechnologies() {
        String[] strongSkills = {"Python", "Java", "Kotlin - Jetpack Compose"};

        for (String technology : strongSkills) {
            boolean especially = technology.contains("spark curiosity");
            if (especially) {
                System.out.println("Embracing every challenge as an opportunity to grow and innovate.");
            }
        }
    }

    public static void main(String[] args) {
        aboutMe();
        System.out.println(journey());
        diveIntoTechnologies();
    }
}
"""
        const val KOBWEB_CODE = """
fun profilePage() {
    fun aboutMe() {
        println("Hello there!! I am Vikramaditya Khupse")
        println("Ambitious Student | Programmer | Tech Enthusiast")
    }

    var onAJourney = true
    while (onAJourney) {
        return "to lead, learn, inspire, and drive meaningful change in tech"
    }

    val strongSkills = listOf("Python", "Java", "Kotlin - Jetpack Compose")

    for (toDive in newTechnologies) {
        with(fullDedication) {
            if (especially) {
                if (it.sparksCuriosity()) {
                    println("Embracing every challenge as an opportunity to grow and innovate.")
                }
            }
        }
    }
}"""


    }

    object Selected{
        var LANGUAGE = "language-python"
    }

    object Dimens {
        const val BORDER_RADIUS = 8
        const val MAX_CARD_WIDTH = 1000
        const val MAX_CARD_HEIGHT = 600
        const val ICON_SIZE = 24
        const val ICON_SIZE_LG = 32
    }
}