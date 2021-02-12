package info.tommarsh.eventsearch.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import info.tommarsh.eventsearch.R

private val RobotoSlab = FontFamily(
    Font(R.font.roboto_slab_semi_bold, FontWeight.W600),
    Font(R.font.roboto_slab_medium, FontWeight.W500),
    Font(R.font.roboto_slab_regular)
)

val EventSearchTypography = Typography(
    h3 = TextStyle(
        fontFamily = RobotoSlab,
        fontWeight = FontWeight.W600,
        fontSize = 48.sp
    ),

    h4 = TextStyle(
        fontFamily = RobotoSlab,
        fontWeight = FontWeight.W600,
        fontSize = 34.sp
    ),

    h5 = TextStyle(
        fontFamily = RobotoSlab,
        fontWeight = FontWeight.W600,
        fontSize = 24.sp
    ),

    h6 = TextStyle(
        fontFamily = RobotoSlab,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp
    ),

    subtitle1 = TextStyle(
        fontFamily = RobotoSlab,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    ),

    subtitle2 = TextStyle(
        fontFamily = RobotoSlab,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),

    body1 = TextStyle(
        fontFamily = RobotoSlab,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    body2 = TextStyle(
        fontFamily = RobotoSlab,
        fontSize = 14.sp
    ),

    button = TextStyle(
        fontFamily = RobotoSlab,
        fontWeight = FontWeight.W600,
        fontSize = 14.sp
    ),

    caption = TextStyle(
        fontFamily = RobotoSlab,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),

    overline = TextStyle(
        fontFamily = RobotoSlab,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp
    ),
)