plugins {
    id("kotlin-adapter-conventions")
    application
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.bundles.http4k)

    testFixturesApi(libs.bundles.http4k.testing)
}
