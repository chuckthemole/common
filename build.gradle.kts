import com.rumpushub.buildlogic.plugins.CommonPlugin
import com.rumpushub.buildlogic.plugins.CommonPublisherPlugin
import com.rumpushub.buildlogic.plugins.AwsDependenciesPlugin
import com.rumpushub.buildlogic.plugins.CommonDBDependenciesPlugin
import com.rumpushub.buildlogic.plugins.CommonSessionDependencies
import com.rumpushub.buildlogic.plugins.RumpusTest
import com.rumpushub.buildlogic.plugins.RumpusTestConventions
import com.rumpushub.buildlogic.plugins.RumpusDependenciesPlugin

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

apply<AwsDependenciesPlugin>()
configure<AwsDependenciesPlugin.AwsExtension> {
    awsCoreDependency = rumpusLibs.springCloudAws
    awsS3Dependency = rumpusLibs.springCloudAwsS3
}

apply<CommonDBDependenciesPlugin>()
configure<CommonDBDependenciesPlugin.DbExtension> {
    springJdbc = rumpusLibs.springJdbc
    springDataJpa = rumpusLibs.springDataJpa
    mysqlConnector = rumpusLibs.mysql
    redis = rumpusLibs.springDataRedis
    jedis = rumpusLibs.jedis
    jooq = rumpusLibs.jooq
}

apply<CommonSessionDependencies>()
configure<CommonSessionDependencies.SessionExtension> {
    core = rumpusLibs.springSessionCore
    jdbc = rumpusLibs.springSessionJdbc
}

apply<RumpusTest>()
configure<RumpusTest.TestExtension> {
    springBoot = rumpusLibs.springBootStarterTest
    mockito = rumpusLibs.mockito
    junitApi = rumpusLibs.junit
    junitEngine = rumpusLibs.junitEngine
    springSecurityTest = rumpusLibs.springSecurityTest
}

apply<RumpusTestConventions>()
configure<RumpusTestConventions.TestConventionsExtension> {
    junitVersion = rumpusLibs.junit4
    showStandardStreams = true
}

apply<RumpusDependenciesPlugin>()

configure<RumpusDependenciesPlugin.RumpusDepsExtension> {
    core.addAll(listOf(
        rumpusLibs.rumpusSpringBoot.get(),
        rumpusLibs.springBootWeb.get()
    ))

    web.addAll(listOf(
        rumpusLibs.webFlux.get(),
        rumpusLibs.webSocket.get()
    ))

    db.addAll(listOf(
        rumpusLibs.jpa.get(),
        rumpusLibs.jdbc.get(),
        rumpusLibs.mysql.get()
    ))

    security.addAll(listOf(
        rumpusLibs.springSecurity.get(),
        rumpusLibs.oauth2Client.get(),
        rumpusLibs.jjwtApi.get(),
        rumpusLibs.jjwtImpl.get(),
        rumpusLibs.jjwtJackson.get()
    ))

    cloud.addAll(listOf(
        rumpusLibs.springCloudAws.get(),
        rumpusLibs.springCloudAwsS3.get()
    ))

    devTools.addAll(listOf(
        rumpusLibs.devTools.get()
    ))

    testing.addAll(listOf(
        rumpusLibs.junit.get(),
        rumpusLibs.mockito.get()
    ))

    // 👇 Miscellaneous dependencies that don’t neatly fit in other buckets
    additionalDeps.addAll(listOf(
        rumpusLibs.springBootActuator.get(),
        rumpusLibs.springBootAdminClient.get(),
        rumpusLibs.springBootAdminServer.get(),
        rumpusLibs.commonsValidator.get(),
        rumpusLibs.bootstrap.get(),
        rumpusLibs.htmlunit.get(),
        rumpusLibs.unirest.get(),
        rumpusLibs.jsr305.get(),
        rumpusLibs.j2html.get(),
        rumpusLibs.jython.get(),
        rumpusLibs.tess4j.get(),
        rumpusLibs.oauth2ResourceServer.get()
    ))

}

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
