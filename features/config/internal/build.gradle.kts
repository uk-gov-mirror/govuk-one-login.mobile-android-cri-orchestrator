plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
}

dependencies {
    implementation(libs.kotlinx.collections.immutable)

    implementation(libs.kotlinx.coroutines)
    implementation(libs.uk.gov.logging.api)
    implementation(projects.features.config.publicApi)
    implementation(projects.libraries.di)
    testImplementation(projects.features.config.publicApi)

    testFixturesImplementation(libs.uk.gov.logging.testdouble)
    testFixturesImplementation(projects.features.config.publicApi)
    testFixturesImplementation(testFixtures(projects.features.config.publicApi))

    testImplementation(libs.uk.gov.logging.testdouble)
    testImplementation(testFixtures(projects.features.config.publicApi))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Config Internal",
        )
        description.set(
            """
            Internal implementations for configuring the SDK
            """.trimIndent(),
        )
    }
}
