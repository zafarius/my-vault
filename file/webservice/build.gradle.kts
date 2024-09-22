/*
 * This file was generated by the Gradle 'init' task.
 */
project.version = project.findProperty("projVersion") ?: "0.1.0-SNAPSHOT"

plugins {
    id("buildlogic.java-library-conventions")
    alias(libs.plugins.lombok)
}

dependencies {
    implementation(project(":file:webservice-contracts"))
    implementation(project(":file:domain"))

    implementation(project(":common:domain"))
    implementation(project(":common:security"))

    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.jakarta.validation)
    implementation(libs.spring.web)
    implementation(libs.swagger.annotation)
    implementation(libs.jackson.databind)
    implementation(libs.mapstruct)
    annotationProcessor(libs.mapstruct.processor)
    compileOnly(libs.jakarta.servlet.api)

    testImplementation(project(":common:security-test"))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.spring.security.test)
    testImplementation(libs.spring.boot.starter.test)
}

//rename jar file
tasks.getByName<Jar>("jar") {
    archiveBaseName = "file-webservice"
}