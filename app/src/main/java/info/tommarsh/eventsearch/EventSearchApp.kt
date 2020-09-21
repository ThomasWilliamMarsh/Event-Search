package info.tommarsh.eventsearch

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.viewinterop.viewModel
import dagger.hilt.android.HiltAndroidApp
import info.tommarsh.eventsearch.navigation.NavigationViewModel
import info.tommarsh.eventsearch.navigation.Screen
import info.tommarsh.eventsearch.theme.EventSearchAppTheme
import info.tommarsh.eventsearch.ui.event.EventScreen
import info.tommarsh.eventsearch.ui.search.SearchScreen

@HiltAndroidApp
class EventSearchApp : Application()

@Composable
fun EventSearchApp(content: @Composable () -> Unit) {
    EventSearchAppTheme {
        content()
    }
}

@Composable
fun TopLevel() {
    val navigator = viewModel<NavigationViewModel>()
    Providers(Navigator provides navigator) {
        val currentScreen by navigator.currentScreen.observeAsState(initial = Screen.SEARCH)
        when(currentScreen) {
            Screen.SEARCH -> SearchScreen()
            Screen.EVENT -> EventScreen()
        }
    }
}