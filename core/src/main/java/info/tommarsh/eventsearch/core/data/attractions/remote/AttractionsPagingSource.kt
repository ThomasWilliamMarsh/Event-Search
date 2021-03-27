package info.tommarsh.eventsearch.core.data.attractions.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import info.tommarsh.eventsearch.core.data.attractions.model.data.AttractionsResponse
import info.tommarsh.eventsearch.core.data.attractions.model.domain.AttractionModel
import info.tommarsh.eventsearch.core.data.attractions.model.toDomainModel
import info.tommarsh.eventsearch.core.util.ScreenWidthResolver

class AttractionsPagingSource(
    private val api: AttractionsAPI,
    private val request: AttractionsRequest,
    private val screenWidthResolver: ScreenWidthResolver
) : PagingSource<Int, AttractionModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AttractionModel> {
        return try {
            val key = params.key ?: 0
            val response = request.fetch(api, key)
            val pageInfo = response.page
            val prevKey = if (pageInfo.number <= 0) null else pageInfo.number - 1
            val nextKey = if (pageInfo.number < pageInfo.totalPages) pageInfo.number + 1 else null

            LoadResult.Page(
                data = response.toDomainModel(screenWidthResolver),
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, AttractionModel>): Int {
        return 0
    }
}

interface AttractionsRequest {
    suspend fun fetch(api: AttractionsAPI, page: Int): AttractionsResponse
}

class SearchRequest(val query: String) : AttractionsRequest {
    override suspend fun fetch(api: AttractionsAPI, page: Int): AttractionsResponse {
        return api.searchForAttractions(query, page)
    }
}

class CategoryRequest(val category: String) : AttractionsRequest {
    override suspend fun fetch(api: AttractionsAPI, page: Int): AttractionsResponse {
        return api.getAttractionsForCategory(category, page)
    }
}