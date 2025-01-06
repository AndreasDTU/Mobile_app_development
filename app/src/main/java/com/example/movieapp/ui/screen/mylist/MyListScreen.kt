package com.example.movieapp.ui.screen.mylist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieapp.ui.theme.MovieappTheme
import com.example.movieapp.ui.theme.DarkPurple
import com.example.movieapp.ui.theme.MediumPurple
import com.example.movieapp.ui.theme.LightPurple
import com.example.movieapp.ui.theme.TextWhite

@Composable
fun MyList() {
    // Main Container with Enhanced Gradient Background
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MediumPurple.copy(alpha = 0.95f),
                        DarkPurple
                    )
                )
            )
    ) {
        // Sleek Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            LightPurple.copy(alpha = 0.7f),
                            MediumPurple
                        )
                    )
                )
                .padding(vertical = 24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Placeholder for a back button or icon
                Text(
                    text = "My List",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                // Placeholder for an action button or icon
            }
        }

        // Content Sections
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
        color = TextWhite, // Highlighted color for section titles
        style = MaterialTheme.typography.titleMedium, // Use refined typography
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun MovieRow() {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(5) { // Add placeholder content
            MovieCard()
        }
    }
}

@Composable
fun MovieCard() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(120.dp)
            .padding(bottom = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(180.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MediumPurple,
                            DarkPurple
                        )
                    )
                )
        )
        Icon(
            imageVector = androidx.compose.material.icons.Icons.Default.FavoriteBorder,
            contentDescription = "Like",
            tint = LightPurple, // Accent color for the heart icon
            modifier = Modifier
                .size(24.dp)
                .padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyListPreview() {
    MovieappTheme {
        MyList()
    }
}
