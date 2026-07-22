import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.script
import kotlinx.html.style
import kotlinx.html.unsafe
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

val siteUrl = "https://phantom-vk.github.io/MyPortfolio/"
val siteDescription = "Vikramaditya Khupse — IT Engineer specializing in AI/ML, GenAI, Agentic AI, " +
        "and full-stack development. Explore projects, experience, and skills."
val ogImageUrl = siteUrl + "og-image.png"

kobweb {
    app {
        index {
            head.add {
                meta(name = "description", content = siteDescription)
                meta(name = "author", content = "Vikramaditya Khupse")
                meta(name = "theme-color", content = "#171717")
                link(rel = "canonical", href = siteUrl)

                meta(content = "Vikramaditya Khupse — Portfolio") { attributes["property"] = "og:title" }
                meta(content = siteDescription) { attributes["property"] = "og:description" }
                meta(content = "website") { attributes["property"] = "og:type" }
                meta(content = siteUrl) { attributes["property"] = "og:url" }
                meta(content = ogImageUrl) { attributes["property"] = "og:image" }
                meta(content = "1200") { attributes["property"] = "og:image:width" }
                meta(content = "630") { attributes["property"] = "og:image:height" }

                meta(name = "twitter:card", content = "summary_large_image")
                meta(name = "twitter:title", content = "Vikramaditya Khupse — Portfolio")
                meta(name = "twitter:description", content = siteDescription)
                meta(name = "twitter:image", content = ogImageUrl)

                script(type = "application/ld+json") {
                    unsafe {
                        raw(
                            """
                            {
                              "@context": "https://schema.org",
                              "@type": "Person",
                              "name": "Vikramaditya Khupse",
                              "jobTitle": "IT Engineer",
                              "url": "$siteUrl",
                              "sameAs": [
                                "https://github.com/Phantom-VK",
                                "https://www.linkedin.com/in/vikramaditya-khupse-04838a259"
                              ]
                            }
                            """.trimIndent()
                        )
                    }
                }

                style {
                    unsafe {
                        raw(
                            """
                            :focus-visible {
                              outline: 2px solid #00ff41;
                              outline-offset: 2px;
                            }
                            @media (prefers-reduced-motion: reduce) {
                              *, *::before, *::after {
                                animation-duration: 0.001ms !important;
                                animation-iteration-count: 1 !important;
                                transition-duration: 0.001ms !important;
                                scroll-behavior: auto !important;
                              }
                            }
                            """.trimIndent()
                        )
                    }
                }

                link(rel = "preconnect", href = "https://fonts.googleapis.com")
                link(rel = "preconnect", href = "https://fonts.gstatic.com") { attributes["crossorigin"] = "" }

                link(
                    href = "https://fonts.googleapis.com/css2?" +
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
