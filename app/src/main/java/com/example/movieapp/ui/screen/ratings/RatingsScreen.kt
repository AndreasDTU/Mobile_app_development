package com.example.movieapp.ui.screen.ratings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.movieapp.data.model.Rating
import com.example.movieapp.repositories.MovieRepository

@Composable
fun RatingsScreen(
    viewModel: RatingsViewModel = viewModel(),
    movieRepository: MovieRepository
) {
    val ratings = viewModel.ratings.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "My Ratings",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        if (ratings.isEmpty()) {
            Text(
                text = "No ratings available",
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            LazyColumn {
                items(ratings) { rating ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Movie Poster
                        AsyncImage(
                            model = if (rating.posterPath?.isNotEmpty() == true) {
                                "https://image.tmdb.org/t/p/w500${rating.posterPath}"
                            } else {
                                null // Placeholder case: provide a placeholder drawable if needed
                            },
                            contentDescription = rating.title ?: "Movie Poster",
                            modifier = Modifier
                                .size(60.dp)
                                .padding(end = 8.dp)
                        )

                        // Movie Title and Rating
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = rating.title ?: "Unknown Title",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = "${rating.rating} ★",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}
