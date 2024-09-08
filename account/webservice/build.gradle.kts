/*
 * This file was generated by the Gradle 'init' task.
 */
project.version = project.findProperty("projVersion") ?: "0.1.0-SNAPSHOT"

plugins {
    id("buildlogic.java-library-conventions")
    alias(libs.plugins.openapi.generator)
    alias(libs.plugins.lombok)
}

dependencies {
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.jakarta.validation)
    implementation(libs.springdoc.openapi)
    implementation(libs.openapitools.jackson)
    implementation(libs.javax.annotation)
    implementation(libs.javax.validation)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.spring.boot.starter.test)
}

openApiMeta {
    generatorName = "account-webservice"
    packageName = "vault.webservice.account"
    outputFolder = "${layout.buildDirectory.get()}/meta"
}

openApiValidate {
    inputSpec = "${layout.projectDirectory}/src/main/resources/contracts/account-v1.0.yaml"
    recommend = true
}

openApiGenerate {
    generatorName = "spring"
    inputSpec = "${layout.projectDirectory}/src/main/resources/contracts/account-v1.0.yaml"
    outputDir = "${layout.buildDirectory.get()}/generate-resources/openapi"

    apiPackage = "vault.webservice.account"
    invokerPackage = "vault.webservice.account"
    modelPackage = "vault.account.model"

    configOptions.put("interfaceOnly", "true")
    configOptions.put("useTags", "true")
    configOptions.put("skipDefaultInterface", "true")
}
// Specify path to generated sources
sourceSets {
    main {
        java {
            srcDir( "${layout.buildDirectory.get()}/generate-resources/openapi")
        }
    }
}