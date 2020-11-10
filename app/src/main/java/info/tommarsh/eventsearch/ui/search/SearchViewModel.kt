package info.tommarsh.eventsearch.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.tommarsh.eventsearch.core.data.attractions.AttractionsRepository
import info.tommarsh.eventsearch.core.data.category.CategoryRepository
import info.tommarsh.eventsearch.fetch
import info.tommarsh.eventsearch.model.AttractionViewModel
import info.tommarsh.eventsearch.model.CategoryViewModel
import info.tommarsh.eventsearch.model.FetchState
import info.tommarsh.eventsearch.model.toViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SearchViewModel @ViewModelInject constructor(
    private val attractionsRepository: AttractionsRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _eventState =
        MutableStateFlow<FetchState<List<AttractionViewModel>>>(FetchState.Loading(true))
    internal val eventState: StateFlow<FetchState<List<AttractionViewModel>>> = _eventState

    private val _categoriesState =
        MutableStateFlow<FetchState<List<CategoryViewModel>>>(FetchState.Loading(true))
    internal val categoriesState: StateFlow<FetchState<List<CategoryViewModel>>> = _categoriesState

    init {
        getEvents()
        getCategories()
    }

    fun getEvents(query: String = "") = viewModelScope.launch(Dispatchers.IO) {
        _eventState.value = FetchState.Loading(false)
        _eventState.value = fetch { attractionsRepository.searchForAttractions(query).toViewModel() }
    }

    private fun getCategories() = viewModelScope.launch(Dispatchers.IO) {
        _categoriesState.value = fetch { categoryRepository.getCategories().toViewModel() }
    }
}