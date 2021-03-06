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
import info.tommarsh.eventsearch.ui.model.SearchScreenAction.AttractionDeleted
import info.tommarsh.eventsearch.ui.model.SearchScreenAction.QueryEntered
import info.tommarsh.eventsearch.ui.model.SearchScreenState
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

    init {

        viewModelScope.launch {
            combine(
                categoryRepository.getCategories(),
                likesRepository.getLikedAttractions()
            ) { categories, likedAttractions ->
                screenState.value.copy(
                    categories = categories.toViewModel(),
                    likedAttractions = likedAttractions
                )
            }.collectLatest { state ->
                _screenState.value = state
            }
        }
    }

    fun postAction(action: SearchScreenAction) {
        when (action) {
            is AttractionDeleted -> deleteLikedAttraction(action.model)
            is QueryEntered -> setQuery(action.query)
            else -> throw NotImplementedError("Not handling action: $action")
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

    private fun deleteLikedAttraction(model: LikedAttractionModel) = viewModelScope.launch {
        likesRepository.removeLikedAttraction(model)
    }
}