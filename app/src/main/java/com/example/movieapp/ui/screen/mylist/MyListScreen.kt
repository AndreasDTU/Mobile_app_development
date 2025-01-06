package com.example.movieapp.ui.screen.mylist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.movieapp.data.model.Movie

@Composable
fun MyList(viewModel: MyListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
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

        // sections
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                SectionTitle("My List")
                MovieRow(myList, onLikeClicked = { movie -> viewModel.toggleLike(movie) })
            }
            item {
                SectionTitle("Recently Watched")
                MovieRow(recentlyWatched, onLikeClicked = { movie -> viewModel.toggleLike(movie) })
            }
            item {
                SectionTitle("Favorites")
                MovieRow(favorites, onLikeClicked = { movie -> viewModel.toggleLike(movie) })
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
fun MovieRow(movies: List<Movie>?, onLikeClicked: (Movie) -> Unit) {
    println(movies) // Debugging: print the movies list to the log

    LazyRow {
        items(movies.orEmpty()) { movie ->
            MovieCard(movie = movie, onLikeClicked = onLikeClicked)
        }
    }
}


@Composable
fun MovieCard(movie: Movie, onLikeClicked: (Movie) -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(180.dp)
        ) {
            // Load poster image (e.g., using Coil)
        }
        androidx.compose.material3.Icon(
            imageVector = if (movie.isLiked)
                androidx.compose.material.icons.Icons.Filled.Favorite
            else
                androidx.compose.material.icons.Icons.Default.FavoriteBorder,
            contentDescription = "Like",
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .padding(top = 4.dp)
                .align(Alignment.CenterHorizontally)
                .clickable { onLikeClicked(movie) }
        )
    }
}
