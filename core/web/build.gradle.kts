plugins {
    id("kotlin-adapter-conventions")
    application
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.bundles.http4k)
    implementation("com.fasterxml.jackson.module:jackson-module-blackbird:2.17.2")
    implementation("com.fasterxml.jackson.module:jackson-module-afterburner:2.17.2")

    testFixturesApi(libs.bundles.http4k.testing)
}
