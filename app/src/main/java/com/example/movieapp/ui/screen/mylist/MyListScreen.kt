package com.example.movieapp.ui.screen.mylist

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieapp.data.model.Movie
import com.example.movieapp.ui.components.AppBackground

@Composable
fun MyList(
    navController: NavController,
    viewModel: MyListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // LazyColumn to display all movie sections without headers
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    MovieRow(
                        navController = navController,
                        movies = viewModel.myList,
                        onLikeClicked = { movie -> viewModel.toggleLike(movie) }
                    )
                }
                item {
                    MovieRow(
                        navController = navController,
                        movies = viewModel.recentlyWatched,
                        onLikeClicked = { movie -> viewModel.toggleLike(movie) }
                    )
                }
                item {
                    MovieRow(
                        navController = navController,
                        movies = viewModel.favorites,
                        onLikeClicked = { movie -> viewModel.toggleLike(movie) }
                    )
                }
            }
        }
    }
}

@Composable
fun MovieRow(navController: NavController, movies: List<Movie>?, onLikeClicked: (Movie) -> Unit) {
    LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
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
            .padding(8.dp)
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