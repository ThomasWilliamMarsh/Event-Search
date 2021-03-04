package info.tommarsh.eventsearch.core.data.attractions

import androidx.paging.PagingData
import info.tommarsh.eventsearch.core.data.attractions.model.domain.AttractionModel
import kotlinx.coroutines.flow.Flow

interface AttractionsRepository {

    fun getAttractionsForQuery(query: String): Flow<PagingData<AttractionModel>>

    fun getAttractionsForCategory(category: String): Flow<PagingData<AttractionModel>>

    suspend fun getAttraction(id: String): AttractionModel
}