package com.example.movieapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.example.movieapp.ui.theme.DarkPurple
import com.example.movieapp.ui.theme.MediumPurple

@Composable
fun AppBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(MediumPurple.copy(alpha = 0.95f), DarkPurple)
                )
            )
    ) {
        content()
    }
}
