package info.tommarsh.eventsearch.ui.category.screen

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.tommarsh.eventsearch.ui.common.TopToolbar

@Composable
fun CategoryScreen(
    name: String,
    id: String
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(topBar = { TopToolbar(title = name) },
        scaffoldState = scaffoldState,
        bodyContent = {}
    )
}

@Composable
private fun CategoryHeader(text: String) {
    Column(Modifier.padding(8.dp)) {
        Text(text = text, style = MaterialTheme.typography.h3)
        Box(
            modifier = Modifier.height(5.dp)
                .width(60.dp)
                .background(color = Color.Red)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryHeaderPreview() {
    CategoryHeader(text = "Pre-Sale")
}