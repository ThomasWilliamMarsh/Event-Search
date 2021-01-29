package info.tommarsh.eventsearch.core.data.attractions

import androidx.paging.PagingSource
import info.tommarsh.eventsearch.core.data.attractions.model.toDomainModel
import info.tommarsh.eventsearch.core.data.attractions.remote.AttractionsAPI
import info.tommarsh.eventsearch.core.data.attractions.remote.AttractionsPagingSource
import info.tommarsh.eventsearch.core.data.attractions.remote.CategoryRequest
import info.tommarsh.eventsearch.core.data.attractions.remote.SearchRequest
import info.tommarsh.eventsearch.core.util.ScreenWidthResolver
import info.tommarsh.eventsearch.core.data.attractions.model.domain.AttractionModel
import javax.inject.Inject

internal class AttractionsRepositoryImpl
@Inject constructor(
    private val api: AttractionsAPI,
    private val screenWidthResolver: ScreenWidthResolver
) : AttractionsRepository {

    override fun getAttractionsForQuery(query: String): PagingSource<Int, AttractionModel> {
        return AttractionsPagingSource(
            api = api,
            request = SearchRequest(query),
            screenWidthResolver = screenWidthResolver
        )
    }

    override fun getAttractionsForCategory(category: String): PagingSource<Int, AttractionModel> {
        return AttractionsPagingSource(
            api = api,
            request = CategoryRequest(category),
            screenWidthResolver = screenWidthResolver
        )
    }

    override suspend fun getAttraction(id: String): AttractionModel {
        return api.attractionDetails(id).toDomainModel(screenWidthResolver)
    }
}