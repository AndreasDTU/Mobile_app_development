package com.example.movieapp.ui.screen.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.data.model.Movie
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage


@Composable
fun MainScreen(navController: NavController, viewModel: MovieViewModel = viewModel()) {
    val popularMovies = viewModel.popularMovies.collectAsState().value
    val scaryMovies = viewModel.scaryMovies.collectAsState().value
    val funnyMovies = viewModel.funnyMovies.collectAsState().value

    // Use LazyColumn for vertical scrolling
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        // Trending Movies section
        Text(
            text = "Trending Movies",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )
        LazyRow {
            items(popularMovies) { movie ->
                MovieCard(navController = navController, movie = movie)
            }
        }

        // Scary Movies section
        Text(
            text = "Scary Movies",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )
        LazyRow {
            items(scaryMovies) { movie ->
                MovieCard(navController = navController, movie = movie)
            }
        }

        // Funny Movies section
        Text(
            text = "Funny Movies",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )
        LazyRow {
            items(funnyMovies) { movie ->
                MovieCard(navController = navController, movie = movie)
            }
        }
    }
}

@Composable
fun TopMovieCard(navController: NavController, movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp) // Adjust the height as needed
            .padding(16.dp)
            .clickable { navController.navigate("MovieDetailScreen/${movie.id}") }  // Navigate to MovieDetailScreen
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            val painter: Painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            Image(painter = painter, contentDescription = movie.title, modifier = Modifier.fillMaxSize())
            Text(text = movie.title, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Composable
fun MovieCard(navController: NavController, movie: Movie) {
    Card(
        modifier = Modifier
            .size(width = 150.dp, height = 250.dp) // Adjust size as needed
            .clickable {
                navController.navigate("MovieDetailScreen/${movie.id}") // Navigate to detail screen
            }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Movie poster
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier.fillMaxSize()
            )
            // Movie title
            Text(
                text = movie.title,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(8.dp),
                maxLines = 1
            )
        }
    }
}