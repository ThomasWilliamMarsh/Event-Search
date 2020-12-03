package info.tommarsh.eventsearch.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
fun AttractionDetailTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette.copy(surface = ebon)
    } else {
        LightColorPalette
    }

    EventSearchAppTheme(colors = colors, content = content)
}

@Composable
fun EventHomeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    EventSearchAppTheme(colors = colors, content = content)
}


@Composable
private fun EventSearchAppTheme(
    colors: Colors,
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colors = colors,
        typography = EventSearchTypography,
        shapes = shapes,
        content = content
    )
}