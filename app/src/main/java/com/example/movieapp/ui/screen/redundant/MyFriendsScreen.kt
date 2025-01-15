package com.example.movieapp.ui.screen.redundant

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class Friend(
    val name: String,
    val watched: Int,
    val lists: Int,
    val likes: Int,
    val profilePicture: Int, // Replace with actual drawable resource IDs
    val isNew: Boolean = false
)
@Preview(showBackground = true)
@Composable
fun MyFriendsScreen() {
    val friends = listOf(
        Friend("Jessica", 204, 88, 96,1),
        Friend("Nick", 983, 50, 432, 1),
        Friend("Ashley", 1, 0, 0, 1, isNew = true),
        Friend("Katy", 200, 12, 204, 1)
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
fun FriendCard(friend: Friend) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(Color.DarkGray)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            // Profile Picture
            /* Image(
                painter = painterResource(id = friend.profilePicture),
                contentDescription = friend.name,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp)
            )
             */
            // Friend Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = friend.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                if (friend.isNew) {
                    Text(
                        text = "New user!",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
            // Stats (Watched, Lists, Likes)
            Column(horizontalAlignment = Alignment.End) {
                Text("Watched: ${friend.watched}")
                Text("Lists: ${friend.lists}")
                Text("Likes: ${friend.likes}")
            }
        }
    }
}
