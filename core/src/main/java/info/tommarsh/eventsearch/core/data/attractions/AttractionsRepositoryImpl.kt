package info.tommarsh.eventsearch.core.data.attractions

import info.tommarsh.eventsearch.core.data.attractions.model.toDomainModel
import info.tommarsh.eventsearch.core.data.attractions.remote.AttractionsAPI
import info.tommarsh.eventsearch.core.util.ScreenWidthResolver
import info.tommarsh.eventsearch.domain.AttractionModel
import javax.inject.Inject

class AttractionsRepositoryImpl
@Inject constructor(
    private val api: AttractionsAPI,
    private val screenWidthResolver: ScreenWidthResolver
) : AttractionsRepository {

    override suspend fun getAttractions(): List<AttractionModel> {
        return api.getAttractions().toDomainModel(screenWidthResolver)
    }

    override suspend fun searchForAttractions(query: String): List<AttractionModel> {
        return api.searchForAttractions(query).toDomainModel(screenWidthResolver)
    }

    override suspend fun attractionDetails(event: String): AttractionModel {
        return api.attractionDetails(event).toDomainModel(screenWidthResolver)
    }
}