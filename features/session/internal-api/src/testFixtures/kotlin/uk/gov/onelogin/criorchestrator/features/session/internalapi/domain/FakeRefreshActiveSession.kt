package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class FakeRefreshActiveSession(
    var willHaveActiveSession: Boolean = true,
    private val fakeIsSessionResumable: FakeIsSessionResumable? = null,
): RefreshActiveSession {
    override suspend fun invoke() {
        delay(1.seconds)
        fakeIsSessionResumable?.value?.value = willHaveActiveSession
    }
}