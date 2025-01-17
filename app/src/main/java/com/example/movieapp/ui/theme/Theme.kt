package com.example.movieapp.ui.theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Define Dark and Light color schemes
private val DarkColorScheme = darkColorScheme(
    primary = MediumPurple,
    secondary = LightPurple,
    tertiary = AccentPink,
    background = DarkPurple,
    surface = DarkPurple,
    onPrimary = TextWhite,
    onSecondary = TextGray,
    onTertiary = TextWhite,
    onBackground = TextWhite,
    onSurface = TextGray
)

private val LightColorScheme = lightColorScheme(
    primary = MediumPurple,
    secondary = LightPurple,
    tertiary = AccentPink,
    background = Color(0xFFF2F2F2), // Lys baggrund for lyse temaer
    surface = Color.White,
    onPrimary = TextWhite,
    onSecondary = TextGray,
    onTertiary = TextWhite,
    onBackground = Color.Black,
    onSurface = Color.Black
)


@Composable
fun MovieAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

