package info.tommarsh.eventsearch.ui.attractions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.tommarsh.eventsearch.core.data.AttractionDetailsUseCase
import info.tommarsh.eventsearch.core.data.likes.LikesRepository
import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel
import info.tommarsh.eventsearch.fetch
import info.tommarsh.eventsearch.model.toViewModel
import info.tommarsh.eventsearch.ui.attractions.model.AttractionDetailAction
import info.tommarsh.eventsearch.ui.attractions.model.AttractionDetailState
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

    private val _screenState = MutableStateFlow(AttractionDetailState())

    val screenState = _screenState.asStateFlow()

    fun postAction(action: AttractionDetailAction) {
        when (action) {
            is AttractionDetailAction.FetchDetails -> fetchDetails(action.id)
            is AttractionDetailAction.ClickLiked -> toggleLiked(action.attraction)
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