package com.example.movieapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.viewmodels.UserViewModel

@Composable
fun ProfileScreen(
    userViewModel: UserViewModel = viewModel(),
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
    }
}
