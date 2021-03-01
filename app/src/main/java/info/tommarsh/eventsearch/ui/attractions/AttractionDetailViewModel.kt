package info.tommarsh.eventsearch.ui.attractions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.tommarsh.eventsearch.core.data.AttractionDetailsUseCase
import info.tommarsh.eventsearch.core.data.likes.LikesRepository
import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel
import info.tommarsh.eventsearch.fetch
import info.tommarsh.eventsearch.model.FetchState
import info.tommarsh.eventsearch.model.toViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
internal class AttractionDetailViewModel @Inject constructor(
    private val attractionDetailsUseCase: AttractionDetailsUseCase,
    private val likedRepository: LikesRepository
) : ViewModel() {

    fun likedFlow(id: String) = likedRepository.getLikedAttractions()
        .map { attractions ->
            attractions.find { liked -> liked.id == id } != null
        }

    fun attractionFlow(id: String) = flow {
        emit(fetch { attractionDetailsUseCase.get(id).toViewModel() })
    }.stateIn(viewModelScope, SharingStarted.Eagerly, FetchState.Loading(true))

    fun addLikedAttraction(attraction: LikedAttractionModel) = viewModelScope.launch {
        likedRepository.addLikedAttraction(attraction)
    }

    fun removeLikedAttraction(attraction: LikedAttractionModel) = viewModelScope.launch {
        likedRepository.removeLikedAttraction(attraction)
    }
}