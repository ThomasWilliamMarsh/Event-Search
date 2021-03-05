package info.tommarsh.eventsearch.ui.category

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.model.AttractionViewModel
import info.tommarsh.eventsearch.navigation.Arguments
import info.tommarsh.eventsearch.navigation.Destinations
import info.tommarsh.eventsearch.stringArg
import info.tommarsh.eventsearch.theme.CategoryTheme
import info.tommarsh.eventsearch.ui.common.CenteredCircularProgress
import info.tommarsh.eventsearch.ui.common.ErrorSnackbar
import info.tommarsh.eventsearch.ui.common.LoadStateFooter
import info.tommarsh.eventsearch.ui.search.component.SearchCard

@Composable
internal fun CategoryScreen(
    backStackEntry: NavBackStackEntry,
    controller: NavHostController
) = CategoryTheme {
    val viewModel = viewModel<CategoryViewModel>(
        factory = HiltViewModelFactory(LocalContext.current, backStackEntry)
    )
    val id = backStackEntry.stringArg(Arguments.ID)
    val name = backStackEntry.stringArg(Arguments.NAME)
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
) {
    val scaffoldState = rememberScaffoldState()

    LazyColumn(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {
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
