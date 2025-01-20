package com.example.movieapp.ui.theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Define Dark and Light color schemes
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFFC0CB), // Pink for primary color
    secondary = Color(0xFFFFA6C9), // Slightly lighter pink for secondary elements
    tertiary = Color(0xFFF48FB1), // Accent pink for highlights
    background = Color(0xFFFFC0CB), // Light pink background
    surface = Color(0xFFFFE4E1), // Light pink surface
    onPrimary = Color.Black, // Black text on pink
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

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


@Composable
fun MovieappTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

