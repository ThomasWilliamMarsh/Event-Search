package info.tommarsh.eventsearch.ui.event

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.tommarsh.eventsearch.core.data.events.EventRepository
import info.tommarsh.eventsearch.model.EventViewModel
import info.tommarsh.eventsearch.model.FetchState
import info.tommarsh.eventsearch.model.toViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import java.util.*

@ExperimentalCoroutinesApi
class EventDetailViewModel @ViewModelInject constructor(
    private val eventRepository: EventRepository) : ViewModel() {

    private val _detailState = MutableStateFlow<FetchState<EventViewModel>>(FetchState.Loading(true))
    val detailState: StateFlow<FetchState<EventViewModel>> = _detailState

    fun getEventDetails(event: String) = viewModelScope.launch(Dispatchers.IO) {
        _detailState.value = FetchState.Loading(true)
        try {
            val todaysDate = DateTime(Date().time)
            _detailState.value = FetchState.Success(eventRepository.eventDetails(event = event).toViewModel(todaysDate))
        } catch (throwable: Throwable) {
            _detailState.value = FetchState.Failure(throwable = throwable)
        }
    }
}