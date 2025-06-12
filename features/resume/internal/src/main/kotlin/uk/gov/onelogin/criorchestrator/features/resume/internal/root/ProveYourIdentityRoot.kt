package uk.gov.onelogin.criorchestrator.features.resume.internal.root

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.launch
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.resume.internal.card.ProveYourIdentityUiCard
import uk.gov.onelogin.criorchestrator.features.resume.internal.modal.ProveYourIdentityModal
import uk.gov.onelogin.criorchestrator.features.resume.internal.modal.ProveYourIdentityModalNavHost
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider

@Composable
internal fun ProveYourIdentityRoot(
    viewModel: ProveYourIdentityViewModel,
    navGraphProviders: ImmutableSet<ProveYourIdentityNavGraphProvider>,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    val navController = rememberNavController()

    val navigateToModal = {
        navController
            .navigate(DESTINATION_MODAL) {
                this.launchSingleTop = true
            }
    }

    LaunchedEffect(Unit) {
        launch {
            viewModel.actions
                .onSubscription {
                    // Ensure we've started collecting actions before starting
                    viewModel.onScreenStart()
                }.collect {
                    when (it) {
                        ProveYourIdentityRootUiAction.AllowModalToShow -> {
                            navigateToModal()
                        }
                    }
                }
        }
    }

    val onCardStartClick = {
        viewModel.onStartClick()
        navigateToModal()
    }

    ProveYourIdentityRootNavHost(
        state = state,
        navController = navController,
        onCardStartClick = onCardStartClick,
        onModalCancelClick = viewModel::onModalCancelClick,
        modifier = modifier,
        modalContent = {
            ProveYourIdentityModalNavHost(
                navGraphProviders = navGraphProviders,
                onFinish = { navController.popBackStack() },
            )
        },
    )
}

private const val DESTINATION_CARD = "/card"
private const val DESTINATION_MODAL = "/modal"

@Suppress("LongParameterList")
@Composable
internal fun ProveYourIdentityRootNavHost(
    state: ProveYourIdentityRootUiState,
    navController: NavHostController,
    onCardStartClick: () -> Unit,
    modifier: Modifier = Modifier,
    onModalCancelClick: () -> Unit = {},
    // Suppress naming rule for clarity
    @SuppressLint("ComposableLambdaParameterNaming")
    modalContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = DESTINATION_CARD
    ) {
        composable(DESTINATION_CARD) {
            if (state.showCard) {
                ProveYourIdentityUiCard(
                    onStartClick = onCardStartClick,
                    modifier =
                        modifier
                            .testTag(ProveYourIdentityRootTestTags.CARD),
                )
            }
        }

        dialog(
            DESTINATION_MODAL,
            dialogProperties = DialogProperties(usePlatformDefaultWidth = false),
        ) {
            ProveYourIdentityModal(
                onCancelClick = onModalCancelClick,
                onDismissRequest = { navController.popBackStack() },
                modifier = Modifier.testTag(ProveYourIdentityRootTestTags.MODAL),
            ) {
                modalContent()
            }
        }
    }

}

internal data class PreviewParams(
    val state: ProveYourIdentityRootUiState,
)

@Suppress("MaxLineLength") // Conflict between Ktlint formatting and Detekt rule
internal class ProveYourIdentityRootNavHostPreviewParameterProvider : PreviewParameterProvider<PreviewParams> {
    override val values =
        sequenceOf(
            PreviewParams(
                state = ProveYourIdentityRootUiState(showCard = true),
            ),
            PreviewParams(
                state = ProveYourIdentityRootUiState(showCard = false),
            ),
        )
}

@Composable
@PreviewLightDark
internal fun ProveYourIdentityRootContentPreview(
    @PreviewParameter(ProveYourIdentityRootNavHostPreviewParameterProvider::class)
    parameters: PreviewParams,
) {
    GdsTheme {
        ProveYourIdentityRootNavHost(
            state = parameters.state,
            onCardStartClick = {},
            navController = rememberNavController(),
            modalContent = {
                Text("Prove your identity modal is open")
            },
        )
    }
}

internal object ProveYourIdentityRootTestTags {
    internal const val MODAL = "ProveIdentityRootModalTestTag"
    internal const val CARD = "ProveIdentityRootCardTestTag"
}
