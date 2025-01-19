package com.example.movieapp.ui.screen.redundant

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.data.model.UserProfile
import com.example.movieapp.ui.components.AppBackground
import com.example.movieapp.ui.theme.MovieappTheme
import com.example.movieapp.viewmodels.UserViewModel

@Composable
fun EditProfileScreen(
    userViewModel: UserViewModel = UserViewModel(),
    onSaveClick: () -> Unit,
    isDarkTheme: Boolean
) {
    val userProfile = userViewModel.userProfile.collectAsState()

    // Wrap the screen in the AppBackground composable
    AppBackground(isDarkTheme = isDarkTheme) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Sleek Top Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            ) {
                Text(
                    text = "Edit Profile",
                    color = MaterialTheme.colorScheme.onPrimary, // White text
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Profile Content
            userProfile.value?.let { profile ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Name: ${profile.name}",
                        color = MaterialTheme.colorScheme.onPrimary, // White text
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Age: ${profile.age}",
                        color = MaterialTheme.colorScheme.onPrimary, // White text
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Location: ${profile.location}",
                        color = MaterialTheme.colorScheme.onPrimary, // White text
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = onSaveClick,
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = "Save",
                            color = MaterialTheme.colorScheme.onPrimary // White text on button
                        )
                    }
                }
            } ?: Text(
                text = "Loading...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary, // White text
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    MovieappTheme {
        EditProfileScreen(onSaveClick = {}, isDarkTheme = true)
    }
}
