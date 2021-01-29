package info.tommarsh.eventsearch.ui.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import artCategory
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import familyCategory
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.model.CategoryViewModel
import info.tommarsh.eventsearch.model.FetchState
import info.tommarsh.eventsearch.theme.EventHomeTheme
import info.tommarsh.eventsearch.ui.common.BorderButton
import info.tommarsh.eventsearch.ui.common.TopToolbar
import kotlinx.coroutines.delay
import musicCategory
import sportCategory

private const val DEBOUNCE_MS = 1000L

@Composable
internal fun SearchToolbar(
    categoryState: FetchState<List<CategoryViewModel>>,
    toggleDrawer: () -> Unit,
    onSearch: (keyword: String) -> Unit,
    navigateToCategory: (id: String, name: String) -> Unit
) {
    Column(modifier = Modifier.background(color = MaterialTheme.colors.primaryVariant)) {
        TopToolbar(
            title = stringResource(id = R.string.app_name),
            modifier = Modifier.statusBarsPadding(),
            navigationIcon = {
                IconButton(onClick = toggleDrawer) {
                    Icon(
                        imageVector = vectorResource(id = R.drawable.ic_baseline_menu_24),
                        contentDescription = stringResource(R.string.open_liked_attractions_menu)
                    )
                }
            }
        )
        SearchField(
            categoryState = categoryState,
            onSearch = onSearch,
            navigateToCategory = navigateToCategory
        )
    }
}

@Composable
private fun SearchField(
    categoryState: FetchState<List<CategoryViewModel>>,
    onSearch: (keyword: String) -> Unit,
    navigateToCategory: (id: String, name: String) -> Unit
) {
    Surface(color = MaterialTheme.colors.primaryVariant) {
        Column {
            SearchTextField(onSearch)
            when (categoryState) {
                is FetchState.Loading -> {
                    /**No need to do anything here.**/
                }
                is FetchState.Success -> CategoriesList(
                    categories = categoryState.data,
                    navigateToCategory = navigateToCategory
                )
                is FetchState.Failure -> ErrorText()
            }
        }
    }
}

@Composable
private fun SearchTextField(onSearch: (keyword: String) -> Unit) {
    val (text, setText) = remember { mutableStateOf("") }

    LaunchedEffect(text) {
        delay(DEBOUNCE_MS)
        if (text.isNotEmpty()) onSearch(text)
    }

    TextField(
        value = text,
        onValueChange = { setText(it.replace("\n", "")) },
        label = {
            Text(
                text = stringResource(id = R.string.toolbar_hint_text),
                color = Color.White.copy(0.5f)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, MaterialTheme.colors.onPrimary, RoundedCornerShape(4.dp)),
        textStyle = MaterialTheme.typography.subtitle1,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search),
                tint = Color.White.copy(0.5f)
            )
        },
        shape = RoundedCornerShape(4.dp),
    )
}

@Composable
private fun CategoriesList(
    categories: List<CategoryViewModel>,
    navigateToCategory: (id: String, name: String) -> Unit
) {

    LazyRow(modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)) {
        items(categories) { category ->
            BorderButton(
                modifier = Modifier.padding(8.dp),
                onClick = { navigateToCategory(category.id, category.name) }) {
                Text(text = category.name, color = Color.White.copy(alpha = 0.5f))
            }
        }
    }
}

@Composable
private fun ErrorText() {
    Text(
        text = stringResource(id = R.string.failed_to_load_categories),
        color = Color.Red
    )
}

@Preview
@Composable
private fun ToolbarFailingToLoadCategories() {
    EventHomeTheme {
        SearchToolbar(categoryState = FetchState.Failure(Throwable()),
            navigateToCategory = { _, _ -> },
            onSearch = {},
            toggleDrawer = {})
    }
}

@Preview
@Composable
private fun ToolbarWithCategories() {
    EventHomeTheme {
        SearchToolbar(
            categoryState = FetchState.Success(
                data = listOf(
                    musicCategory,
                    sportCategory,
                    artCategory,
                    familyCategory
                )
            ),
            onSearch = {},
            navigateToCategory = { _, _ ->  },
            toggleDrawer = {}
        )
    }
}