package info.tommarsh.eventsearch

import android.app.Application
import androidx.compose.runtime.Composable
import dagger.hilt.android.HiltAndroidApp
import info.tommarsh.eventsearch.theme.EventSearchAppTheme

@HiltAndroidApp
class EventSearchApp : Application()

@Composable
fun EventSearchApp(content: @Composable () -> Unit) {
    EventSearchAppTheme {
        content()
    }
}