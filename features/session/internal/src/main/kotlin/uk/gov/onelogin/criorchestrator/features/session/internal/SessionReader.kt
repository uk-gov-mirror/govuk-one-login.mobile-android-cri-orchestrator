package uk.gov.onelogin.criorchestrator.features.session.internal

import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session

fun interface SessionReader {
    suspend fun isActiveSession(): Result

    sealed interface Result {
        data class IsActive(val session: Session) : Result
        object IsNotActive : Result
        object Unknown : Result
    }
}