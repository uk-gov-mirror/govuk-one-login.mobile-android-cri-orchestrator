package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

data class Session(
    val sessionId: String,
    val redirectUri: String? = null,
    val state: String,
    val resumable: Boolean = true,
    val aborted: Boolean = false,
) {
    init {
        require(!(resumable && aborted))
    }
    companion object;
}
