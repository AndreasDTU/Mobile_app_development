package com.example.movieapp.ui.screen.mylist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieapp.data.model.Movie

@Composable
fun MyList(
    navController: NavController,
    viewModel: MyListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val myList = viewModel.myList
    val recentlyWatched = viewModel.recentlyWatched
    val favorites = viewModel.favorites

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFB6C1))
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = "My List",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Sections
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                SectionTitle("My List")
                MovieRow(
                    navController = navController,
                    movies = myList,
                    onLikeClicked = { movie -> viewModel.toggleLike(movie) })
            }
            item {
                SectionTitle("Recently Watched")
                MovieRow(
                    navController = navController,
                    movies = recentlyWatched,
                    onLikeClicked = { movie -> viewModel.toggleLike(movie) })
            }
            item {
                SectionTitle("Favorites")
                MovieRow(
                    navController = navController,
                    movies = favorites,
                    onLikeClicked = { movie -> viewModel.toggleLike(movie) })
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
fun MovieRow(navController: NavController, movies: List<Movie>?, onLikeClicked: (Movie) -> Unit) {
    LazyRow {
        items(movies.orEmpty()) { movie ->
            MovieCard(navController = navController, movie = movie)
        }
    }
}

@Composable
fun MovieCard(navController: NavController, movie: Movie) {
    Card(
        modifier = Modifier
            .size(width = 150.dp, height = 250.dp)
            .clickable {
                navController.navigate("MovieDetailScreen/${movie.id}")
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