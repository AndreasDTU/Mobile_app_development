package com.example.movieapp.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.data.model.Movie
import com.example.movieapp.repositories.MovieRepository
import com.example.movieapp.viewmodels.MovieDetailViewModel
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.movieapp.nav.simplenav
import com.example.movieapp.viewmodels.MovieViewModel

@Composable
fun MovieDetailScreen(id: Int, navController: NavController, viewModel: MovieDetailViewModel = viewModel()) {

    val movie = viewModel.movieDetails.collectAsState().value
    Log.d("MovieDetailScreen", "The movie details are: $movie")


    // Display movie details if available
    if (movie != null) {
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterpath}",
                contentDescription = movie.title,
                modifier = Modifier.fillMaxWidth().aspectRatio(0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "Release Year: ${movie.releasedate}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Rating: ${movie.voteaverage}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = movie.overview, style = MaterialTheme.typography.bodyMedium)
        }
    } else {
        // Display loading state or message
        Text(text = "Loading...", style = MaterialTheme.typography.bodyMedium)
    }
}
