plugins {
    id("kotlin-adapter-conventions")
}

val http4kVersion: String by project

dependencies {
    implementation(project(":booking-service:domain"))

    implementation(platform("org.http4k:http4k-bom:$http4kVersion"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-format-jackson")

    testFixturesApi(testFixtures(project(":booking-service:domain")))
    testFixturesApi(platform("org.http4k:http4k-bom:$http4kVersion"))
    testFixturesApi("org.http4k:http4k-core")
    testFixturesApi("org.http4k:http4k-format-jackson")
}
