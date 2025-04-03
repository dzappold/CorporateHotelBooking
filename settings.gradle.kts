import org.gradle.api.initialization.resolve.RepositoriesMode.FAIL_ON_PROJECT_REPOS

rootProject.name = "Corporate Hotel Booking Kata"

includeBuild("build-logic")

"core".apply {
    includeModule("domain")
    includeModule("web")
}

"booking-policy-service".apply {
    includeModule("domain")
    includeModule("presentation:web")
    includeModule("presentation:acceptance-test")
    includeModule("infrastructure:example-adapter")
}

"booking-service".apply {
    includeModule("domain")
    includeModule("presentation:web")
    includeModule("presentation:acceptance-test")
    includeModule("infrastructure:hotel-service")
    includeModule("infrastructure:booking-policy-service")
}

"company-service".apply {
    includeModule("domain")
    includeModule("presentation:web")
    includeModule("presentation:acceptance-test")
    includeModule("infrastructure:example-adapter")
}

"hotel-service".apply {
    includeModule("domain")
    includeModule("presentation:web")
    includeModule("presentation:acceptance-test")
    includeModule("infrastructure:example-adapter")
}

"e2e".apply {
    includeModule("domain")
    includeModule("presentation:web")
    includeModule("presentation:acceptance-test")
}

fun String.includeModule(name: String) {
    val projectName = "$this:$name"
    include(":$projectName")
    project(":$projectName").projectDir = File("$this/${name.replace(':', '/')}")
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }

    val kotlinVersion: String by settings
    val benManesVersion: String by settings
    val detektVersion: String by settings
    val dependencyCheckVersion: String by settings
    val foojayVersion: String by settings

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
                "com.github.ben-manes.versions" -> useVersion(benManesVersion)
                "io.gitlab.arturbosch.detekt" -> useVersion(detektVersion)
                "org.owasp.dependencycheck" -> useVersion(dependencyCheckVersion)
                "org.gradle.toolchains.foojay-resolver-convention"->useVersion(foojayVersion)
            }
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention")
}

dependencyResolutionManagement {
    repositoriesMode.set(FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
    }
}
