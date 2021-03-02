package info.tommarsh.eventsearch.core.data

import info.tommarsh.eventsearch.core.data.attractions.model.domain.AttractionDetailsModel

interface AttractionDetailsUseCase {
    suspend fun get(id: String): AttractionDetailsModel
}