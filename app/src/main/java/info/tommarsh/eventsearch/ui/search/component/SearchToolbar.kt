package info.tommarsh.eventsearch.ui.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.model.CategoryViewModel
import info.tommarsh.eventsearch.core.ui.BorderButton
import info.tommarsh.eventsearch.core.ui.TopToolbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val DEBOUNCE_MS = 1000L

@Composable
internal fun SearchToolbar(
    categories: List<CategoryViewModel>,
    drawerState: DrawerState,
    onSearch: (query: String) -> Unit,
    navigateToSettings: () -> Unit,
    navigateToCategory: (id: String, name: String) -> Unit
) {
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.background(color = MaterialTheme.colors.primary)) {
        TopToolbar(
            title = stringResource(id = R.string.app_name),
            modifier = Modifier.statusBarsPadding(),
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        if (drawerState.isOpen) drawerState.close() else drawerState.open()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(R.string.open_liked_attractions_menu)
                    )
                }
            },
            actions = {
                IconButton(onClick = { navigateToSettings() }) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = stringResource(R.string.settings)
                    )
                }
            }
        )
        SearchField(
            categories = categories,
            onSearch = onSearch,
            navigateToCategory = navigateToCategory
        )
    }
}

@Composable
private fun SearchField(
    categories: List<CategoryViewModel>,
    onSearch: (keyword: String) -> Unit,
    navigateToCategory: (id: String, name: String) -> Unit
) {
    Surface(color = MaterialTheme.colors.primary) {
        Column {
            SearchTextField(onSearch)
            CategoriesList(
                categories = categories,
                navigateToCategory = navigateToCategory
            )
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
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = { onSearch(text) })
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