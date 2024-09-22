/*
 * This file was generated by the Gradle 'init' task.
 *
 * The settings file is used to specify which projects to include in your build.
 * For more detailed information on multi-project builds, please refer to https://docs.gradle.org/8.9/userguide/multi_project_builds.html in the Gradle documentation.
 */

plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "my-vault"
include(
    "vault",

    //common
    "common:security",
    "common:security-test",
    "common:webservice",
    "common:repository-data-model",
    "common:domain",

    //account domain
    "account:security-auth",
    "account:domain",
    "account:repository",
    "account:webservice",
    "account:webservice-contracts",

    //file domain
    "file:domain",
    "file:repository",
    "file:webservice",
    "file:webservice-contracts"
)
