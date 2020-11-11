package info.tommarsh.eventsearch.core.data.attractions

import info.tommarsh.eventsearch.domain.AttractionModel

interface AttractionsRepository {

    suspend fun searchForAttractions(query: String) : List<AttractionModel>

    suspend fun getAttraction(id: String) : AttractionModel
}