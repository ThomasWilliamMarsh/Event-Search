package info.tommarsh.eventsearch.ui.search.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import info.tommarsh.eventsearch.EventSearchApp
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.model.*
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
    eventState: FetchState<EventViewModel>,
    categoryState: FetchState<CategoryViewModel>,
    onSearch: (query: String) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(topBar = { SearchToolbar(categoryState, onSearch) },
        scaffoldState = scaffoldState,
        bodyContent = {
            when (eventState) {
                is FetchState.Loading -> CenteredCircularProgress()
                is FetchState.Success -> SearchList(events = eventState.items)
                is FetchState.Failure -> ErrorSnackbar(scaffoldState.snackbarHostState)
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

@Composable
fun CenteredCircularProgress() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) { CircularProgressIndicator() }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ErrorSnackbar(snackbarHostState: SnackbarHostState) {
    val message = stringResource(id = R.string.error_loading_events)
    launchInComposition {
        snackbarHostState.showSnackbar(message = message)
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