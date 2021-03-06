package info.tommarsh.eventsearch.ui

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.insets.statusBarsPadding
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel
import info.tommarsh.eventsearch.core.navigation.Screen
import info.tommarsh.eventsearch.core.theme.SearchTheme
import info.tommarsh.eventsearch.core.ui.*
import info.tommarsh.eventsearch.model.SearchAttractionViewModel
import info.tommarsh.eventsearch.ui.component.LikedAttractionCard
import info.tommarsh.eventsearch.ui.component.SearchToolbar
import info.tommarsh.eventsearch.ui.model.SearchScreenAction
import info.tommarsh.eventsearch.ui.model.SearchScreenAction.*
import info.tommarsh.eventsearch.ui.model.SearchScreenState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
internal fun SearchScreen(
    controller: NavController
) {
    val viewModel = hiltViewModel<SearchViewModel>()
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
    val screenState by viewModel.screenState.collectAsState()
    val attractions = viewModel.attractions(screenState.query).collectAsLazyPagingItems()

    SearchScreen(
        attractions = attractions,
        screenState = screenState
    ) { action ->
        when (action) {
            is SettingsButtonClicked -> controller.navigate(Screen.Settings.route)
            is AttractionClicked -> controller.navigate(Screen.Attraction.route(action.id))
            is CategoryClicked -> controller.navigate(Screen.Category.route(action.id, action.name))
            else -> viewModel.postAction(action)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun SearchScreen(
    attractions: LazyPagingItems<SearchAttractionViewModel>,
    screenState: SearchScreenState,
    actionDispatcher: (SearchScreenAction) -> Unit
) = SearchTheme {
    val scaffoldState = rememberScaffoldState()
    val drawerState = scaffoldState.drawerState
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Scaffold(
        drawerContent = {
            SavedEventsDrawer(
                likedAttractions = screenState.likedAttractions,
                deleteLikedAttraction = { model -> actionDispatcher(AttractionDeleted(model)) },
                navigateToAttraction = { id -> actionDispatcher(AttractionClicked(id)) }
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
                        screenState.categories,
                        drawerState,
                        { query -> actionDispatcher(QueryEntered(query)) },
                        { actionDispatcher(SettingsButtonClicked) },
                        { id, name -> actionDispatcher(CategoryClicked(id, name)) }
                    )
                }

                itemsIndexed(attractions) { _, attraction ->
                    if (attraction != null) {
                        AttractionCard(
                            name = attraction.name,
                            numberOfEvents = attraction.numberOfEvents,
                            imageUrl = attraction.searchImage,
                            onClick = { actionDispatcher(AttractionClicked(attraction.id)) }
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