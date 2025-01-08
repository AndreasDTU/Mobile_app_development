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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.viewmodels.UserViewModel
import com.example.movieapp.data.model.Rating


@Composable
internal fun ProfileScreen(
    userViewModel: UserViewModel = viewModel(),
    onEditClick: () -> Unit
) {
    val userProfile = userViewModel.userProfile.collectAsState()
    val userRatings = userViewModel.userRatings.collectAsState()
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
            Spacer (modifier = Modifier.height(24.dp))

            Text(
                text = "Your Ratings: ",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
                if (userRatings.value.isNotEmpty()) {
                    LazyColumn {
                        items(userRatings.value) { rating ->
                            RatingItem(rating = rating)
                        }
                    }
                } else {
                    Text(text = "You have not rated any movies yet.")
                }

        } ?: Text(text = "Loading...")
    }
}
@Composable
fun RatingItem (rating: Rating) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = rating.movieTitle, style = MaterialTheme.typography.bodyLarge)
        Text(text = "${rating.rating}/5", style = MaterialTheme.typography.bodyMedium)
    }
}