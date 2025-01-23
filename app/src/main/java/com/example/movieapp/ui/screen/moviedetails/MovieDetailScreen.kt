package com.example.movieapp.ui.screen.moviedetails

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieapp.ui.components.AppBackground
import com.example.movieapp.ui.screen.mylist.MyListViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
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
    ratingsViewModel: RatingsViewModel,
    isDarkTheme: Boolean
) {
    val movie = viewModel.movieDetails.collectAsState().value
    val context = LocalContext.current

    val rating = ratingsViewModel.getRatingForMovie(id)
    val userRating = rating?.rating ?: 0f
    val averageRating by ratingsViewModel.averageRating.collectAsState() // Observe average rating


    var updatedRating by remember { mutableStateOf(userRating) }
    val coroutineScope = rememberCoroutineScope()

    // Load average rating on screen load
    LaunchedEffect(id) {
        ratingsViewModel.loadAverageRating(id) // Load average rating when screen opens
    }

    AppBackground (isDarkTheme = isDarkTheme) {
        if (movie != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Movie Poster
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${movie.posterPath ?: ""}",
                    contentDescription = movie.title ?: "Movie Poster",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.7f)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Movie Title
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )

                // Display Average Rating
                Text(
                    text = "Average Rating: ${averageRating?.let { String.format("%.1f", it) } ?: "Loading..."} ★",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )



                // Like Button under the title

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(14.dp))

                    val isLiked = myListViewModel.isMovieLiked(movie)
                    IconButton(
                        onClick = { myListViewModel.toggleLike(movie) },
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .size(48.dp) // Adjust the size as needed
                            .size(48.dp)
                    ) {
                        Icon(
                            imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if (isLiked) "Unlike" else "Like",
                            modifier = Modifier.size(33.dp), // Adjust the icon s
                            tint = if (isLiked) LightPurple else Color.Gray,

                            )
                    }

                    // Watch Trailer Button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 45.dp), // Align with Like button spacing
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    val trailerKey =
                                        viewModel.getMovieTrailer(id) // Fetch trailer key
                                    if (trailerKey != null) {
                                        val youtubeUrl =
                                            "https://www.youtube.com/watch?v=$trailerKey"
                                        val intent =
                                            Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
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
                }

                // Genres
                if (!movie.genres.isNullOrEmpty()) {
                    Text(
                        text = "Genres: ${movie.genres.joinToString { it.name }}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                // Runtime
                Text(
                    text = "Runtime: ${movie.runtime ?: "N/A"} minutes",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                // Status
                if (!movie.status.isNullOrEmpty()) {
                    Text(
                        text = "Status: ${movie.status}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                // Overview
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = movie.overview ?: "No Overview Available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                // Cast
                if (!movie.cast.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Cast:",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Remember state for the expanded list
                    var isExpanded by remember { mutableStateOf(false) }

                    Column {
                        val visibleCast = if (isExpanded) movie.cast else movie.cast.take(4)

                        visibleCast.forEach { castMember ->
                            Text(
                                text = "${castMember.name} as ${castMember.character ?: "Unknown"}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Expand/Collapse Button
                        Text(
                            text = if (isExpanded) "Show Less" else "Show More",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .clickable { isExpanded = !isExpanded }
                                .padding(vertical = 4.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Rating Section
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
                                            movie.title ?: "Unknown",
                                            movie.posterPath ?: "",
                                            updatedRating
                                        )
                                        Toast.makeText(context, "Rating saved!", Toast.LENGTH_SHORT)
                                            .show()
                                    },
                                tint = if (star <= updatedRating) LightPurple else Color.Gray
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
                                tint = (LightPurple),
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            ratingsViewModel.removeRating(id)
                            updatedRating = 0f // Reset the local rating
                            Toast.makeText(context, "Rating removed!", Toast.LENGTH_SHORT).show()
                            ratingsViewModel.loadRatings() // Reload ratings list to update UI

                            // Refresh the average rating right away after removing it
                            ratingsViewModel.loadAverageRating(id)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Remove Rating", color = TextWhite)
                    }
                }
            }

        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "Loading...", style = MaterialTheme.typography.bodyMedium)
            }

        }
    }
}