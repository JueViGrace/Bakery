plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinx.serialization)
    application
}

group = "com.bakery.server"
version = libs.versions.server.version

application {
    mainClass.set("com.bakery.server.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    // Types: core
    implementation(projects.lib.core.shared.types)

    // Types: server
    implementation(projects.lib.core.server.types)

    // Util: server
    implementation(projects.lib.core.server.util)

    // Database: server
    implementation(projects.lib.core.server.database)

    // Api: server
    implementation(projects.lib.core.server.api)

    // Validation: server
    implementation(projects.lib.core.server.validation)

    // Server
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.thymeleaf)
    implementation(libs.ktor.server.html.builder)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.call.id)
    implementation(libs.ktor.server.metrics)
    implementation(libs.ktor.server.caching.headers)
    implementation(libs.ktor.server.default.headers)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.compression)
    implementation(libs.ktor.server.host.common)
    implementation(libs.ktor.server.status.pages)
//    implementation(libs.ktor.server.sse)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.webjars)
    implementation(libs.ktor.server.request.validation)
    implementation(libs.logback.classic)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Kotlin time
    implementation(libs.kotlinx.datetime)

    // Koin
    implementation(libs.koin.core)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger)

    // Logger
    implementation(libs.logback.classic)

    // dotenv
    implementation(libs.kdotenv)
}

ktor {
    docker {
        jreVersion.set(JavaVersion.VERSION_21)
    }

    fatJar {
        archiveFileName.set("fat.jar")
    }
}
