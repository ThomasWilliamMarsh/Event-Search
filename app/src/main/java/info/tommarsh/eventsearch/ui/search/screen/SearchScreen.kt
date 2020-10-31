package info.tommarsh.eventsearch.ui.search.screen

import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import info.tommarsh.eventsearch.EventSearchApp
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.model.*
import info.tommarsh.eventsearch.ui.common.CenteredCircularProgress
import info.tommarsh.eventsearch.ui.common.ErrorSnackbar
import info.tommarsh.eventsearch.ui.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SearchScreen(searchViewModel: SearchViewModel) {
    val events by searchViewModel.eventState.collectAsState()
    val categories by searchViewModel.categoriesState.collectAsState()

    SearchScreen(
        eventState = events,
        categoryState = categories,
        onSearch = searchViewModel::getEvents
    )
}

@Composable
fun SearchScreen(
    eventState: FetchState<List<EventViewModel>>,
    categoryState: FetchState<List<CategoryViewModel>>,
    onSearch: (query: String) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(topBar = { SearchToolbar(categoryState, onSearch) },
        scaffoldState = scaffoldState,
        bodyContent = {
            when (eventState) {
                is FetchState.Loading -> CenteredCircularProgress()
                is FetchState.Success -> SearchList(events = eventState.items)
                is FetchState.Failure -> ErrorSnackbar(
                    snackbarHostState = scaffoldState.snackbarHostState,
                    message = stringResource(id = R.string.error_loading_events)
                )
            }
        }
    )
}

@Composable
fun SearchList(events: List<EventViewModel>) {
    LazyColumnFor(events) { event ->
        SearchCard(event = event)
    }
}

@Preview(showBackground = true)
@Composable
fun EventScreenPreview() {
    EventSearchApp {
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
            onSearch = {}
        )
    }
}