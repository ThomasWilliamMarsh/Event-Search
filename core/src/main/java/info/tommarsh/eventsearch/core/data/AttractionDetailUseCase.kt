package info.tommarsh.eventsearch.core.data

import info.tommarsh.eventsearch.core.data.attractions.model.domain.AttractionDetailModel

interface AttractionDetailUseCase {
    suspend fun get(id: String): AttractionDetailModel
}