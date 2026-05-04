import java.util.jar.Attributes

plugins {
    id("io.github.goooler.shadow") version "8.1.7"
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.guardsquare:proguard-gradle:7.6.0")
    }
}

apply(plugin = "java")

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 21
}

kotlin {
    jvmToolchain(21)
}

@Suppress("PackageUpdate")
dependencies {
    fun DependencyHandler.shadowLib(dependency: Any) =
        shadow(api(dependency)!!)!!

    shadowLib(project(":common"))
}

tasks {
    shadowJar {
        from("LICENSE")

        configurations = listOf(
            project.configurations.shadow.get()
        )
        archiveClassifier.set("all")
        manifest {
            attributes["Main-Class"] = "Main"
            attributes[Attributes.Name.IMPLEMENTATION_VERSION.toString()] = rootProject.property("mod.version")
            attributes[Attributes.Name.IMPLEMENTATION_TITLE.toString()] = rootProject.property("mod.name")
        }
    }
}
