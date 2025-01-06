package com.example.movieapp.ui.screen.moviedetails

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieapp.ui.components.AppBackground
import com.example.movieapp.ui.theme.LightPurple
import com.example.movieapp.ui.theme.TextWhite

@Composable
fun MovieDetailScreen(id: Int, navController: NavController, viewModel: MovieDetailViewModel = viewModel()) {

    val movie = viewModel.movieDetails.collectAsState().value
    Log.d("MovieDetailScreen", "The movie details are: $movie")

    AppBackground {
        // Display movie details if available
        if (movie != null) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Movie Poster
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${movie.posterpath}",
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.7f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Movie Title
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextWhite,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Release Year
                Text(
                    text = "Release Year: ${movie.releasedate}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LightPurple,
                    textAlign = TextAlign.Center
                )

                // Rating
                Text(
                    text = "Rating: ${movie.voteaverage}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LightPurple,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Overview
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextWhite,
                    textAlign = TextAlign.Start
                )
            }
        } else {
            // Display loading state or message
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextWhite
                )
            }
        }
    }
}

