/*
 * This file was generated by the Gradle 'init' task.
 */
project.version = project.findProperty("projVersion") ?: "0.2.0-SNAPSHOT"

plugins {
    id("buildlogic.java-library-conventions")
    alias(libs.plugins.lombok)
}

dependencies {
    implementation(project(":account:domain"))
    implementation(project(":common:security"))
    implementation(project(":common:domain"))


    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.jakarta.validation)
    implementation(libs.mapstruct)
    implementation(libs.spring.boot.starter.security)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.spring.boot.starter.test)

    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.session.jdbc)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)
}

//rename jar file
tasks.getByName<Jar>("jar") {
    archiveBaseName = "account-security-auth"
}