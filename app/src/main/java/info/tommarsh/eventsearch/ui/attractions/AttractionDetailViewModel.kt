package info.tommarsh.eventsearch.ui.attractions

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.tommarsh.eventsearch.core.data.AttractionDetailsUseCase
import info.tommarsh.eventsearch.fetch
import info.tommarsh.eventsearch.model.AttractionDetailsViewModel
import info.tommarsh.eventsearch.model.FetchState
import info.tommarsh.eventsearch.model.toViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class AttractionDetailViewModel @ViewModelInject constructor(
    private val attractionDetailsUseCase: AttractionDetailsUseCase
) : ViewModel() {

    private val _detailState =
        MutableStateFlow<FetchState<AttractionDetailsViewModel>>(FetchState.Loading(true))
    internal val detailState: StateFlow<FetchState<AttractionDetailsViewModel>> = _detailState

    fun getAttractionDetails(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _detailState.value = FetchState.Loading(true)
        _detailState.value =
            fetch { attractionDetailsUseCase.get(id).toViewModel() }
    }
}