package info.tommarsh.eventsearch.core.data.attractions

import info.tommarsh.eventsearch.domain.AttractionModel

interface AttractionsRepository {

    suspend fun getAttractions(): List<AttractionModel>

    suspend fun searchForAttractions(query: String) : List<AttractionModel>

    suspend fun attractionDetails(id: String) : AttractionModel
}