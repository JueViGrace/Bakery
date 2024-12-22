rootProject.name = "bakery"
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

include(":client:composeApp")
include(":server")
include(":lib")

// core client library
include(":lib:core:client:api")
include(":lib:core:client:database")
include(":lib:core:client:di")
include(":lib:core:client:presentation")
include(":lib:core:client:resources")
include(":lib:core:client:types")

// core server library
include(":lib:core:server:api")
include(":lib:core:server:database")
include(":lib:core:server:types")
include(":lib:core:server:util")
include(":lib:core:server:validation")

// core shared library
include(":lib:core:shared:di")
include(":lib:core:shared:types")

// auth library

//  auth client
include(":lib:auth:client")

// auth server
include(":lib:auth:server")

// user library

// user server
include(":lib:user:server")

// user client
include(":lib:user:client")

// product library

// product server
include(":lib:product:server")

// product client
include(":lib:product:client")

// order library

// order server
include(":lib:order:server")

// order client
include(":lib:order:client")
