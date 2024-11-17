package com.example.movieapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter



@Composable
fun ReviewPageUI() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        // Header Section
        HeaderSection()

        // Reviewed Movies Section
        SectionTitle("Reviewed movies")
        MovieCarousel()

        // Friend's Review Section
        Spacer(modifier = Modifier.height(16.dp))
        FriendReviewSection()

        // Friends 5 Star Movies Section
        Spacer(modifier = Modifier.height(16.dp))
        SectionTitle("Friends 5 star movies:")
        MovieCarousel()

        // Bottom Navigation Placeholder
        Spacer(modifier = Modifier.weight(1f)) // Push navigation to the bottom
        BottomNavigationPlaceholder()
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFEFB7F4)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // User Profile Icon
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color.LightGray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "J", fontSize = 24.sp, color = Color.Black)
        }

        // Username
        Text(
            text = "Johny",
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 18.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.weight(1f))

        // Search Icon
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            modifier = Modifier.size(24.dp),
            tint = Color.Black
        )
    }
}

@Composable
fun ReviewSectionTitle(title: String) {
    Text(
        text = title,
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun MovieCarousel() {
    LazyRow(
        modifier = Modifier.padding(start = 16.dp, top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(5) {
            MoviePoster()
        }
    }
}

@Composable
fun MoviePoster() {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(160.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter("https://via.placeholder.com/100x150"),
                contentDescription = "Movie Poster",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            )
        }
    }
}

@Composable
fun FriendReviewSection() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Color.LightGray, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "N", fontSize = 20.sp, color = Color.Black)
            }

            Text(
                text = "Nick",
                modifier = Modifier.padding(start = 8.dp),
                fontSize = 16.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "This is what Nick had to say:",
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.LightGray)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Row(verticalAlignment = Alignment.Top) {
                    Image(
                        painter = rememberAsyncImagePainter("https://via.placeholder.com/100x150"),
                        contentDescription = "Movie Poster",
                        modifier = Modifier
                            .width(60.dp)
                            .height(90.dp)
                            .padding(end = 8.dp)
                    )

                    Text(
                        text = "This is the ultimate rizzler movie. My gyatt was lvl 10 scared...",
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationPlaceholder() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.Black),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Home, contentDescription = "Home", tint = Color.White)
        Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Color.White)
        Icon(imageVector = Icons.Default.Favorite, contentDescription = "My List", tint = Color.White)
        Icon(imageVector = Icons.Default.Person, contentDescription = "Profile", tint = Color.White)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000) // Set a black background for the preview
@Composable
fun PreviewReviewPage() {
    ReviewPageUI()
}
