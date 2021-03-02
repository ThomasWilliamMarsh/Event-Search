package info.tommarsh.eventsearch.core.data.attractions

import androidx.paging.PagingSource
import info.tommarsh.eventsearch.core.data.attractions.model.domain.AttractionModel

interface AttractionsRepository {

    fun getAttractionsForQuery(query: String): PagingSource<Int, AttractionModel>

    fun getAttractionsForCategory(category: String): PagingSource<Int, AttractionModel>

    suspend fun getAttraction(id: String): AttractionModel
}