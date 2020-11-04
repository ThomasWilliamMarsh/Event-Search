package info.tommarsh.eventsearch.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

//Light
val grey200 = Color(0xFFEEEEEE)
val blueGray300 = Color(0xFF90A4AE)
val blueGray900 = Color(0xFF263238)
val blueGray700 = Color(0xFF455A64)
val gray500 = Color(0xFF616161)
val gray200 = Color(0xEEEEEE)

val DarkColorPalette = darkColors(
    primary = blueGray300,
    onPrimary = Color.White,
    primaryVariant = blueGray700,
    surface = Color.Black,
    onSurface = Color.White,
    onBackground = Color.White,
    background = Color.Black,
)

val LightColorPalette = lightColors(
    primary = blueGray700,
    onPrimary = Color.White,
    primaryVariant = blueGray900,
    surface = Color.White,
    onSurface = Color.Black,
    background = grey200,
)
