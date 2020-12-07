package info.tommarsh.eventsearch.ui.attractions

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.tommarsh.eventsearch.core.data.AttractionDetailsUseCase
import info.tommarsh.eventsearch.core.data.likes.LikesRepository
import info.tommarsh.eventsearch.domain.LikedAttractionModel
import info.tommarsh.eventsearch.fetch
import info.tommarsh.eventsearch.model.AttractionDetailsViewModel
import info.tommarsh.eventsearch.model.FetchState
import info.tommarsh.eventsearch.model.toViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class AttractionDetailViewModel @ViewModelInject constructor(
    private val attractionDetailsUseCase: AttractionDetailsUseCase,
    private val likedRepository: LikesRepository
) : ViewModel() {

    private val _detailState =
        MutableStateFlow<FetchState<AttractionDetailsViewModel>>(FetchState.Loading(true))
    internal val detailState: StateFlow<FetchState<AttractionDetailsViewModel>> = _detailState

    private val _likedState =
        MutableStateFlow(false)

    val likedState: StateFlow<Boolean> = _likedState

    fun getAttractionDetails(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _detailState.value = FetchState.Loading(true)
        _detailState.value =
            fetch { attractionDetailsUseCase.get(id).toViewModel() }
    }

    fun getLikedAttractionState(id: String) = viewModelScope.launch {
        Log.v("Likes", "getting attractions")
        likedRepository.getLikedAttractions()
            .map { attractions ->
                Log.v("Likes", "items: ${attractions.joinToString()}")
                attractions.contains(LikedAttractionModel(id))
            }.collectLatest {
                Log.v("Likes", "Liked now? $it")
                _likedState.value = it
            }
    }

    fun addLikedAttraction(id: String) = viewModelScope.launch {
        likedRepository.addLikedAttraction(id)
    }

    fun removeLikedAttraction(id: String) = viewModelScope.launch {
        likedRepository.removeLikedAttraction(id)
    }
}