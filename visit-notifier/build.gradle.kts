plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "com.vikramaditya.portfolio"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(17)
}

application {
    mainClass = "com.vikramaditya.portfolio.visitnotifier.MainKt"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.jakarta.mail)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit.jupiter)
}

tasks.test {
    useJUnitPlatform()
}
