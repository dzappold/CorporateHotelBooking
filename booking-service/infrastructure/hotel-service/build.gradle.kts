plugins {
    id("kotlin-adapter-conventions")
}

dependencies {
    implementation(project(":booking-service:domain"))

    implementation(libs.http4k.jackson)

    testFixturesApi(testFixtures(project(":booking-service:domain")))
    testFixturesApi(libs.http4k.jackson)
}
