plugins {
    id("kotlin-domain-conventions")
}

dependencies {
    api(libs.result4k)
    implementation(project(":core:domain"))
    testFixturesApi(testFixtures(project(":core:domain")))
}
