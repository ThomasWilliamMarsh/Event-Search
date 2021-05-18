package info.tommarsh.eventsearch.core.data.attractions

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import info.tommarsh.eventsearch.core.data.attractions.model.domain.AttractionModel
import info.tommarsh.eventsearch.core.data.attractions.model.toDomainModel
import info.tommarsh.eventsearch.core.data.attractions.remote.AttractionsAPI
import info.tommarsh.eventsearch.core.data.attractions.remote.AttractionsPagingSource
import info.tommarsh.eventsearch.core.data.attractions.remote.CategoryRequest
import info.tommarsh.eventsearch.core.data.attractions.remote.SearchRequest
import info.tommarsh.eventsearch.core.util.ScreenWidthResolver
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class AttractionsRepositoryImpl
@Inject constructor(
    private val api: AttractionsAPI,
    private val screenWidthResolver: ScreenWidthResolver,
    private val pagingConfig: PagingConfig
) : AttractionsRepository {

    override fun getAttractionsForQuery(query: String): Flow<PagingData<AttractionModel>> {

        return Pager(
            config = pagingConfig,
            initialKey = 0
        ) {
            AttractionsPagingSource(
                api = api,
                request = SearchRequest(query),
                screenWidthResolver = screenWidthResolver
            )
        }.flow
    }

    override fun getAttractionsForCategory(category: String): Flow<PagingData<AttractionModel>> {
        return Pager(
            config = pagingConfig,
            initialKey = 0
        ) {
            AttractionsPagingSource(
                api = api,
                request = CategoryRequest(category),
                screenWidthResolver = screenWidthResolver
            )
        }.flow
    }

    override suspend fun getAttractionsForGenre(genre: String): List<AttractionModel> {
        return api.getAttractionsForGenre(genre, 0).toDomainModel(screenWidthResolver)
    }

    override suspend fun getAttraction(id: String): AttractionModel {
        return api.attractionDetails(id).toDomainModel(screenWidthResolver)
    }
}