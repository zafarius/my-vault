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
    implementation(project(":account:repository"))
    implementation(project(":account:webservice-contracts"))
    implementation(project(":account:webservice"))
    implementation(project(":account:domain"))
    implementation(project(":account:security"))
    implementation(project(":security"))

    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.h2.database)
    implementation(libs.atomikos)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.junit.jupiter.mockito)
    testImplementation(libs.org.assertj.core)
}

application {
    // Define the main class for the application.
    mainClass = "vault.VaultApplication"
}

//disable plain jar
tasks.getByName<Jar>("jar") {
    enabled = false
}