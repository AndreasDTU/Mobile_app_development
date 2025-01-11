package com.example.movieapp.ui.screen.ratings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.data.model.Rating
import com.example.movieapp.viewmodels.UserViewModel

@Composable
internal fun ProfileScreen(
    userViewModel: UserViewModel = viewModel(),
    ratings: List<Rating>,
    onViewRatingsClick: () -> Unit,
    onEditClick: () -> Unit
) {
    val userProfile = userViewModel.userProfile.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        userProfile.value?.let { profile ->
            Text(text = "Name: ${profile.name}", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Age: ${profile.age}")
            Text(text = "Location: ${profile.location}")
            Text(text = "Favorite Genre: ${profile.favoriteGenre}")

            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onEditClick) {
                Text(text = "Edit Profile")
            }
        } ?: Text(text = "Loading...")

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "My Ratings",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (ratings.isEmpty()) {
            Text(text = "No ratings available.")
        } else {
            LazyColumn {
                items(ratings) { rating ->
                    Text(text = "Movie ID: ${rating.movieId} - Rating: ${rating.rating} ★")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onViewRatingsClick) {
            Text(text = "View All Ratings")
        }
    }
}

