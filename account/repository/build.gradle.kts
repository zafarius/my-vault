/*
 * This file was generated by the Gradle 'init' task.
 */
project.version = project.findProperty("projVersion") ?: "0.1.0-SNAPSHOT"

plugins {
    id("buildlogic.java-library-conventions")
    alias(libs.plugins.lombok)
}

dependencies {
    api(libs.spring.boot.starter.data.jpa)
    implementation(libs.jakarta.validation)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.spring.boot.starter.test)
}