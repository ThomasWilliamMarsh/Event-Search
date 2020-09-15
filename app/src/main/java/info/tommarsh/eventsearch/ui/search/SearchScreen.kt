package info.tommarsh.eventsearch.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import info.tommarsh.eventsearch.EventSearchApp
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.ui.search.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.reflect.KProperty

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SearchScreen() {
    val viewModel = viewModel<SearchViewModel>()
    val events by viewModel.eventState.collectAsState()
    val categories by viewModel.categoriesState.collectAsState()

    SearchScreen(events, categories)
}

@Composable
fun SearchScreen(eventState: FetchState<EventViewModel>,
                 categoryState: FetchState<CategoryViewModel>) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(topBar = { SearchToolbar(categoryState) },
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
        SearchCard(item = event)
    }
}

@Composable
fun CenteredCircularProgress() {
    Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            horizontalGravity = Alignment.CenterHorizontally,
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
fun DefaultPreview() {
    EventSearchApp {
        SearchScreen(
                eventState = FetchState.Success(items = listOf(
                        onSaleEvent,
                        comingSoonEvent,
                        presaleEvent
                )),
                categoryState = FetchState.Success(items = listOf(
                        musicCategory,
                        sportCategory,
                        artCategory,
                        familyCategory
                ))
        )
    }
}