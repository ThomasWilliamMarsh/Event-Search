package info.tommarsh.eventsearch.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
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
    private val attractionsRepository: AttractionsRepository
) : ViewModel() {

    fun attractions(category: String): Flow<PagingData<AttractionViewModel>> {
        return attractionsRepository.getAttractionsForCategory(category)
            .map { page -> page.map { attraction -> attraction.toViewModel() } }
            .cachedIn(viewModelScope)
    }
}