pluginManagement {
    repositories {
        // Official repositories first for Fabric dependencies
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.kikugie.dev/releases/")
        maven {
            name = "Kikugie Snapshot"
            url = uri("https://maven.kikugie.dev/snapshots")
        }
        // Chinese mirrors as fallback
        maven("https://maven.aliyun.com/repository/central") {
            name = "Aliyun Central"
            content {
                // Only use Aliyun for common libraries, not Fabric-specific
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("com\\.alibaba.*")
                includeGroupByRegex("org\\.apache.*")
                includeGroupByRegex("org\\.jetbrains.*")
            }
        }
        maven("https://maven.aliyun.com/repository/public") {
            name = "Aliyun Public"
            content {
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("org\\.apache.*")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.7.7-beta.2"
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "X Backup"
include("common")

stonecutter {
    kotlinController = true
    centralScript = "build.gradle.kts"

    shared {
        versions(
            "1.21.1",
            "1.21.3",
            "1.21.4",
            "1.21.5",
            "1.21.6",
            "1.21.9",
            "1.21.11",
        )
    }
    create(rootProject)
}

include("compat-fake-source")
include("cli")
include("api")
