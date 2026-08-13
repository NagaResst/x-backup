plugins {
    id("dev.kikugie.stonecutter")
    id("net.fabricmc.fabric-loom") version "1.17.19" apply false
    id("net.fabricmc.fabric-loom-remap") version "1.17.19" apply false

    kotlin("jvm") version "2.4.0" apply false
    kotlin("plugin.serialization") version "2.4.0" apply false
    id("com.gradleup.shadow") version "9.6.1" apply false
    id("me.modmuss50.mod-publish-plugin") version "2.2.0" apply false
    id("org.ajoberstar.grgit") version "5.3.3"
}
stonecutter active "1.21.5" /* [SC] DO NOT EDIT */

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            url = uri("https://maven.shedaniel.me/")
        }
        maven {
            url = uri("https://jitpack.io/")
        }
        maven {
            url = uri("https://masa.dy.fi/maven")
        }
    }

    the<BasePluginExtension>().archivesName.set(property("mod.id") as String + "-" + name)
}


// Build all versions
tasks.register("buildAllVersions") {
    group = "build"
    description = "Build all Minecraft versions"

    val versions = listOf(
        "1.21.1", "1.21.3", "1.21.4", "1.21.5",
        "1.21.6", "1.21.9", "1.21.11",
        "26.1.2",
    )

    versions.forEach { version ->
        dependsOn(":${version}:buildAndCollect")
    }
}

stonecutter parameters {
    swaps["mod_version"] = "\"${project.property("mod.version")}\""
    swaps["git_commit"] = "\"${grgit.head().abbreviatedId}\""
    swaps["commit_date"] = "\"${grgit.head().dateTime.toString().substringBefore("[")}\""
    constants["poly_lib"] = project.property("deps.poly_lib").toString().isNotEmpty()
}
