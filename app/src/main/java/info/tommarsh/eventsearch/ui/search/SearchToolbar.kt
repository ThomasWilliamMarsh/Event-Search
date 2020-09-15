package info.tommarsh.eventsearch.ui.search

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRowFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.ui.search.model.CategoryViewModel
import info.tommarsh.eventsearch.ui.search.model.FetchState

@Composable
fun SearchToolbar(categoryState: FetchState<CategoryViewModel>) {
    Column {
        TopToolbar()
        SearchField(categoryState = categoryState)
    }
}

@Composable
private fun TopToolbar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h6
            )
        },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        elevation = 0.dp
    )
}

@Composable
private fun SearchField(categoryState: FetchState<CategoryViewModel>) {
    Surface(color = MaterialTheme.colors.primaryVariant) {
        Column {
            SearchTextField()
            when (categoryState) {
                is FetchState.Loading -> {
                }
                is FetchState.Success -> CategoriesList(categoryState.items)
                is FetchState.Failure -> {
                }
            }
        }
    }
}

@Composable
private fun SearchTextField() {
    val textState = remember { mutableStateOf(TextFieldValue()) }
    TextField(
        value = textState.value,
        onValueChange = { textState.value = it },
        label = { Text(text = "Search by Artist, Event or Venue") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, MaterialTheme.colors.onPrimary, RoundedCornerShape(4.dp)),
        textStyle = MaterialTheme.typography.subtitle1,
        activeColor = MaterialTheme.colors.onPrimary,
        inactiveColor = MaterialTheme.colors.onPrimary,
        trailingIcon = {
            Icon(
                asset = Icons.Default.Search,
                tint = MaterialTheme.colors.onPrimary
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(4.dp),
    )
}

@Composable
private fun CategoriesList(categories: List<CategoryViewModel>) {
    LazyRowFor(
        items = categories,
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
    ) { category ->
        BorderTextButton(text = category.name, modifier = Modifier.padding(8.dp))
    }
}

@Composable
private fun BorderTextButton(
    text: String,
    borderColor: Color = MaterialTheme.colors.onPrimary,
    textColor: Color = MaterialTheme.colors.onPrimary,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = {},
        elevation = 0.dp,
        backgroundColor = Color.Transparent,
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier
            .wrapContentWidth()
            .height(52.dp) then modifier
    ) {
        Text(text = text, color = textColor)
    }
}