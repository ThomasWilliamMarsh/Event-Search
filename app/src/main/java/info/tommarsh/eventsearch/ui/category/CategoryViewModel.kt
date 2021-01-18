package info.tommarsh.eventsearch.ui.category

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import info.tommarsh.eventsearch.core.data.attractions.AttractionsRepository
import info.tommarsh.eventsearch.model.AttractionViewModel
import info.tommarsh.eventsearch.model.toViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
internal class CategoryViewModel @Inject constructor(
    private val attractionsRepository: AttractionsRepository,
    private val pagingConfig: PagingConfig
) : ViewModel() {

    fun getAttractions(category: String): Flow<PagingData<AttractionViewModel>> {
        return Pager(
            config = pagingConfig,
            initialKey = 0
        ) {
            attractionsRepository.getAttractionsForCategory(category)
        }.flow.map { page -> page.map { attraction -> attraction.toViewModel() } }
    }
}