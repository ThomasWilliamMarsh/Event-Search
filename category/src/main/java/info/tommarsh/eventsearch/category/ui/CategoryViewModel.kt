package info.tommarsh.eventsearch.category.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import info.tommarsh.eventsearch.category.ui.model.CategoryAttractionViewModel
import info.tommarsh.eventsearch.category.ui.model.toViewModel
import info.tommarsh.eventsearch.core.data.attractions.AttractionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
internal class CategoryViewModel @Inject constructor(
    private val attractionsRepository: AttractionsRepository
) : ViewModel() {

    fun attractions(category: String): Flow<PagingData<CategoryAttractionViewModel>> {
        return attractionsRepository.getAttractionsForCategory(category)
            .map { page -> page.map { attraction -> attraction.toViewModel() } }
            .cachedIn(viewModelScope)
    }
}