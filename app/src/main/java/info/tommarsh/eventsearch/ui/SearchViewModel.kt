package info.tommarsh.eventsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import info.tommarsh.eventsearch.core.data.attractions.AttractionsRepository
import info.tommarsh.eventsearch.core.data.category.CategoryRepository
import info.tommarsh.eventsearch.core.data.likes.LikesRepository
import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel
import info.tommarsh.eventsearch.model.SearchAttractionViewModel
import info.tommarsh.eventsearch.model.toViewModel
import info.tommarsh.eventsearch.ui.model.SearchScreenAction
import info.tommarsh.eventsearch.ui.model.SearchScreenAction.*
import info.tommarsh.eventsearch.ui.model.SearchScreenEffect
import info.tommarsh.eventsearch.ui.model.SearchScreenEffect.ShowReminderDialog
import info.tommarsh.eventsearch.ui.model.SearchScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val attractionsRepository: AttractionsRepository,
    private val categoryRepository: CategoryRepository,
    private val likesRepository: LikesRepository
) : ViewModel() {

    private var query = ""
    private var attractions: Flow<PagingData<SearchAttractionViewModel>>? = null

    private val _screenState = MutableStateFlow(SearchScreenState())
    val screenState = _screenState.asStateFlow()

    private val _effects = MutableSharedFlow<SearchScreenEffect>()
    val effects = _effects.asSharedFlow()

    init {
        viewModelScope.launch {
            _screenState.value = screenState.value.copy(
                categories = categoryRepository.getCategories().toViewModel()
            )
        }

        viewModelScope.launch {
            likesRepository.getLikedAttractions().collectLatest { likedAttractions ->
                _screenState.value = screenState.value.copy(
                    likedAttractions = likedAttractions
                )
            }
        }
    }

    fun postAction(action: SearchScreenAction) {
        when (action) {
            is AttractionDeleted -> deleteLikedAttraction(action.model)
            is QueryEntered -> setQuery(action.query)
            is SetReminder -> sendShowDialogEffect(action.attraction)
            else -> throw NotImplementedError("Not handling action: $action")
        }
    }

    private fun sendShowDialogEffect(attraction: LikedAttractionModel) {
        viewModelScope.launch {
            _effects.emit(
                ShowReminderDialog(
                    id = attraction.id,
                    name = attraction.name,
                    image = attraction.imageUrl
                )
            )
        }
    }

    fun attractions(query: String = ""): Flow<PagingData<SearchAttractionViewModel>> {
        val currentAttractions = attractions
        if (query == this.query && currentAttractions != null) {
            return currentAttractions
        }

        this.query = query
        val newAttractions = attractionsRepository.getAttractionsForQuery(query)
            .map { page -> page.map { attraction -> attraction.toViewModel() } }
            .cachedIn(viewModelScope)

        attractions = newAttractions
        return newAttractions
    }

    private fun setQuery(query: String) {
        if (query != screenState.value.query) {
            _screenState.value = screenState.value.copy(query = query)
        }
    }

    private fun deleteLikedAttraction(model: LikedAttractionModel) {
        viewModelScope.launch(Dispatchers.IO) {
            likesRepository.removeLikedAttraction(model)
        }
    }
}