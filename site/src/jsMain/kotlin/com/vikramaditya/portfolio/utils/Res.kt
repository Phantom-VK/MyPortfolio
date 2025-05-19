package com.vikramaditya.portfolio.utils


import com.varabyte.kobweb.compose.ui.graphics.Color

object Res {


    enum class Theme(val color: Color) {

        // Backgrounds
        DARK_THEME_BACKGROUND(Color.rgb(r = 23, g = 23, b = 23)),
        LIGHT_THEME_BACKGROUND(Color.rgb(r = 220, g = 220, b = 220)),
        GREY_BACKGROUND(Color.rgb(r = 40, g = 44, b = 52)),

        // Cards/Containers
        DARK_CARD_BACKGROUND(Color.rgb(r = 25, g = 25, b = 50)),
        LIGHT_CARD_BACKGROUND(Color.rgb(r = 230, g = 240, b = 255)),


        GLASS_BOX_BORDER_COLOR_LIGHT(Color.rgb(0xE9E9E9)),
        GLASS_BOX_BORDER_COLOR_DARK(Color.rgb(0x4E4E4E)),

        // Buttons (e.g., Social Media Buttons)
        THEME_GREEN(Color.rgb(r = 39, g = 174, b = 96)),
        THEME_GREEN_NEON(Color.rgb(44, 255, 5)),
        MATRIX_GLOW(Color.rgb(0, 255, 65)),


        // Borders
        CARD_BORDER_LIGHT(Color.rgb(r = 180, g = 180, b = 200)),
        CARD_BORDER_DARK(Color.rgb(r = 50, g = 50, b = 100)),

        // Icon Colors
        SOCIAL_ICON_BACKGROUND_LIGHT(Color.rgb(r = 225, g = 235, b = 255)),
        SOCIAL_ICON_BACKGROUND_DARK(Color.rgb(r = 50, g = 60, b = 100)),


        BLUE(Color.rgb(r = 33, g = 150, b = 243)),
        DARK_BLUE(Color.rgb(r = 34, g = 63, b = 94)),
        GoogleBlue(Color.rgb(r = 66, g = 103, b = 210)),
        JavaOrange(Color.rgb(r =255,g = 165 ,b = 0))
    }




    object Icon {
        const val EMAIL_LIGHT = "images/icons/mail.svg"
        const val EMAIL_DARK = "images/icons/mail_dark.svg"
        const val GITHUB = "images/icons/github.svg"
        const val GITHUB_LIGHT = "images/icons/github_light.svg"
        const val INSTAGRAM = "images/icons/instagram.svg"
        const val INSTAGRAM_LIGHT = "images/icons/instagram_light.svg"
        const val LINKEDIN = "images/icons/linkedin.svg"
        const val LINKEDIN_LIGHT = "images/icons/linkedin_light.svg"
        const val SUN = "images/icons/sun.svg"
        const val MOON = "images/icons/moon.svg"
        const val DEV = "images/icons/dev.svg"
        const val HEXAWEB = "images/icons/hexaweb.svg"
        const val CUBOID = "images/icons/cuboid.svg"

    }

    object Image {
        const val PROFILE_PHOTO_GREEN = "mypfpgreen.png"
        const val PROFILE_PHOTO_REGULAR = "mypfpregular.jpeg"


    }
    object Logo{
        const val ANDROID_LOGO = "images/tools/Android.png"
        const val GITHUB_LOGO = "images/tools/GitHub.svg"
        const val PYCHARM_LOGO = "images/tools/PyCharm.svg"
        const val UBUNTU_LOGO = "images/tools/Ubuntu.svg"
        const val CMP_LOGO = "images/skills/cmp.svg"
        const val FIGMA_LOGO = "images/tools/Figma.svg"
        const val FIREBASE_LOGO = "images/tools/Firebase.svg"
        const val GIT_LOGO = "images/tools/Git.svg"
        const val INTELLIJ_LOGO = "images/tools/IntelliJ.svg"
        const val VSCODE_LOGO = "images/tools/VSCode.svg"
        const val JAVA_LOGO = "images/skills/Java.svg"
        const val KOTLIN_LOGO = "images/skills/Kotlin.svg"
        const val PYTHON_LOGO = "images/skills/python.svg"
        const val MYSQL_LOGO = "images/skills/mysql.svg"
        const val CPP_LOGO = "images/skills/Cpp.svg"
        const val C_LOGO = "images/skills/C.svg"
        const val FLASK_LOGO = "images/skills/Flask.svg"
        const val DJANGO_LOGO = "images/skills/Django.svg"
        const val KOBWEB_LOGO = "images/tools/kobweb.png"
        const val PYTORCH_LOGO = "images/tools/PyTorch.svg"
    }

    object String {
        const val NAME = "Vikramaditya"
        const val MY_EMAIL = "mailto:vikramadityakhupse@gmail.com"
        const val RESUME_URL = "https://drive.google.com/file/d/1eif_tRGq_AAp6lrQIOIuiG4XDEb8nEms/view?usp=sharing"
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
        return ""; 
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
        const val ICON_SIZE = 24
        const val ICON_SIZE_LG = 32
    }


}

