package info.tommarsh.eventsearch.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

//TODO: Same colours as light for now
private val DarkColorPalette = darkColors(
    primary = blueGray700,
    onPrimary = Color.White,
    primaryVariant = blueGray900,
    surface = Color.White,
    onSurface = Color.Black,
    secondary = Color.White,
    background = grey200,
)

private val LightColorPalette = lightColors(
    primary = blueGray700,
    onPrimary = Color.White,
    primaryVariant = blueGray900,
    surface = Color.White,
    onSurface = Color.Black,
    secondary = Color.White,
    background = grey200,
)

@Composable
fun EventSearchAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}