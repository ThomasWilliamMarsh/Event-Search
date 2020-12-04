package info.tommarsh.eventsearch.core.data.attractions.remote

import androidx.paging.PagingSource
import info.tommarsh.eventsearch.core.data.attractions.model.toDomainModel
import info.tommarsh.eventsearch.core.util.ScreenWidthResolver
import info.tommarsh.eventsearch.domain.AttractionModel
import java.io.IOException

class AttractionsPagingSource(
    private val api: AttractionsAPI,
    private val query: String,
    private val screenWidthResolver: ScreenWidthResolver
) : PagingSource<Int, AttractionModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AttractionModel> {
        return try {
            val key = params.key ?: 0
            val response = api.searchForAttractions(query, key)
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
}