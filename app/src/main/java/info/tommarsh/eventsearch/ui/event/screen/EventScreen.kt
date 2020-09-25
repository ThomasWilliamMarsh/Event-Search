package info.tommarsh.eventsearch.ui.event.screen

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import info.tommarsh.eventsearch.ui.common.TopToolbar

@Composable
fun EventScreen(name: String, id: String) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(topBar = { TopToolbar(title = name) },
        scaffoldState = scaffoldState,
        bodyContent = {}
    )
}