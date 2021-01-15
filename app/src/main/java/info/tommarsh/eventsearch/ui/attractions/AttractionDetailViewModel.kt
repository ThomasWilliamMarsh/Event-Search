package info.tommarsh.eventsearch.ui.attractions

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.tommarsh.eventsearch.core.data.AttractionDetailsUseCase
import info.tommarsh.eventsearch.core.data.likes.LikesRepository
import info.tommarsh.eventsearch.domain.LikedAttractionModel
import info.tommarsh.eventsearch.fetch
import info.tommarsh.eventsearch.model.FetchState
import info.tommarsh.eventsearch.model.toViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
internal class AttractionDetailViewModel @ViewModelInject constructor(
    private val attractionDetailsUseCase: AttractionDetailsUseCase,
    private val likedRepository: LikesRepository
) : ViewModel() {

    fun likedStateFlowFor(id: String) = likedRepository.getLikedAttractions()
        .map { attractions ->
            attractions.find { liked -> liked.id == id } != null
        }

    fun fetchStateFlowFor(id: String) = flow {
        emit(fetch { attractionDetailsUseCase.get(id).toViewModel() })
    }.stateIn(viewModelScope, SharingStarted.Eagerly, FetchState.Loading(true))

    fun addLikedAttraction(attraction: LikedAttractionModel) = viewModelScope.launch {
        likedRepository.addLikedAttraction(attraction)
    }

    fun removeLikedAttraction(attraction: LikedAttractionModel) = viewModelScope.launch {
        likedRepository.removeLikedAttraction(attraction)
    }
}