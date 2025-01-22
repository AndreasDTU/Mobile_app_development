package com.example.movieapp.ui.screen.mylist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieapp.data.model.Movie
import com.example.movieapp.ui.components.AppBackground
import com.example.movieapp.ui.theme.DarkPurple
import com.example.movieapp.ui.theme.TextWhite
import kotlinx.coroutines.delay

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
    viewModel: MyListViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
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
                    onLikeClicked = { movie -> viewModel.toggleLike(movie) },
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
fun FavoritesGrid(
    navController: NavController,
    movies: List<Movie>,
    onLikeClicked: (Movie) -> Unit,
    isDarkTheme: Boolean
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(movies, key = { it.id }) { movie ->
            SwipeableMovieCard(
                navController = navController,
                movie = movie,
                onSwipeToUnlike = { onLikeClicked(movie) },
                isDarkTheme = isDarkTheme
            )
        }
    }
}

@Composable
fun SwipeableMovieCard(
    navController: NavController,
    movie: Movie,
    onSwipeToUnlike: () -> Unit,
    isDarkTheme: Boolean
) {
    var swipeOffset by remember { mutableStateOf(0f) }
    var isDeleted by remember { mutableStateOf(false) }

    // Animate the swipe offset to create a smooth transition
    val animatedSwipeOffset by animateFloatAsState(
        targetValue = if (isDeleted) -500f else swipeOffset,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.68f)
            .padding(4.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    // Update swipeOffset as the dragAmount increases
                    swipeOffset = (swipeOffset + dragAmount).coerceIn(-300f, 0f) // Limit the swipe range

                    // If the swipe reaches the threshold, mark the item as deleted
                    if (swipeOffset <= -300f && !isDeleted) {
                        isDeleted = true
                        onSwipeToUnlike() // Trigger the item removal logic
                    }
                }
            }
    ) {
        // Movie card content
        MovieCard(navController = navController, movie = movie, isDarkTheme = isDarkTheme)

        // Smooth swipe animation with a cleaner look
        AnimatedVisibility(
            visible = animatedSwipeOffset < 0f, // Show this when swiping
            modifier = Modifier
                .fillMaxHeight()
                .width(100.dp) // Increase width for better visibility
                .align(Alignment.CenterEnd)
                .offset(x = (animatedSwipeOffset * 0.3f).dp) // Adjust the swipe offset
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFFFF4D4D), Color(0xFFFF6A6A)) // Gradient red background
                        )
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                // Add a trash icon for delete action
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // If the swipe goes beyond the threshold, remove the movie card
        if (isDeleted) {
            LaunchedEffect(Unit) {
                delay(300) // Wait for the animation to finish
                // Trigger the movie removal from the list here
            }
        }
    }

    // Reset the swipe position if it wasn't enough to delete
    if (!isDeleted && swipeOffset == 0f) {
        swipeOffset = 0f // Reset to the original position
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