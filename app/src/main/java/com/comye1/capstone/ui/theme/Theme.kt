package com.comye1.capstone.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xffacc7ff),
//    primaryVariant = Color(),
    secondary = Teal200,
)

private val LightColorPalette = lightColors(
    primary = Color(0xff315da8),
    primaryVariant = Color(0xffd6e2ff),
    secondary = Color(0xff575e71),
    secondaryVariant = Color(0xffe1e2ec),
    error = Color(0xffba1b1b),
    background = Color(0xfffdfbff),
    onPrimary = Color(0xffffffff),
    onSecondary = Color(0xffffffff),
    onError = Color(0xffffffff),
    onBackground = Color(0xff1b1b1e),
    surface = Color(0xfffdfbff),
    onSurface = Color(0xff1b1b1e),

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun CapstoneTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}