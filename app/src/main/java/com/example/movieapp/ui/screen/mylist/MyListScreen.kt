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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieapp.data.model.Movie
import com.example.movieapp.ui.components.AppBackground
import com.example.movieapp.ui.theme.DarkPurple
import com.example.movieapp.ui.theme.TextWhite

@Composable
fun LikedMoviesCount(viewModel: MyListViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Liked Movies: ${viewModel.favorites.size}",
            style = MaterialTheme.typography.titleMedium,
            color = TextWhite
        )
    }
}

@Composable
fun MyList(
    navController: NavController,
    viewModel: MyListViewModel = viewModel(),
    isDarkTheme: Boolean
) {
    AppBackground(isDarkTheme = isDarkTheme) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Use the LikedMoviesCount composable
            LikedMoviesCount(viewModel = viewModel)

            // Conditionally display the grid or an empty state message
            if (viewModel.favorites.isNotEmpty()) {
                FavoritesGrid(
                    navController = navController,
                    movies = viewModel.favorites,
                    onLikeClicked = { movie ->
                        viewModel.toggleLike(movie)
                    },
                    isDarkTheme = isDarkTheme
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No liked movies yet!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextWhite
                    )
                }
            }
        }
    }
}

@Composable
fun FavoritesGrid(navController: NavController, movies: List<Movie>, onLikeClicked: (Movie) -> Unit, isDarkTheme: Boolean) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Two columns for a balanced look
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(4.dp), // Reduced padding
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(movies.size) { index ->
            val movie = movies[index]
            MovieCard(navController = navController, movie = movie, isDarkTheme = isDarkTheme)
        }
    }
}

@Composable
fun MovieCard(navController: NavController, movie: Movie, isDarkTheme: Boolean) {
    val cardColor = if (isDarkTheme) {
        DarkPurple
    } else {
        Color(0xFFFFC0CB) // Baby pink color
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.68f) // Slightly shorter cards for a more cinematic feel
            .clickable {
                navController.navigate("MovieDetailScreen/${movie.id}")
            },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(4.dp) // Add subtle elevation for better contrast
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxHeight(0.85f)
                    .clip(MaterialTheme.shapes.medium) // Clip the image for rounded corners
            )
            Text(
                text = movie.title,
                style = MaterialTheme.typography.labelMedium,
                color = TextWhite,
                modifier = Modifier
                    .padding(6.dp)
                    .align(Alignment.CenterHorizontally),
                maxLines = 1
            )
        }
    }
}