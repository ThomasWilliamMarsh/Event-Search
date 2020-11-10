package info.tommarsh.eventsearch.ui.search.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRowFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedTask
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import info.tommarsh.eventsearch.AmbientStatusBarHeight
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.model.*
import info.tommarsh.eventsearch.theme.EventHomeTheme
import info.tommarsh.eventsearch.ui.common.TopToolbar
import kotlinx.coroutines.delay

private const val DEBOUNCE_MS = 1000L

@Composable
fun SearchToolbar(
    categoryState: FetchState<List<CategoryViewModel>>,
    onSearch: (keyword: String) -> Unit,
    navigateToCategory: (id: String, name: String) -> Unit
) {
    Column(modifier = Modifier.background(color = MaterialTheme.colors.primaryVariant)){
        TopToolbar(
            title = stringResource(id = R.string.app_name),
            modifier = Modifier.padding(top = AmbientStatusBarHeight.current)
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
                    categories = categoryState.items,
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

    LaunchedTask(text) {
        delay(DEBOUNCE_MS)
        if (text.isNotEmpty()) onSearch(text)
    }

    TextField(
        value = text,
        onValueChange = { setText(it) },
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
        trailingIcon = { Icon(asset = Icons.Default.Search, tint = Color.White.copy(0.5f)) },
        shape = RoundedCornerShape(4.dp),
    )
}

@Composable
private fun CategoriesList(
    categories: List<CategoryViewModel>,
    navigateToCategory: (id: String, name: String) -> Unit
) {
    LazyRowFor(
        items = categories,
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
    ) { category ->
        BorderButton(
            modifier = Modifier.padding(8.dp),
            onClick = { navigateToCategory(category.name, category.id) }) {
            Text(text = category.name, color = Color.White.copy(alpha = 0.5f))
        }
    }
}

@Composable
private fun BorderButton(
    borderColor: Color = MaterialTheme.colors.onPrimary,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    TextButton(
        onClick = { onClick() },
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier
            .wrapContentWidth()
            .height(52.dp) then modifier
    ) {
        content()
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
            onSearch = {})
    }
}

@Preview
@Composable
private fun ToolbarWithCategories() {
    EventHomeTheme {
        SearchToolbar(
            categoryState = FetchState.Success(
                items = listOf(
                    musicCategory,
                    sportCategory,
                    artCategory,
                    familyCategory
                )
            ),
            onSearch = {},
            navigateToCategory = { _, _ -> Unit }
        )
    }
}