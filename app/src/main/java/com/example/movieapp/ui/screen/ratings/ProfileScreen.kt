package com.example.movieapp.ui.screen.ratings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieapp.viewmodels.UserViewModel

@Composable
internal fun ProfileScreen(
    userViewModel: UserViewModel,
    ratingsViewModel: RatingsViewModel,
    onViewMoreRatingsClick: () -> Unit, // Callback for "View More Ratings" button
    onEditClick: () -> Unit // Callback for the "Edit Profile" button
) {
    val userProfile = userViewModel.userProfile.collectAsState() // * Collect user profile state
    val ratings = ratingsViewModel.ratings.collectAsState().value // * Collect ratings from RatingsViewModel

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        userProfile.value?.let { profile ->
            Text(text = "Name: ${profile.name}", style = MaterialTheme.typography.titleLarge) // Display user's name
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Age: ${profile.age}") // Display user's age
            Text(text = "Location: ${profile.location}") // Display user's location
            Text(text = "Favorite Genre: ${profile.favoriteGenre}") // Display user's favorite genre

            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onEditClick) { // Handle Edit Profile button
                Text(text = "Edit Profile")
            }
        } ?: Text(text = "Loading...") // Display loading message if user profile is null

        Spacer(modifier = Modifier.height(32.dp))

        // Ratings Section
        Text(
            text = "My Ratings",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        if (ratings.isEmpty()) {
            Text(
                text = "No ratings available", // Display this message if no ratings are available
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            // Show only the first 3 ratings
            val displayedRatings = ratings.take(3) // Limit to first 3 ratings

            displayedRatings.forEach { rating ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Movie Poster
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${rating.posterPath}", //
                        contentDescription = rating.title, // Provide movie title for content description
                        modifier = Modifier
                            .size(60.dp)
                            .padding(end = 8.dp)
                    )

                    // Movie Title and Rating
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = rating.title, // Display movie title
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "${rating.rating} ★", // * Display movie rating
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            // 'View More Ratings' Button
            if (ratings.size > 3) {
                Button(
                    onClick = onViewMoreRatingsClick, // Handle View More Ratings button
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp)
                ) {
                    Text("View All Ratings") // Button text
                }
            }
        }
    }
}
