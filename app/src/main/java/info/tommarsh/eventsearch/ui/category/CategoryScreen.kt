package info.tommarsh.eventsearch.ui.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import info.tommarsh.eventsearch.model.AttractionViewModel
import info.tommarsh.eventsearch.navigation.Arguments
import info.tommarsh.eventsearch.navigation.Destinations
import info.tommarsh.eventsearch.stringArg
import info.tommarsh.eventsearch.theme.CategoryTheme
import info.tommarsh.eventsearch.ui.common.CenteredCircularProgress
import info.tommarsh.eventsearch.ui.common.ErrorSnackbar
import info.tommarsh.eventsearch.ui.common.LoadStateFooter
import info.tommarsh.eventsearch.ui.common.ScrollToTopButton
import info.tommarsh.eventsearch.ui.search.component.SearchCard
import kotlinx.coroutines.launch

@Composable
internal fun CategoryScreen(
    backStackEntry: NavBackStackEntry,
    controller: NavController
) {
    val viewModel = hiltNavGraphViewModel<CategoryViewModel>()
    val id = backStackEntry.stringArg(Arguments.ID)
    val name = backStackEntry.stringArg(Arguments.NAME)

    CategoryScreen(
        viewModel = viewModel,
        controller = controller,
        id = id,
        name = name
    )
}

@Composable
internal fun CategoryScreen(
    viewModel: CategoryViewModel,
    controller: NavController,
    id: String,
    name: String

) {
    val attractions = viewModel.attractions(id).collectAsLazyPagingItems()
    CategoryScreen(
        categoryName = name,
        attractions = attractions,
        navigateToAttraction = { controller.navigate("${Destinations.EVENT}/$it") }
    )
}

@Composable
private fun CategoryScreen(
    categoryName: String,
    attractions: LazyPagingItems<AttractionViewModel>,
    navigateToAttraction: (id: String) -> Unit
) = CategoryTheme {
    val scaffoldState = rememberScaffoldState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {

        LazyColumn(state = listState) {
            item {
                CategoryTitle(categoryName)
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

@Composable
private fun CategoryTitle(name: String) {
    Text(
        modifier = Modifier.padding(16.dp),
        text = name,
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.h4.copy(color = Color.White)
    )
}
