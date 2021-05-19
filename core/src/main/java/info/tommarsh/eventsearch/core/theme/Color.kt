package info.tommarsh.eventsearch.core.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

//Light
val gray200 = Color(0xFFEEEEEE)
val blueGray300 = Color(0xFF90A4AE)
val blueGray900 = Color(0xFF263238)
val blueGray700 = Color(0xFF455A64)
val red200 = Color(0xFFFF5252)
val amber200 = Color(0xFFFFD740)
val ebon = Color(0xFF222222)

val DarkColorPalette = darkColors(
    primary = blueGray700,
    onPrimary = Color.White,
    primaryVariant = blueGray300,
    surface = Color.Black,
    onSurface = Color.White,
    onBackground = Color.White,
    background = Color.Black,
    error = red200,
    onError = Color.White
)

val LightColorPalette = lightColors(
    primary = blueGray900,
    onPrimary = Color.White,
    primaryVariant = blueGray700,
    surface = Color.White,
    onSurface = Color.Black,
    background = gray200,
    error = red200,
    onError = Color.White
)
