package com.example.movieapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.data.model.UserProfile
import com.example.movieapp.viewmodels.UserViewModel

@Composable
fun EditProfileScreen(
    userViewModel: UserViewModel = viewModel(),
    onSaveClick: () -> Unit
) {
    val userProfile = userViewModel.userProfile.collectAsState()

    userProfile.value?.let { profile ->
        var name by remember { mutableStateOf(profile.name) }
        var age by remember { mutableStateOf(profile.age.toString()) }
        var location by remember { mutableStateOf(profile.location) }
        var favoriteGenre by remember { mutableStateOf(profile.favoriteGenre) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Edit Profile", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Name") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text(text = "Age") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text(text = "Location") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = favoriteGenre,
                onValueChange = { favoriteGenre = it },
                label = { Text(text = "Favorite Genre") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val updatedProfile = UserProfile(
                    name = name,
                    age = age.toIntOrNull() ?: profile.age,
                    location = location,
                    favoriteGenre = favoriteGenre
                )
                userViewModel.saveUserProfile(updatedProfile)
                onSaveClick()
            }) {
                Text(text = "Save Changes")
            }
        }
    } ?: Text(text = "Loading...")
}
