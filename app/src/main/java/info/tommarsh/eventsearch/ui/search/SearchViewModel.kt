package info.tommarsh.eventsearch.ui.search

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
import info.tommarsh.eventsearch.fetch
import info.tommarsh.eventsearch.model.AttractionViewModel
import info.tommarsh.eventsearch.model.CategoryViewModel
import info.tommarsh.eventsearch.model.FetchState
import info.tommarsh.eventsearch.model.toViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val attractionsRepository: AttractionsRepository,
    private val categoryRepository: CategoryRepository,
    private val likesRepository: LikesRepository
) : ViewModel() {

    private var query: String? = null

    private var attractions: Flow<PagingData<AttractionViewModel>>? = null

    private val _categories =
        MutableStateFlow<FetchState<List<CategoryViewModel>>>(FetchState.Loading(true))
    val categories = _categories.asStateFlow()

    val likedAttractions = likesRepository.getLikedAttractions()

    init {
        getCategories()
    }

    fun attractions(query: String = ""): Flow<PagingData<AttractionViewModel>> {
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

    fun deleteLikedAttraction(model: LikedAttractionModel) {
        viewModelScope.launch(Dispatchers.IO) {
            likesRepository.removeLikedAttraction(model)
        }
    }

    private fun getCategories() = viewModelScope.launch(Dispatchers.IO) {
        _categories.value = fetch { categoryRepository.getCategories().toViewModel() }
    }
}