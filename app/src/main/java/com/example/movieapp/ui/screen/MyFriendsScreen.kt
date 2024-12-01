package com.example.movieapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.R

@Composable
fun MyFriendsScreen() {
    // Example list of friends with valid drawable resources¢
    val friends = listOf(
        Profile("Jessica", 204, 88, 96, R.drawable.profile_jessica),
        Profile("Nick", 983, 50, 432, R.drawable.profile_nick),
        Profile("Ashley", 1, 0, 0, R.drawable.profile_ashley, isNew = true),
        Profile("Katy", 200, 12, 204, R.drawable.profile_katy)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(friends) { friend ->
            FriendCard(friend)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun FriendCard(profile: Profile) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface // Use containerColor for the background
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            // Load profile picture safely
            val imagePainter = painterResource(id = profile.profilePictureResId)

            Image(
                painter = imagePainter,
                contentDescription = profile.name,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp)
            )
            // Display profile information
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = profile.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                if (profile.isNew) {
                    Text(
                        text = "New user!",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
            // Display profile stats
            Column(horizontalAlignment = Alignment.End) {
                Text("Watched: ${profile.watched}")
                Text("Lists: ${profile.lists}")
                Text("Likes: ${profile.likes}")
            }
        }
    }
}