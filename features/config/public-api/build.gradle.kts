plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    api(libs.kotlinx.collections.immutable)

    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.uk.gov.logging.api)
    implementation(projects.libraries.di)

    testImplementation(libs.uk.gov.logging.testdouble)
    testImplementation(testFixtures(projects.features.config.publicApi))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Config Internal API",
        )
        description.set(
            """
            The Config Public API module contains the configuration store class
            and an interface for providing configuration to the configuration store.
            """.trimIndent(),
        )
    }
}
