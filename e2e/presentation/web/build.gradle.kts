plugins {
    id("kotlin-adapter-conventions")
    application
}

dependencies {
    implementation(project(":e2e:domain"))

    implementation(libs.bundles.http4k)
    implementation(libs.http4k.connect.redis)

    testImplementation(libs.bundles.http4k.testing)
}

application {
    applicationName = "Web"
    mainClass.set("ServerKt")
}
