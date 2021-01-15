package info.tommarsh.eventsearch.ui.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.domain.LikedAttractionModel
import info.tommarsh.eventsearch.model.AttractionViewModel
import info.tommarsh.eventsearch.model.CategoryViewModel
import info.tommarsh.eventsearch.model.FetchState
import info.tommarsh.eventsearch.theme.EventHomeTheme
import info.tommarsh.eventsearch.ui.common.*
import info.tommarsh.eventsearch.ui.search.component.LikedAttractionCard
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
private fun SearchScreen(
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
        when (drawerState.value) {
            DrawerValue.Closed -> drawerState.open()
            DrawerValue.Open -> drawerState.close()
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
            LazyColumn(modifier = Modifier.statusBarsPadding()) {
                item { LikedAttractionsHeader() }
                items(likedAttractions) { attraction ->
                    LikedAttractionCard(
                        likedModel = attraction,
                        navigateToAttraction = navigateToAttraction
                    )
                }
            }
        },
        scaffoldState = scaffoldState,
        bodyContent = {
            WithPagingRefreshState(
                items = attractions,
                onLoading = {
                    CenteredCircularProgress()
                },
                onError = {
                    ErrorSnackbar(
                        snackbarHostState = scaffoldState.snackbarHostState,
                        message = stringResource(id = R.string.error_loading_events)
                    )
                },
                onLoaded = {
                    SearchList(
                        attractions = attractions,
                        navigateToAttraction = navigateToAttraction
                    )
                }
            )
        }
    )
}

@Composable
private fun SearchList(
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

        item {
            WithPagingAppendState(items = attractions)
        }
    }
}

@Composable
private fun LikedAttractionsHeader() {
    Text(
        stringResource(id = R.string.saved_events),
        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
        style = MaterialTheme.typography.h5
    )
}


@Preview(showBackground = true)
@Composable
fun RetryViewPreview() {
    RetryView(onRetry = { })
}