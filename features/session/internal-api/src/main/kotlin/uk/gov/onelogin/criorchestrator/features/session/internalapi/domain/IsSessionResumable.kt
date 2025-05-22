package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

import kotlinx.coroutines.flow.Flow

fun interface IsSessionResumable {
    operator fun invoke(): Flow<Boolean>
}
