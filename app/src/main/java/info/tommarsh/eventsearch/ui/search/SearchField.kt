package info.tommarsh.eventsearch.ui.search

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import info.tommarsh.eventsearch.R

@Composable
fun SearchField() {
    Surface(
        color = MaterialTheme.colors.primaryVariant) {
        Column {
            SearchTextField()
            CategoriesList()
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
private fun CategoriesList() {
    val categories = listOf("Music", "Sport", "Arts, Theatre & Comedy", "Family & Attractions")
    LazyRowFor(items = categories, modifier = Modifier.padding(bottom = 8.dp)) { category ->
        TextButton(
            onClick = {},
            elevation = 0.dp,
            backgroundColor = MaterialTheme.colors.primaryVariant,
            border = BorderStroke(1.dp, Color.White),
            modifier = Modifier
                .wrapContentWidth()
                .height(64.dp)
                .padding(8.dp)
        ) {
            Text(text = category, color = MaterialTheme.colors.onPrimary)
        }
    }
}