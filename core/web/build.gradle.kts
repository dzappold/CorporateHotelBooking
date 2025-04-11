plugins {
    id("kotlin-adapter-conventions")
    application
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.bundles.http4k)
    implementation("com.fasterxml.jackson.module:jackson-module-blackbird:2.18.3")
    implementation("com.fasterxml.jackson.module:jackson-module-afterburner:2.18.3")

    testFixturesApi(libs.bundles.http4k.testing)
}
