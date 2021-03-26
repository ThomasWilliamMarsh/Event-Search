package info.tommarsh.eventsearch.ui.search

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.insets.statusBarsPadding
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel
import info.tommarsh.eventsearch.model.AttractionViewModel
import info.tommarsh.eventsearch.model.CategoryViewModel
import info.tommarsh.eventsearch.model.FetchState
import info.tommarsh.eventsearch.navigation.Destinations
import info.tommarsh.eventsearch.theme.SearchTheme
import info.tommarsh.eventsearch.ui.common.CenteredCircularProgress
import info.tommarsh.eventsearch.ui.common.ErrorSnackbar
import info.tommarsh.eventsearch.ui.common.LoadStateFooter
import info.tommarsh.eventsearch.ui.common.ScrollToTopButton
import info.tommarsh.eventsearch.ui.search.component.LikedAttractionCard
import info.tommarsh.eventsearch.ui.search.component.SearchCard
import info.tommarsh.eventsearch.ui.search.component.SearchToolbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
internal fun SearchScreen(
    controller: NavController
) {
    val viewModel = hiltNavGraphViewModel<SearchViewModel>()
    SearchScreen(
        viewModel = viewModel,
        controller = controller
    )
}

@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel,
    controller: NavController
) {
    val (currentQuery, setCurrentQuery) = remember { mutableStateOf("") }
    val attractions = viewModel.attractions(currentQuery).collectAsLazyPagingItems()
    val likedItems by viewModel.likedAttractions.collectAsState(initial = emptyList())
    val categories by viewModel.categories.collectAsState(initial = FetchState.Loading(true))

    SearchScreen(
        attractions = attractions,
        categoryState = categories,
        likedAttractions = likedItems,
        deleteLikedAttraction = viewModel::deleteLikedAttraction,
        onSearch = setCurrentQuery,
        navigateToSettings = { controller.navigate(Destinations.SETTINGS) },
        navigateToAttraction = { id -> controller.navigate("${Destinations.EVENT}/$id") },
        navigateToCategory = { id, name -> controller.navigate("${Destinations.CATEGORY}/$id/$name") }
    )

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun SearchScreen(
    attractions: LazyPagingItems<AttractionViewModel>,
    categoryState: FetchState<List<CategoryViewModel>>,
    likedAttractions: List<LikedAttractionModel>,
    deleteLikedAttraction: (LikedAttractionModel) -> Unit,
    onSearch: (query: String) -> Unit,
    navigateToSettings: () -> Unit,
    navigateToAttraction: (id: String) -> Unit,
    navigateToCategory: (id: String, name: String) -> Unit,
) = SearchTheme {
    val scaffoldState = rememberScaffoldState()
    val drawerState = scaffoldState.drawerState
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Scaffold(
        drawerContent = {
            SavedEventsDrawer(
                likedAttractions = likedAttractions,
                deleteLikedAttraction = deleteLikedAttraction,
                navigateToAttraction = navigateToAttraction
            )
        },
        scaffoldState = scaffoldState
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {

                item {
                    SearchToolbar(
                        categoryState,
                        drawerState,
                        onSearch,
                        navigateToSettings,
                        navigateToCategory
                    )
                }

                itemsIndexed(attractions) { _, attraction ->
                    if (attraction != null) {
                        SearchCard(
                            attraction = attraction,
                            navigateToAttraction = navigateToAttraction
                        )
                    }
                }

                item {
                    LoadStateFooter(items = attractions)
                }
            }

            when (attractions.loadState.refresh) {
                is LoadState.Loading -> CenteredCircularProgress()
                is LoadState.Error -> ErrorSnackbar(
                    snackbarHostState = scaffoldState.snackbarHostState,
                    message = stringResource(id = R.string.error_loading_events)
                )
                is LoadState.NotLoading -> {
                    ScrollToTopButton(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        listState = listState,
                    ) {
                        scope.launch { listState.animateScrollToItem(0) }
                    }
                }
            }
        }
    }
}

@Composable
private fun SavedEventsDrawer(
    likedAttractions: List<LikedAttractionModel>,
    navigateToAttraction: (id: String) -> Unit,
    deleteLikedAttraction: (LikedAttractionModel) -> Unit
) {
    LazyColumn(modifier = Modifier.statusBarsPadding()) {
        item { LikedAttractionsHeader() }
        items(likedAttractions) { attraction ->
            LikedAttractionCard(
                likedModel = attraction,
                navigateToAttraction = navigateToAttraction,
                deleteLikedAttraction = deleteLikedAttraction
            )
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