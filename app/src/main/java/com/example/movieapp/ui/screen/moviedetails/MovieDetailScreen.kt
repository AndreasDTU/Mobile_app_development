package com.example.movieapp.ui.screen.moviedetails

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color

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
        // Wrap the content with a LazyColumn to enable scrolling
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()) // Make the screen scrollable
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))

// Movie Title
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

// Like Button under the title
            val isLiked = myListViewModel.isMovieLiked(movie)
            IconButton(
                onClick = { myListViewModel.toggleLike(movie) },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(48.dp) // Adjust the size as needed
            ) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (isLiked) "Unlike" else "Like",
                    tint = if (isLiked) MaterialTheme.colorScheme.primary else Color.Gray,
                    modifier = Modifier.size(32.dp) // Adjust the icon size
                )
            }

// Movie Description
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = movie.overview, // Ensure this value is not null or empty
                style = MaterialTheme.typography.bodyMedium
            )
        }
    } else {
        // Display loading state or message
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Loading...", style = MaterialTheme.typography.bodyMedium)
        }
    }
}