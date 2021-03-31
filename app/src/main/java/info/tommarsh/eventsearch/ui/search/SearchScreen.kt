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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.insets.statusBarsPadding
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel
import info.tommarsh.eventsearch.core.ui.ReminderDialog
import info.tommarsh.eventsearch.model.AttractionViewModel
import info.tommarsh.eventsearch.navigation.Destinations
import info.tommarsh.eventsearch.theme.SearchTheme
import info.tommarsh.eventsearch.ui.common.CenteredCircularProgress
import info.tommarsh.eventsearch.ui.common.ErrorSnackbar
import info.tommarsh.eventsearch.ui.common.LoadStateFooter
import info.tommarsh.eventsearch.ui.common.ScrollToTopButton
import info.tommarsh.eventsearch.ui.search.component.LikedAttractionCard
import info.tommarsh.eventsearch.ui.search.component.SearchCard
import info.tommarsh.eventsearch.ui.search.component.SearchToolbar
import info.tommarsh.eventsearch.ui.search.model.SearchScreenAction
import info.tommarsh.eventsearch.ui.search.model.SearchScreenAction.*
import info.tommarsh.eventsearch.ui.search.model.SearchScreenEffect
import info.tommarsh.eventsearch.ui.search.model.SearchScreenEffect.*
import info.tommarsh.eventsearch.ui.search.model.SearchScreenState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
internal fun SearchScreen(
    controller: NavController,
    reminderDialog: ReminderDialog
) {
    val viewModel = hiltNavGraphViewModel<SearchViewModel>()
    SearchScreen(
        viewModel = viewModel,
        reminderDialog = reminderDialog,
        controller = controller
    )
}

@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel,
    reminderDialog: ReminderDialog,
    controller: NavController
) {
    val screenState by viewModel.screenState.collectAsState()
    val attractions = viewModel.attractions(screenState.query).collectAsLazyPagingItems()

    SearchScreen(
        attractions = attractions,
        screenState = screenState
    ) { action ->
        when (action) {
            is SettingsButtonClicked -> controller.navigate(Destinations.SETTINGS)
            is AttractionClicked -> controller.navigate("${Destinations.EVENT}/${action.id}")
            is CategoryClicked -> controller.navigate("${Destinations.CATEGORY}/${action.id}/${action.name}")
            else -> viewModel.postAction(action)
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.effects.collectLatest { effect ->
            when(effect) {
                is ShowReminderDialog -> reminderDialog.show(effect.id, effect.name, effect.image)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun SearchScreen(
    attractions: LazyPagingItems<AttractionViewModel>,
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
                setAttractionReminder = { model -> actionDispatcher(SetReminder(model)) },
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
                        SearchCard(
                            attraction = attraction,
                            navigateToAttraction = { id -> actionDispatcher(AttractionClicked(id)) }
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
    setAttractionReminder: (LikedAttractionModel) -> Unit,
    deleteLikedAttraction: (LikedAttractionModel) -> Unit
) {
    LazyColumn(modifier = Modifier.statusBarsPadding()) {
        item { LikedAttractionsHeader() }
        items(likedAttractions) { attraction ->
            LikedAttractionCard(
                likedModel = attraction,
                setAttractionReminder = setAttractionReminder,
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