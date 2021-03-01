package info.tommarsh.eventsearch.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val attractionsRepository: AttractionsRepository,
    private val categoryRepository: CategoryRepository,
    private val likesRepository: LikesRepository,
    private val pagingConfig: PagingConfig,
) : ViewModel() {

    private val _categoriesFlow =
        MutableStateFlow<FetchState<List<CategoryViewModel>>>(FetchState.Loading(true))
    val categoriesFlow: StateFlow<FetchState<List<CategoryViewModel>>> = _categoriesFlow

    val likedAttractionsFlow = likesRepository.getLikedAttractions()

    init {
        getCategories()
    }

    fun attractionsFlow(query: String = ""): Flow<PagingData<AttractionViewModel>> {
        return Pager(
            config = pagingConfig,
            initialKey = 0
        ) {
            attractionsRepository.getAttractionsForQuery(query)
        }.flow.map { page -> page.map { attraction -> attraction.toViewModel() } }
    }


    fun deleteLikedAttraction(model: LikedAttractionModel) {
        viewModelScope.launch(Dispatchers.IO) {
            likesRepository.removeLikedAttraction(model)
        }
    }

    private fun getCategories() = viewModelScope.launch(Dispatchers.IO) {
        _categoriesFlow.value = fetch { categoryRepository.getCategories().toViewModel() }
    }
}