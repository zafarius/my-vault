/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    id("buildlogic.java-application-conventions")
    alias(libs.plugins.spring.boot)
}

dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.h2.database)
    implementation(libs.flyway.core)
    implementation(libs.atomikos)
    implementation(project(":account:repository"))
    implementation(project(":security"))

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.junit.jupiter)
}

application {
    // Define the main class for the application.
    mainClass = "vault.VaultApplication"
}