/*
 * This file was generated by the Gradle 'init' task.
 */
project.version = project.findProperty("projVersion") ?: "0.1.0"

plugins {
    id("buildlogic.java-library-conventions")
    alias(libs.plugins.lombok)
}

dependencies {
    implementation(project(":common:domain"))
    implementation(libs.spring.security.test)
    implementation(libs.spring.boot.starter.security)
}

//rename jar file
tasks.getByName<Jar>("jar") {
    archiveBaseName = "common-security-test"
}