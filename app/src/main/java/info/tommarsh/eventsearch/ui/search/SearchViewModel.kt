package info.tommarsh.eventsearch.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import info.tommarsh.eventsearch.core.data.attractions.AttractionsRepository
import info.tommarsh.eventsearch.core.data.category.CategoryRepository
import info.tommarsh.eventsearch.fetch
import info.tommarsh.eventsearch.model.AttractionViewModel
import info.tommarsh.eventsearch.model.CategoryViewModel
import info.tommarsh.eventsearch.model.FetchState
import info.tommarsh.eventsearch.model.toViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SearchViewModel @ViewModelInject constructor(
    private val attractionsRepository: AttractionsRepository,
    private val categoryRepository: CategoryRepository,
    private val pagingConfig: PagingConfig
) : ViewModel() {

    private val _categoriesState =
        MutableStateFlow<FetchState<List<CategoryViewModel>>>(FetchState.Loading(true))
    internal val categoriesState: StateFlow<FetchState<List<CategoryViewModel>>> = _categoriesState

    init {
        getCategories()
    }

    internal fun getEvents(query: String = ""): Flow<PagingData<AttractionViewModel>> {
        return Pager(
            config = pagingConfig,
            initialKey = 0
        ) {
            attractionsRepository.getAttractionsPagingSource(query)
        }.flow.map { page -> page.map { attraction -> attraction.toViewModel() } }
    }

    private fun getCategories() = viewModelScope.launch(Dispatchers.IO) {
        _categoriesState.value = fetch { categoryRepository.getCategories().toViewModel() }
    }
}