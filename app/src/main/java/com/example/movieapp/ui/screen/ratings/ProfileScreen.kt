package com.example.movieapp.ui.screen.ratings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.movieapp.data.model.UserProfile
import com.example.movieapp.viewmodels.UserViewModel

@Composable
internal fun ProfileScreen(
    userViewModel: UserViewModel,
    ratingsViewModel: RatingsViewModel,
    onViewMoreRatingsClick: () -> Unit,
    onEditClick: () -> Unit // Navigate to EditProfileScreen
) {
    val userProfile = userViewModel.userProfile.collectAsState().value // Observe user profile state
    val ratings = ratingsViewModel.ratings.collectAsState().value // Observe ratings state

    // Display profile details
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Show a default profile if the userProfile is null (still loading)
        val profile = userProfile ?: UserProfile(
            name = "Loading...",
            age = 0,
            location = "Loading...",
            favoriteGenre = "Loading..."
        )

        // Display user profile details
        Text(text = "Name: ${profile.name}", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Age: ${if (profile.age == 0) "Loading..." else profile.age.toString()}")
        Text(text = "Location: ${profile.location}")
        Text(text = "Favorite Genre: ${profile.favoriteGenre}")

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onEditClick) { // Use `onEditClick` to navigate to EditProfileScreen
            Text(text = "Edit Profile")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Ratings Section
        Text(
            text = "My Ratings",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        if (ratings.isEmpty()) {
            Text(
                text = "No ratings available",
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            val displayedRatings = ratings.take(3) // Show top 3 ratings

            LazyColumn {
                items(displayedRatings) { rating ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w500${rating.posterPath}",
                            contentDescription = rating.title,
                            modifier = Modifier
                                .size(60.dp)
                                .padding(end = 8.dp)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = rating.title,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = "${rating.rating} ★",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }

            if (ratings.size > 3) {
                Button(
                    onClick = onViewMoreRatingsClick, // Use `onViewMoreRatingsClick` to navigate
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp)
                ) {
                    Text("View All Ratings")
                }
            }
        }
    }
}