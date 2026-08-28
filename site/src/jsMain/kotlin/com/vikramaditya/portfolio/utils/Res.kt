package com.vikramaditya.portfolio.utils



object Res {


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
        const val PROFILE_PHOTO_GREEN = "mypfpgreen.webp"
        const val PROFILE_PHOTO_REGULAR = "mypfpregular.webp"


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
        const val LANGGRAPH_LOGO = "images/tools/langgraph-color.svg"
        const val CHATGPT_LOGO = "images/icons/chatgpt.png"
        const val POSTGRESQL_LOGO = "images/tools/postgre.png"
        const val REACT_LOGO = "images/skills/React.svg"
        const val TYPESCRIPT_LOGO = "images/skills/TypeScript.svg"
        const val FASTAPI_LOGO = "images/tools/FastAPI.svg"
        const val VITE_LOGO = "images/tools/Vite.svg"
        const val DEEPSEEK_LOGO = "images/tools/DeepSeek.svg"
    }

    object String {
        const val NAME = "Vikramaditya"
        const val MY_EMAIL = "mailto:vikramadityakhupse@gmail.com"
        const val RESUME_URL = "https://drive.google.com/drive/folders/1izi_woduWACltXpALTIlfUNDmbEKJurJ?usp=sharing"
        const val PYTHON_CODE = """class Profile:
    def __init__(self):
        self.name = "Vikramaditya Khupse"
        self.focus = ["Full Stack", "AI/ML", "DevOps & Cloud"]
        self.primary_stack = ["Python", "Transformers", "LangGraph", "FastAPI"]
        self.secondary = ["Java", "Kotlin", "Compose", "Docker", "GitHub Actions", "AWS"]

    def ship(self):
        return "Build ML-heavy products, automate pipelines, deliver to cloud."

me = Profile()
print(me.ship())"""
        const val JAVA_CODE = """public record Experience(
    String role,
    String company,
    String period,
    String[] highlights
) {
    public static Experience current() {
        return new Experience(
            "Associate Software Engineer (Intern)",
            "Better Software",
            "Oct 2025 – Present",
            new String[]{
                "CI/CD with GitHub Actions + Docker",
                "Multi-cloud releases across AWS & DigitalOcean",
                "Reliable, monitored deployments"
            }
        );
    }
}"""
        const val KOBWEB_CODE = """
data class Project(val name: String, val impact: String)

val projects = listOf(
    Project("HR Chacha", "80% automated candidate screening with LLMs"),
    Project("Phishing Detection", "97% accuracy, CI/CD to AWS"),
    Project("Vyom Assist", "₹1L hackathon win, AI customer support")
)

fun headline() = "Full-stack + AI/ML builder shipping to production."
"""



    }



}
