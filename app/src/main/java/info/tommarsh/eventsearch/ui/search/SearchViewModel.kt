package info.tommarsh.eventsearch.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.tommarsh.eventsearch.core.data.EventRepository
import info.tommarsh.eventsearch.ui.search.model.EventFetchState
import info.tommarsh.eventsearch.ui.search.model.toViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SearchViewModel @ViewModelInject constructor(
    private val repository: EventRepository
) : ViewModel() {

    private val _fetchState = MutableStateFlow<EventFetchState>(EventFetchState.Loading)
    val fetchState: StateFlow<EventFetchState> = _fetchState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _fetchState.value = try {
                EventFetchState.Success(repository.getEvents().toViewModel())
            } catch (throwable: Throwable) {
                EventFetchState.Failure(throwable)
            }
        }
    }
}