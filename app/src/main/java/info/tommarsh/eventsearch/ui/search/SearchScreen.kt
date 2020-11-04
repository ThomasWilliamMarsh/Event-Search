package info.tommarsh.eventsearch.ui.search

import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.model.*
import info.tommarsh.eventsearch.theme.EventHomeTheme
import info.tommarsh.eventsearch.ui.common.CenteredCircularProgress
import info.tommarsh.eventsearch.ui.common.ErrorSnackbar
import info.tommarsh.eventsearch.ui.search.screen.SearchCard
import info.tommarsh.eventsearch.ui.search.screen.SearchToolbar
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    navigateToEvent: (id: String) -> Unit,
    navigateToCategory: (id: String, name: String) -> Unit
) = EventHomeTheme {
    val events by viewModel.eventState.collectAsState()
    val categories by viewModel.categoriesState.collectAsState()

    SearchScreen(
        eventState = events,
        categoryState = categories,
        onSearch = viewModel::getEvents,
        navigateToEvent = navigateToEvent,
        navigateToCategory = navigateToCategory
    )
}

@Composable
fun SearchScreen(
    eventState: FetchState<List<EventViewModel>>,
    categoryState: FetchState<List<CategoryViewModel>>,
    onSearch: (query: String) -> Unit,
    navigateToCategory: (id: String, name: String) -> Unit,
    navigateToEvent: (id: String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(topBar = { SearchToolbar(categoryState, onSearch, navigateToCategory) },
        scaffoldState = scaffoldState,
        bodyContent = {
            when (eventState) {
                is FetchState.Loading -> CenteredCircularProgress()
                is FetchState.Success -> SearchList(
                    events = eventState.items,
                    navigateToEvent = navigateToEvent
                )
                is FetchState.Failure -> ErrorSnackbar(
                    snackbarHostState = scaffoldState.snackbarHostState,
                    message = stringResource(id = R.string.error_loading_events)
                )
            }
        }
    )
}

@Composable
fun SearchList(
    events: List<EventViewModel>,
    navigateToEvent: (id: String) -> Unit
) {
    LazyColumnFor(events) { event ->
        SearchCard(event = event, navigateToEvent = navigateToEvent)
    }
}

@Preview(showBackground = true)
@Composable
fun EventScreenPreview() {
    EventHomeTheme {
        SearchScreen(
            eventState = FetchState.Success(
                items = listOf(
                    onSaleEvent,
                    comingSoonEvent,
                    presaleEvent
                )
            ),
            categoryState = FetchState.Success(
                items = listOf(
                    musicCategory,
                    sportCategory,
                    artCategory,
                    familyCategory
                )
            ),
            onSearch = {},
            navigateToCategory = { _, _ -> },
            navigateToEvent = {}
        )
    }
}