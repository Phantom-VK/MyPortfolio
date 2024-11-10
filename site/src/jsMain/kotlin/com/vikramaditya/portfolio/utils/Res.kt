package com.vikramaditya.portfolio.utils


import com.varabyte.kobweb.compose.ui.graphics.Color

object Res {


    enum class Theme(val color: Color) {
        // Backgrounds
        DARK_THEME_BACKGROUND(Color.rgb(r = 18, g = 18, b = 18)),
        LIGHT_THEME_BACKGROUND(Color.rgb(r = 245, g = 245, b = 245)),

        // Cards/Containers
        DARK_CARD_BACKGROUND(Color.rgb(r = 25, g = 25, b = 50)),
        LIGHT_CARD_BACKGROUND(Color.rgb(r = 230, g = 240, b = 255)),

        // Headings (Titles and Section Headers)
        PRIMARY_HEADING_TEXT(Color.rgb(r = 33, g = 150, b = 243)),

        // Body Text
        BODY_TEXT_DARK(Color.rgb(r = 33, g = 33, b = 33)),
        BODY_TEXT_LIGHT(Color.rgb(r = 210, g = 210, b = 210)),

        // Skill Category Labels
        SKILL_LABEL_GRADIENT_START(Color.rgb(r = 123, g = 31, b = 162)),
        SKILL_LABEL_GRADIENT_END(Color.rgb(r = 0, g = 188, b = 212)),
        SKILL_LABEL_GRADIENT_DARK_START(Color.rgb(r = 85, g = 26, b = 139)),
        SKILL_LABEL_GRADIENT_DARK_END(Color.rgb(r = 0, g = 150, b = 136)),

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
        GRADIENT_ONE(Color.rgb(r = 161, g = 196, b = 253)),
        GRADIENT_ONE_DARK(Color.rgb(r = 19, g = 38, b = 58)),
        GRADIENT_TWO(Color.rgb(r = 194, g = 233, b = 251)),
        GRADIENT_TWO_DARK(Color.rgb(r = 20, g = 46, b = 73)),
        GoogleBlue(Color.rgb(r = 66, g = 103, b = 210))
    }


    object Icon {
        const val EMAIL_LIGHT = "mail.svg"
        const val EMAIL_DARK = "mail_light.svg"
        const val GITHUB = "github.svg"
        const val GITHUB_LIGHT = "github_light.svg"
        const val INSTAGRAM = "instagram.svg"
        const val INSTAGRAM_LIGHT = "instagram_light.svg"
        const val LINKEDIN = "linkedin.svg"
        const val LINKEDIN_LIGHT = "linkedin_light.svg"
        const val SUN = "sun.svg"
        const val MOON = "moon.svg"
    }

    object Image {
        const val PROFILE_PHOTO = "mypfp.jpeg"
    }

    object String {
        const val NAME = "Vikramaditya Khupse"
        const val PROFESSION = "Third Year Information Technology Student"
        const val ABOUT_ME =
            "Motivated IT student with strong proficiency in Android development using Kotlin and Jetpack Compose, advanced\n" +
                    "skills in Python and Java. Distinguished by exceptional problem-solving abilities and rapid technology adaptation.\n" +
                    "Experienced in building Android apps and Python projects, with multiple certifications and HackerRank\n" +
                    "achievements. Demonstrates leadership through roles in Google Developer Group and SWAG Developer’s Club,\n" +
                    "SGGSIE&T."
        const val BUTTON_TEXT = "Get in touch"
        const val ROBOTO_CONDENSED = "RobotoCondensedBold"
        const val ROBOTO_REGULAR = "RobotoRegular"
        const val MY_EMAIL = "mailto:vikramadityakhupse@gmail.com"
        const val COLOR_MODE_KEY = "app:colorMode"

    }

    object Dimens {
        const val BORDER_RADIUS = 8
        const val MAX_CARD_WIDTH = 1000
        const val MAX_CARD_HEIGHT = 600
        const val ICON_SIZE = 24
        const val ICON_SIZE_LG = 32
    }
}