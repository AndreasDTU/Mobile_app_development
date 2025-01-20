package com.example.movieapp.ui.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieapp.data.model.Movie
import com.example.movieapp.ui.components.AppBackground
import com.example.movieapp.ui.theme.DarkPurple
import com.example.movieapp.ui.theme.LightPurple
import com.example.movieapp.ui.theme.TextWhite
import android.util.Log


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, searchViewModel: SearchViewModel = viewModel()) {
    val searchResults by searchViewModel.searchResults.collectAsState()
    val errorMessage by searchViewModel.errorMessage.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedYear by remember { mutableStateOf("All") }
    var selectedGenre by remember { mutableStateOf("All") }

    val genres = listOf("All", "Action", "Comedy", "Drama", "Horror", "Sci-Fi") // Static genres
    val years = listOf("All") + (2025 downTo 1900).map { it.toString() }

    AppBackground {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = "Advanced Search",
                color = TextWhite,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search movies...") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Filters
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterDropdown(label = "Year", selectedItem = selectedYear, items = years) { selectedYear = it }
                FilterDropdown(label = "Genre", selectedItem = selectedGenre, items = genres) { selectedGenre = it }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Search Button
            Button(
                onClick = {
                    searchViewModel.searchMoviesWithFilters(
                        query = searchQuery,
                        year = selectedYear,
                        genre = selectedGenre
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = DarkPurple)
            ) {
                Text("Search", color = TextWhite)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Display Search Results
            if (searchResults.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(searchResults) { movie ->
                        SearchResultCard(navController, movie)
                    }
                }
            } else if (!errorMessage.isNullOrEmpty()) {
                Text(
                    text = errorMessage ?: "Error",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(
                    text = "No movies found",
                    color = TextWhite,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun FilterDropdown(label: String, selectedItem: String, items: List<String>, onItemSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        TextButton(onClick = { expanded = true }) {
            Text("$label: $selectedItem", color = TextWhite)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    },
                    text = { Text(item) }
                )
            }
        }
    }
}

@Composable
fun SearchResultCard(navController: NavController, movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("MovieDetailScreen/${movie.id}") },
        colors = CardDefaults.cardColors(containerColor = LightPurple)
    ) {
        Column(Modifier.padding(8.dp)) {
            Text(movie.title, style = MaterialTheme.typography.titleMedium, color = TextWhite)
            Text(movie.releaseDate ?: "Unknown", color = TextWhite)
            Text(movie.overview ?: "No description available", color = TextWhite)
        }
    }
}
