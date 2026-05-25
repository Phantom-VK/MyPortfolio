import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import kotlinx.html.meta
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
}

group = "com.vikramaditya.portfolio"
version = "1.0-SNAPSHOT"

val visitNotifyApiBaseUrl = providers.gradleProperty("visitNotifyApiBaseUrl")
    .orElse(providers.environmentVariable("VISIT_NOTIFY_API_BASE_URL"))
    .orElse("")

kobweb {
    app {
        index {
            head.add {
                link(rel = "preconnect", href = "https://fonts.googleapis.com")
                link(rel = "preconnect", href = "https://fonts.gstatic.com") { attributes["crossorigin"] = "" }
                link(
                    href = "https://fonts.googleapis.com/css2?family=Roboto&display=swap",
                    rel = "stylesheet"
                )

                link(
                    href = "https://fonts.googleapis.com/css2?" +
                            "family=DM+Sans:ital,opsz,wght@0,9..40,100..1000;1,9..40,100..1000&" +
                            "family=JetBrains+Mono:ital,wght@0,100..800;1,100..800&" +
                            "family=Share+Tech+Mono&" +
                            "family=VT323&display=swap",
                    rel = "stylesheet"
                )

                visitNotifyApiBaseUrl.orNull
                    ?.trim()
                    ?.takeIf { it.isNotEmpty() }
                    ?.let { apiBaseUrl ->
                        meta(name = "visit-notify-api-base-url", content = apiBaseUrl)
                    }
            }
        }
    }
}

kotlin {
    configAsKobwebApplication("portfolio")
    js {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions.target = "es2015"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
        }

        jsMain {
            dependencies {
                implementation(libs.compose.html.core)
                implementation(libs.kobweb.core)
                implementation(libs.kobweb.silk)
                implementation(libs.silk.icons.fa)
                implementation(libs.kobwebx.markdown)
            }
        }
    }
}
