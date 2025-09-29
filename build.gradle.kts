import com.rumpushub.buildlogic.plugins.CommonPlugin
import com.rumpushub.buildlogic.plugins.CommonPublisherPlugin

import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.gradle.jvm.tasks.Jar

// --------------------------------------------------------------------------
// common/build.gradle.kts
// --------------------------------------------------------------------------
// This build file configures the 'common' module, which contains shared
// utilities, data models, and helper classes used across Rumpus subprojects.
//
// Key responsibilities:
// - Provides reusable code for multiple Rumpus modules.
// - Configures compilation, dependencies, and artifact publishing.
// - Ensures consistent plugin versions and Gradle conventions.
// --------------------------------------------------------------------------

apply<CommonPlugin>()             // Applies project-specific conventions for 'common' module
apply<CommonPublisherPlugin>()    // Configures publishing logic for the module

// --------------------------------------------------------------------------
// Plugins
// --------------------------------------------------------------------------
// External plugins are applied via the version catalog for centralized
// version management:
// - Spring Boot: provides BootJar tasks, auto-configuration support
// - Dependency Management: allows consistent dependency versions across modules
plugins {
    alias(rumpusLibs.plugins.springBoot)
    alias(rumpusLibs.plugins.dependencyManagement)
}

// --------------------------------------------------------------------------
// Project Metadata
// --------------------------------------------------------------------------
// Group and version are sourced from the centralized version catalog,
// enabling consistent artifact coordinates across all projects.
group = rumpusLibs.versions.commonGroup.get()
version = rumpusLibs.versions.common.get()

// --------------------------------------------------------------------------
// Dependencies
// --------------------------------------------------------------------------
// JUnit dependency is included for unit testing.
// All versions are managed centrally in `libs.versions.toml`.
dependencies {
    add("implementation", rumpusLibs.junit)
}

// --------------------------------------------------------------------------
// Jar / BootJar Configuration
// --------------------------------------------------------------------------
// - Spring Boot BootJar is disabled because this module produces a plain JAR.
// - Standard Jar task is enabled to produce artifacts suitable for publishing
//   or consumption by other modules.
tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}

// --------------------------------------------------------------------------
// Notes / Best Practices
// --------------------------------------------------------------------------
// 1. Keep version catalog entries up to date; avoid hardcoding versions here.
// 2. Ensure all shared code is backward compatible with modules consuming this artifact.
// 3. Tests should be placed in src/test/java or src/test/kotlin using the JUnit dependency.
// 4. Kotlin DSL ensures type-safe access to Gradle APIs and improves IDE support.
// 5. Use `CommonPlugin` and `CommonPublisherPlugin` to enforce project-wide conventions.
// --------------------------------------------------------------------------
