import com.rumpushub.buildlogic.plugins.CommonPlugin
import com.rumpushub.buildlogic.plugins.CommonPublisherPlugin

import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.gradle.jvm.tasks.Jar

// --------------------------------------------------------------------------
// common/build.gradle.kts
// --------------------------------------------------------------------------
// This build file configures the 'common' module, which contains shared
// code and utilities for the Rumpus project.
// --------------------------------------------------------------------------

apply<CommonPlugin>()
apply<CommonPublisherPlugin>()

plugins {
    // Spring Boot plugin (3.x)
    id("org.springframework.boot") version "3.0.1"

    // Dependency management plugin for centralized version control
    id("io.spring.dependency-management") version "1.1.0"
}

// --------------------------------------------------------------------------
// Project Metadata
// --------------------------------------------------------------------------
group = "com.rumpushub.common"
version = "1.1.0"

// --------------------------------------------------------------------------
// Dependencies
// --------------------------------------------------------------------------
dependencies {
    // Include JUnit 5 API for testing shared classes
    add("implementation", "org.junit.jupiter:junit-jupiter-api:5.8.1")
}

// --------------------------------------------------------------------------
// Jar / BootJar Configuration
// --------------------------------------------------------------------------
// Disable Spring Boot fat jar; only build a standard JAR for publishing
tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}

// --------------------------------------------------------------------------
// Notes
// --------------------------------------------------------------------------
// 1. Keep plugin versions in sync with project requirements.
// 2. This module is designed to produce a standard JAR artifact for reuse
//    across other modules or published artifacts.
// 3. Tests should reside under src/test/java or src/test/kotlin and use
//    the included JUnit dependency.
// 4. Using Kotlin DSL ensures type-safe Gradle configuration and better
//    IDE support for builds.
// --------------------------------------------------------------------------
