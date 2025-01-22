package com.example.movieapp.ui.screen.ratings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.movieapp.ui.components.AppBackground
import com.example.movieapp.viewmodels.UserViewModel

@Composable
fun EditProfileScreen(
    userViewModel: UserViewModel,
    onSaveClick: () -> Unit,
    isDarkTheme: Boolean
) {
    val userProfile = userViewModel.userProfile.collectAsState()

    var name by remember { mutableStateOf(userProfile.value?.name ?: "") }
    var age by remember { mutableStateOf(userProfile.value?.age?.toString() ?: "") }
    var location by remember { mutableStateOf(userProfile.value?.location ?: "") }
    var favoriteGenre by remember { mutableStateOf(userProfile.value?.favoriteGenre ?: "") }

    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Edit Profile",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 24.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Age") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = favoriteGenre,
                onValueChange = { favoriteGenre = it },
                label = { Text("Favorite Genre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    userViewModel.updateUserProfile(
                        name,
                        age.toIntOrNull() ?: 0,
                        location,
                        favoriteGenre
                    )
                    onSaveClick() // Notify the ProfileScreen to stop editing
                }
            ) {
                Text(text = "Save")
            }
        }
    }
}