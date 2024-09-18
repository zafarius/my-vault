/*
 * This file was generated by the Gradle 'init' task.
 */
project.version = project.findProperty("projVersion") ?: "0.1.0-SNAPSHOT"

plugins {
    id("buildlogic.java-library-conventions")
    alias(libs.plugins.lombok)
}

dependencies {
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.jakarta.validation)
    implementation(libs.flyway.core)
}

//rename jar file
tasks.getByName<Jar>("jar") {
    archiveBaseName = "common-repository-data-model"
}