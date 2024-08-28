/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    id("buildlogic.java-library-conventions")
    alias(libs.plugins.lombok)
}

dependencies {
    api(libs.spring.boot.starter.security)
    implementation(libs.spring.session.jdbc)
    implementation(libs.spring.boot.starter.web)

    implementation(project(":account:repository"))

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.junit.jupiter.mockito)
    testImplementation("org.assertj:assertj-core:3.26.3")
}
