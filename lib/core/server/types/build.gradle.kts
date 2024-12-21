plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinx.serialization)
}

group = "com.bakery.core.types"

dependencies {
    // Types: core
    implementation(projects.lib.core.shared.types)

    // Database: server
    implementation(projects.lib.core.server.database)

    // Ktor
    implementation(libs.ktor.server.core)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Kotlin Datetime
    implementation(libs.kotlinx.datetime)

    // Kotlin Reflect
    implementation(libs.kotlin.reflect)
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
