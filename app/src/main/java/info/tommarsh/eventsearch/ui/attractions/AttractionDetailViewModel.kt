package info.tommarsh.eventsearch.ui.attractions

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.tommarsh.eventsearch.core.data.attractions.AttractionsRepository
import info.tommarsh.eventsearch.fetch
import info.tommarsh.eventsearch.model.AttractionViewModel
import info.tommarsh.eventsearch.model.FetchState
import info.tommarsh.eventsearch.model.toViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class AttractionDetailViewModel @ViewModelInject constructor(
    private val attractionsRepository: AttractionsRepository
) : ViewModel() {

    private val _detailState =
        MutableStateFlow<FetchState<AttractionViewModel>>(FetchState.Loading(true))
    internal val detailState: StateFlow<FetchState<AttractionViewModel>> = _detailState

    fun getEventDetails(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _detailState.value = FetchState.Loading(true)
        _detailState.value =
            fetch { attractionsRepository.attractionDetails(id = id).toViewModel() }
    }
}