package com.example.movieapp.ui.screen.moviedetails

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieapp.ui.screen.mylist.MyListViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import com.example.movieapp.repositories.RatingsRepository
import com.example.movieapp.ui.components.AppBackground
import com.example.movieapp.ui.theme.LightPurple
import com.example.movieapp.ui.theme.TextWhite

@Composable
fun MovieDetailScreen(
    id: Int,
    navController: NavController,
    viewModel: MovieDetailViewModel = viewModel(),
    myListViewModel: MyListViewModel,
    ratingsRepository: RatingsRepository

) {
    val movie = viewModel.movieDetails.collectAsState().value
    val userRating = remember { mutableStateOf(ratingsRepository.getRatingForMovie(id) ?: 0f) }
    val context = LocalContext.current

    Log.d("MovieDetailScreen", "The movie details are: $movie")


    AppBackground {
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

                Spacer(modifier = Modifier.height(16.dp))


                // Rating Section
                Text(
                    text = "Rate this movie",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Star Rating Row
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    (1..5).forEach { index ->
                        Icon(
                            imageVector = if (index <= userRating.value.toInt()) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "$index Star",
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { userRating.value = index.toFloat() }, // *
                            tint = if (index <= userRating.value.toInt()) MaterialTheme.colorScheme.primary else Color.Gray // *
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Display User Rating
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "You have rated this film:",
                    style = MaterialTheme.typography.bodyMedium
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    (1..userRating.value.toInt()).forEach { _ ->
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Rated Star",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    ratingsRepository.addRating(id, userRating.value)
                    Toast.makeText(context, "Rating saved!", Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = "Save Rating")
                }
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
}

