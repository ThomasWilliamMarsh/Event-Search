package info.tommarsh.eventsearch.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

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
fun SearchTheme(
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
fun CategoryTheme(
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
fun SettingsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette.copy(primary = gray200, onPrimary = Color.Black)
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