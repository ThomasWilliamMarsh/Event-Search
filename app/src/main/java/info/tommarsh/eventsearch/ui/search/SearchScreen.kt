package info.tommarsh.eventsearch.ui.search

import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import artCategory
import attraction
import familyCategory
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.model.AttractionViewModel
import info.tommarsh.eventsearch.model.CategoryViewModel
import info.tommarsh.eventsearch.model.FetchState
import info.tommarsh.eventsearch.theme.EventHomeTheme
import info.tommarsh.eventsearch.ui.common.ErrorSnackbar
import info.tommarsh.eventsearch.ui.common.WithFetchState
import info.tommarsh.eventsearch.ui.search.screen.SearchCard
import info.tommarsh.eventsearch.ui.search.screen.SearchToolbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import musicCategory
import sportCategory

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel,
    navigateToEvent: (id: String) -> Unit,
    navigateToCategory: (id: String, name: String) -> Unit
) = EventHomeTheme {
    val events by viewModel.eventState.collectAsState()
    val categories by viewModel.categoriesState.collectAsState()

    SearchScreen(
        attractionState = events,
        categoryState = categories,
        onSearch = viewModel::getEvents,
        navigateToAttraction = navigateToEvent,
        navigateToCategory = navigateToCategory
    )
}

@Composable
internal fun SearchScreen(
    attractionState: FetchState<List<AttractionViewModel>>,
    categoryState: FetchState<List<CategoryViewModel>>,
    onSearch: (query: String) -> Unit,
    navigateToCategory: (id: String, name: String) -> Unit,
    navigateToAttraction: (id: String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        topBar = { SearchToolbar(categoryState, onSearch, navigateToCategory) },
        scaffoldState = scaffoldState,
        bodyContent = {
            WithFetchState(
                state = attractionState,
                onFailure = {
                    ErrorSnackbar(
                        snackbarHostState = scaffoldState.snackbarHostState,
                        message = stringResource(id = R.string.error_loading_events)
                    )
                },
                onSuccess = { data ->
                    SearchList(
                        attractions = data,
                        navigateToAttraction = navigateToAttraction
                    )
                })
        }
    )
}

@Composable
internal fun SearchList(
    attractions: List<AttractionViewModel>,
    navigateToAttraction: (id: String) -> Unit
) {
    LazyColumnFor(attractions) { attraction ->
        SearchCard(attraction = attraction, navigateToAttraction = navigateToAttraction)
    }
}

@Preview(showBackground = true)
@Composable
fun EventScreenPreview() {
    EventHomeTheme {
        SearchScreen(
            attractionState = FetchState.Success(
                data = listOf(
                    attraction
                )
            ),
            categoryState = FetchState.Success(
                data = listOf(
                    musicCategory,
                    sportCategory,
                    artCategory,
                    familyCategory
                )
            ),
            onSearch = {},
            navigateToCategory = { _, _ -> },
            navigateToAttraction = {}
        )
    }
}