rootProject.name = "bakeryApp"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

include(":composeApp")
include(":lib")

// core client library
include(":lib:core:api")
include(":lib:core:database")
include(":lib:core:di")
include(":lib:core:presentation")
include(":lib:core:resources")
include(":lib:core:types")

// auth library

//  auth client
include(":lib:auth")

// user library

// user client
include(":lib:user")

// product library

// product client
include(":lib:product")

// order library

// order client
include(":lib:order")
