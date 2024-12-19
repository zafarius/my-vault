/*
 * This file was generated by the Gradle 'init' task.
 */
project.version = project.findProperty("projVersion") ?: "0.1.0"

plugins {
    id("buildlogic.java-library-conventions")
    alias(libs.plugins.lombok)
}

dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.security)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.spring.boot.starter.test)
}

//rename jar file
tasks.getByName<Jar>("jar") {
    archiveBaseName = "common-domain"
}