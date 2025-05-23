rootProject.name = "init_project"

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include("model")
findProject(":model")?.name = "model"

include("app")
findProject(":app")?.name = "app"

include("common")
findProject(":common")?.name = "common"