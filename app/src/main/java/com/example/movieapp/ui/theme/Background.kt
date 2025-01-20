package com.example.movieapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.example.movieapp.ui.theme.DarkPurple
import com.example.movieapp.ui.theme.LightPurple
import com.example.movieapp.ui.theme.MediumPurple
import androidx.compose.ui.graphics.Color


@Composable
fun AppBackground(
    isDarkTheme: Boolean, // Add isDarkTheme parameter
    content: @Composable () -> Unit
) {
    val backgroundColors = if (isDarkTheme) {
        listOf(MediumPurple.copy(alpha = 0.95f), DarkPurple) // Gradient for dark theme
    } else {
        listOf(LightPurple.copy(alpha = 0.95f), Color(0xFFFFC0CB)) // Gradient for light theme
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = backgroundColors
                )
            )
    ) {
        content()
    }
}
