plugins {
    id("kotlin-adapter-conventions")
    application
}

dependencies {
    implementation(project(":company-service:infrastructure:example-adapter"))

    implementation(libs.bundles.http4k)
    implementation(libs.http4k.connect.redis)

    testImplementation(libs.bundles.http4k.testing)
}

application {
    applicationName = "CompanyService"
    mainClass.set("ServerKt")
}
