package info.tommarsh.eventsearch.attraction.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.tommarsh.eventsearch.attraction.ui.model.AttractionDetailScreenAction
import info.tommarsh.eventsearch.attraction.ui.model.AttractionDetailScreenAction.ClickLiked
import info.tommarsh.eventsearch.attraction.ui.model.AttractionDetailScreenAction.FetchDetails
import info.tommarsh.eventsearch.attraction.ui.model.AttractionDetailScreenState
import info.tommarsh.eventsearch.attraction.ui.model.toViewModel
import info.tommarsh.eventsearch.core.data.AttractionDetailsUseCase
import info.tommarsh.eventsearch.core.data.likes.LikesRepository
import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel
import info.tommarsh.eventsearch.fetch
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
internal class AttractionDetailViewModel @Inject constructor(
    private val attractionDetailsUseCase: AttractionDetailsUseCase,
    private val likedRepository: LikesRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow(AttractionDetailScreenState())

    val screenState = _screenState.asStateFlow()

    fun postAction(action: AttractionDetailScreenAction) {
        when (action) {
            is FetchDetails -> fetchDetails(action.id)
            is ClickLiked -> toggleLiked(action.attraction)
        }
    }

    private fun fetchDetails(id: String) {

        viewModelScope.launch {
            likedRepository.getAttractionLiked(id)
                .collectLatest { _screenState.emit(screenState.value.copy(isLiked = it)) }
        }

        viewModelScope.launch {
            _screenState.value = screenState.value.copy(fetchState = fetch {
                attractionDetailsUseCase.get(id).toViewModel()
            })
        }
    }

    private fun toggleLiked(attraction: LikedAttractionModel) {
        viewModelScope.launch {
            if (screenState.value.isLiked) {
                likedRepository.removeLikedAttraction(attraction)
            } else {
                likedRepository.addLikedAttraction(attraction)
            }
        }
    }
}