plugins {
    id("kotlin-adapter-conventions")
}

dependencies {
    implementation(project(":booking-service:domain"))
    implementation(project(":core:web"))

    implementation(libs.http4k.format.jackson)

    testFixturesApi(project(":core:web"))
    testFixturesApi(testFixtures(project(":booking-service:domain")))
    testFixturesApi(libs.http4k.format.jackson)
}
