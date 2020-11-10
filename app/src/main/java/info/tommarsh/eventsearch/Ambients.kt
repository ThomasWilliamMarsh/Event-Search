package info.tommarsh.eventsearch

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.ambientOf
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

val AmbientStatusBarHeight = ambientOf { 0.dp }

private fun Context.statusBarHeight(): Dp {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        return (resources.getDimensionPixelSize(resourceId) / resources.displayMetrics.density).roundToInt().dp
    }
    return 0.dp
}

@Composable
fun ProvidesStatusBarHeight(content: @Composable () -> Unit) {
    val context = ContextAmbient.current
    Providers(AmbientStatusBarHeight provides context.statusBarHeight()) {
        content()
    }
}