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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import com.example.movieapp.repositories.RatingsRepository
import com.example.movieapp.ui.components.AppBackground
import com.example.movieapp.ui.screen.ratings.RatingsViewModel
import com.example.movieapp.ui.theme.LightPurple
import com.example.movieapp.ui.theme.TextWhite

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
    val averageRating by ratingsViewModel.averageRating.collectAsState() // Observe average rating


    // Mutable state to track the updated rating
    var updatedRating by remember { mutableStateOf(userRating) }

    // Load average rating on screen load
    LaunchedEffect(id) {
        ratingsViewModel.loadAverageRating(id) // Load average rating when screen opens
    }
    AppBackground {
        if (movie != null) {
            // Wrap the content with a LazyColumn to enable scrolling
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()) // Make the screen scrollable
            ) {
                // Movie Poster
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
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                // Display Average Rating
                Text(
                    text = "Average Rating: ${averageRating ?: "Loading..."} ★",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(bottom = 16.dp)
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
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )


                Spacer(modifier = Modifier.height(16.dp))


                // Rating Section
                if (updatedRating == 0f) {
                    Text(text = "Rate this movie:", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimary)
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
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            ratingsViewModel.removeRating(id)
                            updatedRating = 0f // Reset the local rating
                            Toast.makeText(context, "Rating removed!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Remove Rating", color = TextWhite)
                    }
                }
            }
        } else {
            // Display loading state or message
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "Loading...", style = MaterialTheme.typography.bodyMedium)
            }

        }
    }
}