package info.tommarsh.eventsearch.attraction.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.tommarsh.eventsearch.attraction.ui.model.AttractionDetailScreenAction
import info.tommarsh.eventsearch.attraction.ui.model.AttractionDetailScreenAction.ClickLiked
import info.tommarsh.eventsearch.attraction.ui.model.AttractionDetailScreenState
import info.tommarsh.eventsearch.attraction.ui.model.toViewModel
import info.tommarsh.eventsearch.core.data.AttractionDetailUseCase
import info.tommarsh.eventsearch.core.data.likes.LikesRepository
import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel
import info.tommarsh.eventsearch.core.fetch
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
internal class AttractionDetailViewModel @Inject constructor(
    private val attractionDetailUseCase: AttractionDetailUseCase,
    private val likedRepository: LikesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _screenState = MutableStateFlow(AttractionDetailScreenState())

    val screenState = _screenState.asStateFlow()

    init {
        val id = savedStateHandle.get<String>("id")!!

        viewModelScope.launch {
            combine(
                fetch { attractionDetailUseCase.get(id).toViewModel() },
                likedRepository.getAttractionLiked(id)
            ) { fetchState, isLiked ->
                AttractionDetailScreenState(
                    fetchState = fetchState,
                    isLiked = isLiked
                )
            }.collectLatest { state ->
                _screenState.value = state
            }
        }
    }

    fun postAction(action: AttractionDetailScreenAction) {
        when (action) {
            is ClickLiked -> toggleLiked(action.attraction)
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