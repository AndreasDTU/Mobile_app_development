package com.example.movieapp.ui.screen.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.movieapp.ui.theme.DarkPurple
import com.example.movieapp.ui.theme.LightPurple
import com.example.movieapp.ui.theme.MediumPurple
import com.example.movieapp.ui.theme.MovieappTheme
import com.example.movieapp.ui.theme.TextWhite


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import android.util.Log
import androidx.compose.ui.draw.drawWithContent

import com.example.movieapp.data.model.Movie
import com.example.movieapp.ui.components.AppBackground
import com.example.movieapp.ui.theme.LightPurple
import com.example.movieapp.ui.theme.TextWhite

@Composable
fun MainScreen(navController: NavController, viewModel: MovieViewModel = viewModel()) {
    val topMovie = viewModel.topMovie.collectAsState().value // Observing topMovie
    val popularMovies = viewModel.popularMovies.collectAsState().value
    val scaryMovies = viewModel.scaryMovies.collectAsState().value
    val funnyMovies = viewModel.funnyMovies.collectAsState().value
    val actionMovies = viewModel.actionMovies.collectAsState().value
    val dramaMovies = viewModel.dramaMovies.collectAsState().value
    val adventureMovies = viewModel.adventureMovies.collectAsState().value
    val romanceMovies = viewModel.romanceMovies.collectAsState().value
    val sciFiMovies = viewModel.sciFiMovies.collectAsState().value


    // Use LazyColumn for vertical scrolling
    AppBackground {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            // Top Movie Section
            if (topMovie != null) {
                TopMovieCard(navController = navController, movie = topMovie)
            } else {
                Text(
                    text = "No Featured Movie Available",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            // Trending Movies Section
            SectionTitle("Trending Movies")
            MovieRow(navController = navController, movies = popularMovies)

            // Scary Movies Section
            SectionTitle("Scary Movies")
            MovieRow(navController = navController, movies = scaryMovies)

            // Funny Movies Section
            SectionTitle("Funny Movies")
            MovieRow(navController = navController, movies = funnyMovies)

            // Action Movies Section
            SectionTitle("Action Movies")
            MovieRow(navController = navController, movies = actionMovies)

            // Drama Movies Section
            SectionTitle("Drama Movies")
            MovieRow(navController = navController, movies = dramaMovies)

            // Adventure Movies Section
            SectionTitle("Adventure Movies")
            MovieRow(navController = navController, movies = adventureMovies)

            // Romance Movies Section
            SectionTitle("Romance Movies")
            MovieRow(navController = navController, movies = romanceMovies)

            // Science Fiction Movies Section
            SectionTitle("Science Fiction Movies")
            MovieRow(navController = navController, movies = sciFiMovies)

    }
        }
}

// SectionTitle, TopMovieCard, and MovieRow remain unchanged.


@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(8.dp)
    )
}

fun Modifier.gradientOverlay(brush: Brush): Modifier = this.then(
    Modifier.drawWithContent {
        drawContent()
        drawRect(brush = brush)
    }
)

@Composable
fun TopMovieCard(navController: NavController, movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .padding(16.dp)
            .clickable {
                navController.navigate("MovieDetailScreen/${movie.id}")
            },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = DarkPurple)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(DarkPurple.copy(alpha = 0.85f))
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextWhite,
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.CenterStart)
                )
            }
        }
    }
}

@Composable
fun MovieRow(navController: NavController, movies: List<Movie>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movies) { movie ->
            MovieCard(navController = navController, movie = movie)
        }
    }
}

@Composable
fun MovieCard(navController: NavController, movie: Movie) {
    Card(
        modifier = Modifier
            .size(width = 160.dp, height = 240.dp)
            .clickable {
                navController.navigate("MovieDetailScreen/${movie.id}")
            },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = DarkPurple)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier.fillMaxHeight(0.85f)
            )
            Text(
                text = movie.title,
                style = MaterialTheme.typography.labelMedium,
                color = TextWhite,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MovieappTheme {
        MainScreen(navController = rememberNavController())
    }
}
