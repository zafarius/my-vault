/*
 * This file was generated by the Gradle 'init' task.
 */
project.version = project.findProperty("projVersion") ?: "0.2.0-SNAPSHOT"

plugins {
    id("buildlogic.java-library-conventions")
    alias(libs.plugins.lombok)
}

dependencies {
    implementation(project(":common:domain"))

    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.session.jdbc)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.junit.jupiter.mockito)
    testImplementation(libs.org.assertj.core)
}

//rename jar file
tasks.getByName<Jar>("jar") {
    archiveBaseName = "common-security"
}