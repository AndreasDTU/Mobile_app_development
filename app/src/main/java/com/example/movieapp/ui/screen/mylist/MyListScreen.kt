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
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
            // Display movie count
            LikedMoviesCount(viewModel = viewModel)

            // Display grid or fallback to empty state
            if (viewModel.favorites.isNotEmpty()) {
                FavoritesGrid(
                    navController = navController,
                    movies = viewModel.favorites,
                    onLikeClicked = { movie -> viewModel.toggleLike(movie) },
                    isDarkTheme = isDarkTheme
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
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
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
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

    // Smooth swipe animation
    val animatedSwipeOffset by animateFloatAsState(
        targetValue = if (isDeleted) -500f else swipeOffset,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.68f)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    swipeOffset = (swipeOffset + dragAmount).coerceIn(-500f, 0f)
                    if (swipeOffset <= -300f && !isDeleted) {
                        isDeleted = true
                        onSwipeToUnlike()
                    }
                }
            }
    ) {
        if (!isDeleted) {
            MovieCard(navController, movie, isDarkTheme)
        }

        // Trash icon with gradient background
        AnimatedVisibility(
            visible = swipeOffset < 0f,
            modifier = Modifier
                .fillMaxHeight()
                .width(100.dp)
                .align(Alignment.CenterEnd)
                .offset(x = (animatedSwipeOffset * 0.3f).dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFFFF4D4D), Color(0xFFFF6A6A))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun MovieCard(navController: NavController, movie: Movie, isDarkTheme: Boolean) {
    val cardColor = if (isDarkTheme) DarkPurple else Color(0xFFFFC0CB)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.68f)
            .clickable { navController.navigate("MovieDetailScreen/${movie.id}") },
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(4.dp)
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
                    .clip(MaterialTheme.shapes.medium)
            )
            Text(
                text = movie.title,
                style = MaterialTheme.typography.labelMedium,
                color = TextWhite,
                modifier = Modifier.padding(6.dp),
                maxLines = 1
            )
        }
    }
}