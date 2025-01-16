package com.example.movieapp.ui.screen.moviedetails

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieapp.ui.components.AppBackground
import com.example.movieapp.ui.screen.mylist.MyListViewModel
import com.example.movieapp.ui.screen.ratings.RatingsViewModel
import com.example.movieapp.ui.theme.LightPurple
import com.example.movieapp.ui.theme.TextWhite
import kotlinx.coroutines.launch

@Composable
fun MovieDetailScreen(
    id: Int,
    navController: NavController,
    viewModel: MovieDetailViewModel = viewModel(),
    myListViewModel: MyListViewModel,
    ratingsViewModel: RatingsViewModel
) {
    val movie = viewModel.movieDetails.collectAsState().value
    val context = LocalContext.current

    // Retrieve the user's rating for the movie
    val rating = ratingsViewModel.getRatingForMovie(id)
    val userRating = rating?.rating ?: 0f

    // Mutable state to track the updated rating
    var updatedRating by remember { mutableStateOf(userRating) }
    val coroutineScope = rememberCoroutineScope()

    AppBackground {
        if (movie != null) {
            // Content Layout
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Movie Poster
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.7f)
                )

                // Buttons Row: Centered Under the Poster
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Like Button
                    val isLiked = myListViewModel.isMovieLiked(movie)
                    IconButton(
                        onClick = { myListViewModel.toggleLike(movie) },
                        modifier = Modifier
                            .padding(end = 16.dp) // Space between buttons
                            .size(48.dp)
                    ) {
                        Icon(
                            imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if (isLiked) "Unlike" else "Like",
                            tint = if (isLiked) MaterialTheme.colorScheme.primary else Color.Gray,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    // Watch Trailer Button
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                val trailerKey = viewModel.getMovieTrailer(id) // Fetch trailer key
                                if (trailerKey != null) {
                                    val youtubeUrl = "https://www.youtube.com/watch?v=$trailerKey"
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
                                    context.startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Trailer not available",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        },
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LightPurple,
                            contentColor = TextWhite
                        ),
                        border = BorderStroke(2.dp, Color.Black) // Black outline
                    ) {
                        Text("Watch Trailer")
                    }
                }

                // Movie Title
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )

                // Movie Description
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                // Rating Section
                Spacer(modifier = Modifier.height(16.dp))
                if (updatedRating == 0f) {
                    Text(
                        text = "Rate this movie:",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        (1..5).forEach { star ->
                            Icon(
                                imageVector = if (star <= updatedRating) Icons.Filled.Star else Icons.Outlined.Star,
                                contentDescription = "$star Star",
                                modifier = Modifier
                                    .size(40.dp)
                                    .clickable {
                                        updatedRating = star.toFloat()
                                        ratingsViewModel.addRating(
                                            id,
                                            movie.title,
                                            movie.posterPath ?: "",
                                            updatedRating
                                        )
                                        Toast.makeText(context, "Rating saved!", Toast.LENGTH_SHORT)
                                            .show()
                                    },
                                tint = if (star <= updatedRating) MaterialTheme.colorScheme.primary else Color.Gray
                            )
                        }
                    }
                } else {
                    Text(
                        text = "You have rated this film:",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(top = 8.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        (1..updatedRating.toInt()).forEach { _ ->
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Rated Star",
                                modifier = Modifier.size(40.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        } else {
            // Loading State
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "Loading...", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
