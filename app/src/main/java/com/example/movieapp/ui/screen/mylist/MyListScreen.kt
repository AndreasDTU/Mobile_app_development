package com.example.movieapp.ui.screen.mylist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.movieapp.ui.theme.DarkPurple
import com.example.movieapp.ui.theme.TextWhite

@Composable
fun MyList(
    navController: NavController,
    viewModel: MyListViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    isDarkTheme: Boolean
) {
    AppBackground(isDarkTheme = isDarkTheme) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Favorites section rendered as a grid
            FavoritesGrid(
                navController = navController,
                movies = viewModel.favorites,
                onLikeClicked = { movie -> viewModel.toggleLike(movie) }
            )
        }
    }
}

@Composable
fun FavoritesGrid(navController: NavController, movies: List<Movie>, onLikeClicked: (Movie) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies.size) { index ->
            val movie = movies[index]
            // Use the updated MovieCard design from MainScreen
            MovieCard(navController = navController, movie = movie)
        }
    }
}

@Composable
fun MovieCard(navController: NavController, movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.75f) // Match the same aspect ratio as MainScreen
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
                modifier = Modifier.fillMaxHeight(0.85f) // Adjust height to match MainScreen
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