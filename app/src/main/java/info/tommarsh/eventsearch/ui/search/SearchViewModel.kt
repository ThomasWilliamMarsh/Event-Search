package info.tommarsh.eventsearch.ui.search

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.tommarsh.eventsearch.core.data.category.CategoryRepository
import info.tommarsh.eventsearch.core.data.events.EventRepository
import info.tommarsh.eventsearch.model.CategoryViewModel
import info.tommarsh.eventsearch.model.EventViewModel
import info.tommarsh.eventsearch.model.FetchState
import info.tommarsh.eventsearch.model.toViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SearchViewModel @ViewModelInject constructor(
    private val eventRepository: EventRepository,
    private val categoryRepository: CategoryRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _eventState =
        MutableStateFlow<FetchState<List<EventViewModel>>>(FetchState.Loading(true))
    val eventState: StateFlow<FetchState<List<EventViewModel>>> = _eventState

    private val _categoriesState =
        MutableStateFlow<FetchState<List<CategoryViewModel>>>(FetchState.Loading(true))
    val categoriesState: StateFlow<FetchState<List<CategoryViewModel>>> = _categoriesState

    init {
        getEvents()
        getCategories()
    }

    fun getEvents(query: String = "") = viewModelScope.launch(Dispatchers.IO) {
        _eventState.value = FetchState.Loading(false)
        try {
            _eventState.value =
                FetchState.Success(eventRepository.searchForEvents(query).toViewModel())
        } catch (throwable: Throwable) {
            _eventState.value = FetchState.Failure(throwable)
        }
    }

    private fun getCategories() = viewModelScope.launch(Dispatchers.IO) {
        _categoriesState.value = try {
            FetchState.Success(categoryRepository.getCategories().toViewModel())
        } catch (throwable: Throwable) {
            FetchState.Failure(throwable)
        }
    }
}