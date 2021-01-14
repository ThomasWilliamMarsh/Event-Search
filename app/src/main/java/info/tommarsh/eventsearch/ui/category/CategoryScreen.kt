package info.tommarsh.eventsearch.ui.category

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import info.tommarsh.eventsearch.theme.CategoryTheme

@Composable
fun CategoryScreen(
    name: String,
    id: String
) = CategoryTheme {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState) {
        Text(
            text = name,
            modifier = Modifier.statusBarsPadding(),
            style = MaterialTheme.typography.h4.copy(color = Color.White)
        )
    }
}