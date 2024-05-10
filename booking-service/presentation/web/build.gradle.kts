plugins {
    id("kotlin-adapter-conventions")
    application
}

dependencies {
    implementation(project(":booking-service:domain"))
    implementation(project(":booking-service:infrastructure:hotel-service"))
    implementation(project(":booking-service:infrastructure:booking-policy-service"))
    implementation(project(":core:web"))

    implementation(libs.bundles.http4k)
    implementation(libs.http4k.connect.redis)

    testImplementation(libs.bundles.http4k.testing)
    testFixturesApi(testFixtures(project(":core:domain")))
    testFixturesApi(testFixtures(project(":core:web")))
    testFixturesApi(testFixtures(project(":booking-service:domain")))
    testFixturesApi(testFixtures(project(":booking-service:infrastructure:hotel-service")))
    testFixturesApi(testFixtures(project(":booking-service:infrastructure:booking-policy-service")))
}

application {
    applicationName = "BookingService"
    mainClass.set("ServerKt")
}
