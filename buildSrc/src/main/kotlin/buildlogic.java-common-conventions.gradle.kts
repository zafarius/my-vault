/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    // Apply the java Plugin to add support for Java.
    java
    checkstyle
}

checkstyle {
    toolVersion = "10.18.0"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
   // maven(uri("https://mvnrepository.com"))
}


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()

    testLogging {
        events("passed")
    }
}
