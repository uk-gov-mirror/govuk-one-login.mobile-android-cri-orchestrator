package uk.gov.onelogin.criorchestrator.features.resume.internal.root

import androidx.lifecycle.SavedStateHandle
import org.mockito.Mockito.mock
import uk.gov.onelogin.criorchestrator.features.resume.internal.analytics.ResumeAnalytics
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeIsSessionResumable
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.IsSessionResumable
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.RefreshActiveSession

fun ProveYourIdentityViewModel.Companion.createTestInstance(
    analytics: ResumeAnalytics = mock(),
    isSessionResumable: IsSessionResumable = FakeIsSessionResumable(),
    refreshActiveSession: RefreshActiveSession = mock(),
    savedStateHandle: SavedStateHandle = SavedStateHandle(),
) = ProveYourIdentityViewModel(
    analytics = analytics,
    isSessionResumable = isSessionResumable,
    refreshActiveSession = refreshActiveSession,
    savedStateHandle = savedStateHandle,
)
