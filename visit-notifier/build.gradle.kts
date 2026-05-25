import org.gradle.api.file.DuplicatesStrategy

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

tasks.jar {
    archiveClassifier.set("all")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }

    from({
        configurations.runtimeClasspath.get()
            .filter { it.exists() }
            .map { if (it.isDirectory) it else zipTree(it) }
    })
}
