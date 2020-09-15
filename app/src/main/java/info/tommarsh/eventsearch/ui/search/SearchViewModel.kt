package info.tommarsh.eventsearch.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.tommarsh.eventsearch.core.data.category.CategoryRepository
import info.tommarsh.eventsearch.core.data.events.EventRepository
import info.tommarsh.eventsearch.ui.search.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SearchViewModel @ViewModelInject constructor(
    private val eventRepository: EventRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _eventState = MutableStateFlow<FetchState<EventViewModel>>(FetchState.Loading(true))
    val eventState: StateFlow<FetchState<EventViewModel>> = _eventState

    private val _categoriesState =
        MutableStateFlow<FetchState<CategoryViewModel>>(FetchState.Loading(true))
    val categoriesState: StateFlow<FetchState<CategoryViewModel>> = _categoriesState

    init {
        viewModelScope.launch(Dispatchers.IO) {

            _eventState.value = try {
                FetchState.Success(eventRepository.getEvents().toViewModel())
            } catch (throwable: Throwable) {
                FetchState.Failure(throwable)
            }

            _categoriesState.value = try {
                FetchState.Success(categoryRepository.getCategories().toViewModel())
            } catch (throwable: Throwable) {
                FetchState.Failure(throwable)
            }
        }
    }
}