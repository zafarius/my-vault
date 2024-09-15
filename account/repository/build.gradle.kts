/*
 * This file was generated by the Gradle 'init' task.
 */
project.version = project.findProperty("projVersion") ?: "0.1.0-SNAPSHOT"

plugins {
    id("buildlogic.java-library-conventions")
    alias(libs.plugins.lombok)
}

dependencies {
    implementation(project(":account:domain"))
    implementation(project(":common:domain"))

    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.jakarta.validation)
    implementation(libs.flyway.core)
    implementation(libs.mapstruct)
    annotationProcessor(libs.mapstruct.processor)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.h2.database)
}

//rename jar file
tasks.getByName<Jar>("jar") {
    archiveBaseName = "account-repository"
}