import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

// TODO: test and coverage aggregation
plugins {
    id("com.github.ben-manes.versions")
    id("org.owasp.dependencycheck")
}

tasks {
    withType<DependencyUpdatesTask> {
        rejectVersionIf {
            isUnstableVersion(candidate.version)
        }

        // optional parameters
        checkForGradleUpdate = true
        outputFormatter = "json"
        outputDir = "build/dependencyUpdates"
        reportfileName = "report"
    }
}

fun isUnstableVersion(version: String): Boolean {
    val unstableKeywords = listOf("alpha", "beta", "rc", "m")

    return unstableKeywords.any { keyword ->
        version.contains(keyword, ignoreCase = true)
    }
}
