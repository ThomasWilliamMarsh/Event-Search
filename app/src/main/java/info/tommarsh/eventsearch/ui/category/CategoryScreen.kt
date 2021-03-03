package info.tommarsh.eventsearch.ui.category

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import attraction
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.model.AttractionViewModel
import info.tommarsh.eventsearch.theme.CategoryTheme
import info.tommarsh.eventsearch.ui.common.CenteredCircularProgress
import info.tommarsh.eventsearch.ui.common.ErrorSnackbar
import info.tommarsh.eventsearch.ui.common.WithPagingAppendState
import info.tommarsh.eventsearch.ui.common.WithPagingRefreshState
import info.tommarsh.eventsearch.ui.search.component.SearchCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun CategoryScreen(
    categoryName: String,
    attractionsFlow: Flow<PagingData<AttractionViewModel>>,
    navigateToEvent: (id: String) -> Unit
) = CategoryTheme {
    val attractions = attractionsFlow.collectAsLazyPagingItems()
    CategoryScreen(
        categoryName = categoryName,
        attractions = attractions,
        navigateToAttraction = navigateToEvent
    )
}

@Composable
private fun CategoryScreen(
    categoryName: String,
    attractions: LazyPagingItems<AttractionViewModel>,
    navigateToAttraction: (id: String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
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
            CategoryList(
                categoryName = categoryName,
                attractions = attractions,
                navigateToAttraction = navigateToAttraction
            )
        }
    )
}

@Composable
private fun CategoryList(
    categoryName: String,
    attractions: LazyPagingItems<AttractionViewModel>,
    navigateToAttraction: (id: String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        item {
            Text(
                modifier = Modifier.padding(16.dp),
                text = categoryName,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h4.copy(color = Color.White)
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
            WithPagingAppendState(items = attractions)
        }
    }
}

@Preview
@Composable
fun CategoryScreenPreview() = CategoryTheme {
    val attractions = flowOf(
        PagingData.from(
            listOf(
                attraction,
                attraction,
                attraction,
                attraction
            )

        )
    )
    CategoryScreen(
        categoryName = "Theatre",
        attractions = attractions.collectAsLazyPagingItems(),
        navigateToAttraction = { })
}

