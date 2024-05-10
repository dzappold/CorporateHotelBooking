plugins {
    id("kotlin-adapter-conventions")
    application
}

dependencies {
    implementation(project(":hotel-service:infrastructure:example-adapter"))

    implementation(libs.bundles.http4k)
    implementation(libs.http4k.connect.redis)

    testImplementation(libs.bundles.http4k.testing)
}

application {
    applicationName = "Web"
    mainClass.set("ServerKt")
}
