package com.example.movieapp.ui.screen.ratings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieapp.viewmodels.UserViewModel

@Composable
internal fun ProfileScreen(
    userViewModel: UserViewModel,
    ratingsViewModel: RatingsViewModel,
    onViewMoreRatingsClick: () -> Unit,
    onEditClick: () -> Unit, // Passing onEditClick to navigate to EditProfileScreen
    isDarkTheme: Boolean
) {
    val userProfile = userViewModel.userProfile.collectAsState().value // Observe user profile state
    val ratings = ratingsViewModel.ratings.collectAsState().value // Observe ratings state

    var isEditing by remember { mutableStateOf(false) } // Track whether we're in editing mode

    if (isEditing) {
        // Navigate to EditProfileScreen when editing
        EditProfileScreen(
            userViewModel = userViewModel,
            onSaveClick = {
                // After saving, switch back to viewing profile mode
                isEditing = false
            },
            isDarkTheme = isDarkTheme
        )
    } else {
        // Display profile details if not editing
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            userProfile?.let { profile ->
                Text(text = "Name: ${profile.name}", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Age: ${profile.age}")
                Text(text = "Location: ${profile.location}")
                Text(text = "Favorite Genre: ${profile.favoriteGenre}")

                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = { isEditing = true }) {
                    Text(text = "Edit Profile")
                }
            } ?: Text(text = "Loading...")

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
                val displayedRatings = ratings.take(3)

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
                        onClick = onViewMoreRatingsClick,
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
}
