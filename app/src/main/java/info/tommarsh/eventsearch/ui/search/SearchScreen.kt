package info.tommarsh.eventsearch.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.domain.LikedAttractionModel
import info.tommarsh.eventsearch.model.AttractionViewModel
import info.tommarsh.eventsearch.model.CategoryViewModel
import info.tommarsh.eventsearch.model.FetchState
import info.tommarsh.eventsearch.theme.EventHomeTheme
import info.tommarsh.eventsearch.ui.common.BorderButton
import info.tommarsh.eventsearch.ui.common.CenteredCircularProgress
import info.tommarsh.eventsearch.ui.common.ErrorSnackbar
import info.tommarsh.eventsearch.ui.search.component.SearchCard
import info.tommarsh.eventsearch.ui.search.component.SearchToolbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
internal fun SearchScreen(
    attractionsFlow: Flow<PagingData<AttractionViewModel>>,
    categoriesFlow: Flow<FetchState<List<CategoryViewModel>>>,
    likedItemsFlow: Flow<List<LikedAttractionModel>>,
    navigateToEvent: (id: String) -> Unit,
    navigateToCategory: (id: String, name: String) -> Unit,
    setCurrentQuery: (query: String) -> Unit
) = EventHomeTheme {
    val attractions = attractionsFlow.collectAsLazyPagingItems()
    val likedItems by likedItemsFlow.collectAsState(initial = emptyList())
    val categories by categoriesFlow.collectAsState(initial = FetchState.Loading(true))

    SearchScreen(
        attractions = attractions,
        categoryState = categories,
        likedAttractions = likedItems,
        onSearch = setCurrentQuery,
        navigateToAttraction = navigateToEvent,
        navigateToCategory = navigateToCategory
    )
}

@Composable
internal fun SearchScreen(
    attractions: LazyPagingItems<AttractionViewModel>,
    categoryState: FetchState<List<CategoryViewModel>>,
    likedAttractions: List<LikedAttractionModel>,
    onSearch: (query: String) -> Unit,
    navigateToAttraction: (id: String) -> Unit,
    navigateToCategory: (id: String, name: String) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val drawerState = scaffoldState.drawerState
    val toggleDrawer = {
        when(drawerState.value) {
            DrawerValue.Closed -> drawerState.open()
            DrawerValue.Open  -> drawerState.close()
        }
    }

    Scaffold(
        topBar = {
            SearchToolbar(
                categoryState,
                toggleDrawer,
                onSearch,
                navigateToCategory
            )
        },
        drawerContent = {
            LazyColumn(modifier = Modifier.statusBarsPadding().padding(start = 8.dp, end = 8.dp)) {
                item { Text(stringResource(id = R.string.saved_events), style = MaterialTheme.typography.h6) }
                items(likedAttractions) { attraction ->
                    Text(text = attraction.id)
                }
            }
        },
        scaffoldState = scaffoldState,
        bodyContent = {
            when (attractions.loadState.refresh) {
                is LoadState.Loading -> CenteredCircularProgress()
                is LoadState.Error -> ErrorSnackbar(
                    snackbarHostState = scaffoldState.snackbarHostState,
                    message = stringResource(id = R.string.error_loading_events)
                )
                is LoadState.NotLoading -> {
                    if (attractions.itemCount == 0) {
                        NoResultsView()
                    } else {
                        SearchList(
                            attractions = attractions,
                            navigateToAttraction = navigateToAttraction
                        )
                    }
                }
            }
        }
    )
}

@Composable
internal fun SearchList(
    attractions: LazyPagingItems<AttractionViewModel>,
    navigateToAttraction: (id: String) -> Unit
) {
    LazyColumn {

        itemsIndexed(attractions) { _, attraction ->
            if (attraction != null) {
                SearchCard(
                    attraction = attraction,
                    navigateToAttraction = navigateToAttraction
                )
            }
        }

        when (attractions.loadState.append) {
            is LoadState.Loading -> item {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
            is LoadState.Error -> item {
                RetryView(onRetry = attractions::retry)
            }
            is LoadState.NotLoading -> {
            }
        }
    }
}

@Composable
internal fun RetryView(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.background(MaterialTheme.colors.error)
            .padding(top = 16.dp)
            .navigationBarsPadding()
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp),
            text = stringResource(id = R.string.error_loading_page),
            style = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.onError),
        )

        BorderButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            borderColor = MaterialTheme.colors.onError,
            onClick = onRetry
        ) {
            Text(text = stringResource(id = R.string.retry), color = MaterialTheme.colors.onError)
        }
    }
}

@Composable
internal fun NoResultsView() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(id = R.string.no_results),
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.h6
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RetryViewPreview() {
    RetryView(onRetry = { })
}