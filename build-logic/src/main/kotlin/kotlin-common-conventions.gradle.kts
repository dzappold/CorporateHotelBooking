import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    java
    id("java-test-fixtures")
    jacoco

    kotlin("jvm")

    id("io.gitlab.arturbosch.detekt")
}

val jvmVersion: String by project

java.toolchain.languageVersion.set(JavaLanguageVersion.of(jvmVersion))
kotlin.jvmToolchain(jvmVersion.toInt())

val forkhandlesVersion: String by project
val junitVersion: String by project
val kotestVersion: String by project
val kotlinVersion: String by project
val mockkVersion: String by project

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:$kotlinVersion"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation(platform("dev.forkhandles:forkhandles-bom:$forkhandlesVersion"))
    implementation("dev.forkhandles:result4k")

    testFixturesApi(platform("dev.forkhandles:forkhandles-bom:$forkhandlesVersion"))
    testFixturesApi("dev.forkhandles:result4k")
    testFixturesApi("dev.forkhandles:result4k-kotest")

    testFixturesApi(platform("org.junit:junit-bom:$junitVersion"))
    testFixturesApi("org.junit.jupiter:junit-jupiter-api")
    testFixturesApi("org.junit.jupiter:junit-jupiter")
    testFixturesApi("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testFixturesApi("io.mockk:mockk-jvm:$mockkVersion") {
        exclude("junit", "junit")
    }

    testFixturesApi(platform("io.kotest:kotest-bom:$kotestVersion"))
    testFixturesApi("io.kotest:kotest-assertions-core")
    testFixturesApi("io.kotest:kotest-assertions-json")
}

tasks {
    withType<Copy>().configureEach {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    withType<Jar>().configureEach {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    withType<Test>().configureEach {
        useJUnitPlatform()
        testLogging {
            events(PASSED, SKIPPED, FAILED)
        }
        finalizedBy(jacocoTestReport) // report is always generated after tests run
    }

    withType<JacocoReport>().configureEach {
        dependsOn(test) // tests are required to run before generating the report
    }

    withType<JacocoCoverageVerification>().configureEach {
        violationRules {
            rule {
                enabled = true

                limit {
                    counter = "LINE"
                    value = "TOTALCOUNT"
                    minimum = "1.0".toBigDecimal()
                }
            }
        }
    }

    withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            freeCompilerArgs.set(listOf("-Xjsr305=strict"))
            languageVersion.set(KOTLIN_2_0)
        }
    }

    withType<Detekt>().configureEach {
        jvmTarget = java.toolchain.languageVersion.get().toString()
    }
}

detekt {
    ignoreFailures = true
}
