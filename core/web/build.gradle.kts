plugins {
    id("kotlin-adapter-conventions")
    application
}

val http4kConnectVersion: String by project
val http4kVersion: String by project

dependencies {
    implementation(project(":core:domain"))

    implementation(platform("org.http4k:http4k-bom:$http4kVersion"))

    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-cloudevents")
    implementation("org.http4k:http4k-cloudnative")
    implementation("org.http4k:http4k-contract")
    implementation("org.http4k:http4k-failsafe")
    implementation("org.http4k:http4k-client-okhttp")
    implementation("org.http4k:http4k-format-jackson")

    testFixturesApi(platform("org.http4k:http4k-bom:$http4kVersion"))
    testFixturesApi("org.http4k:http4k-testing-chaos")
    testFixturesApi("org.http4k:http4k-testing-kotest")
    testFixturesApi("org.http4k:http4k-testing-approval")
    testFixturesApi("org.http4k:http4k-testing-servirtium")
    testFixturesApi("org.http4k:http4k-testing-tracerbullet")
}
