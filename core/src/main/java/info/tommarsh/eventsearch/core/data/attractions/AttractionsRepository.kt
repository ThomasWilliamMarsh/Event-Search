package info.tommarsh.eventsearch.core.data.attractions

import androidx.paging.PagingSource
import info.tommarsh.eventsearch.domain.AttractionModel

interface AttractionsRepository {

    fun getAttractionsPagingSource(query: String) : PagingSource<Int, AttractionModel>

    suspend fun getAttraction(id: String) : AttractionModel
}