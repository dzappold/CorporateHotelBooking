plugins {
    id("kotlin-domain-conventions")
}

dependencies {
    api("dev.forkhandles:result4k")
    implementation(project(":core:domain"))
    testFixturesApi(testFixtures(project(":core:domain")))
}
