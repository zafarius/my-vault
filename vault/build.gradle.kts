/*
 * This file was generated by the Gradle 'init' task.
 */
project.version = project.findProperty("projVersion") ?: "0.1.0-SNAPSHOT"

plugins {
    id("buildlogic.java-application-conventions")
    alias(libs.plugins.lombok)
    alias(libs.plugins.spring.boot)
}

dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.h2.database)
    implementation(libs.flyway.core)
    implementation(libs.atomikos)
    implementation(project(":account:repository"))
    implementation(project(":security"))

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.junit.jupiter.mockito)
    testImplementation(libs.org.assertj.core)
}

application {
    // Define the main class for the application.
    mainClass = "vault.VaultApplication"
}