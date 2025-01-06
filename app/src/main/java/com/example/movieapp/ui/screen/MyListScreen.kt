package com.example.movieapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MyList() {
    // Main Container
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A)) // Dark background (grayish/black)
    ) {
        // Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFB6C1)) // Pink color for the top bar
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = "My List",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge, // Use Material 3 typography
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Sections
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                SectionTitle("My List")
                MovieRow()
            }
            item {
                SectionTitle("Recently Watched")
                MovieRow()
            }
            item {
                SectionTitle("Favorites")
                MovieRow()
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = Color.White,
        style = MaterialTheme.typography.titleMedium, // Use Material 3 typography
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun MovieRow() {
    LazyRow {
        items(3) { // Add placeholder content for now
            MovieCard()
        }
    }
}

@Composable
fun MovieCard() {
    Column(
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(180.dp)
                .background(Color.Gray) // Placeholder for the movie poster
        )
        androidx.compose.material3.Icon(
            imageVector = androidx.compose.material.icons.Icons.Default.FavoriteBorder,
            contentDescription = "Like",
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .padding(top = 4.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}
