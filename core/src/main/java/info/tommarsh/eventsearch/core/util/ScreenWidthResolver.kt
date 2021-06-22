package info.tommarsh.eventsearch.core.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ScreenWidthResolver
@Inject constructor(@ApplicationContext private val context: Context) {
    fun get() = context.resources.displayMetrics.widthPixels
}

@Composable
fun rememberScreenWidthResolver(): ScreenWidthResolver {
    val context = LocalContext.current
    return remember { ScreenWidthResolver(context) }
}