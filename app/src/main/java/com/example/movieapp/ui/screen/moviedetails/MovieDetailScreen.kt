package com.example.movieapp.ui.screen.moviedetails

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieapp.ui.screen.mylist.MyListViewModel

@Composable
fun MovieDetailScreen(
    id: Int,
    navController: NavController,
    viewModel: MovieDetailViewModel = viewModel(),
    myListViewModel: MyListViewModel
) {
    val movie = viewModel.movieDetails.collectAsState().value
    Log.d("MovieDetailScreen", "The movie details are: $movie")

    if (movie != null) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "Release Year: ${movie.releaseDate}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Rating: ${movie.voteAverage}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = movie.overview, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))

            // Like Button
            val isLiked = myListViewModel.isMovieLiked(movie)
            Button(onClick = { myListViewModel.toggleLike(movie) }) {
                Text(text = if (isLiked) "Unlike" else "Like")
            }
        }
    } else {
        Text(text = "Loading...", style = MaterialTheme.typography.bodyMedium)
    }
}