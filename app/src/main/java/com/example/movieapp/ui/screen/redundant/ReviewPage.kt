package com.example.movieapp.ui.screen.redundant

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.ui.components.AppBackground
import com.example.movieapp.ui.screen.mylist.SectionTitle
import com.example.movieapp.ui.theme.LightPurple
import com.example.movieapp.ui.theme.TextWhite

@Composable
fun ReviewPageUI() {
    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header Section
            HeaderSection()

            // Reviewed Movies Section
            SectionTitle("Reviewed Movies")
            MovieCarousel()

            // Friend's Review Section
            Spacer(modifier = Modifier.height(16.dp))
            FriendReviewSection()

            // Friends 5 Star Movies Section
            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle("Friends' 5 Star Movies")
            MovieCarousel()

            // Bottom Navigation Placeholder
            Spacer(modifier = Modifier.weight(1f)) // Push navigation to the bottom
            BottomNavigationPlaceholder()
        }
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // User Profile Icon
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(LightPurple, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "J", fontSize = 24.sp, color = TextWhite)
        }

        // Username
        Text(
            text = "Johnny",
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 18.sp,
            color = TextWhite,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(1f))

        // Search Icon
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            modifier = Modifier.size(24.dp),
            tint = TextWhite
        )
    }
}

@Composable
fun ReviewSectionTitle(title: String) {
    Text(
        text = title,
        color = LightPurple,
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
        colors = CardDefaults.cardColors(containerColor = LightPurple)
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
                    .background(LightPurple, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "N", fontSize = 20.sp, color = TextWhite)
            }

            Text(
                text = "Nick",
                modifier = Modifier.padding(start = 8.dp),
                fontSize = 16.sp,
                color = TextWhite
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "This is what Nick had to say:",
            fontSize = 14.sp,
            color = TextWhite,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = LightPurple.copy(alpha = 0.1f))
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
                        text = "This is the ultimate movie. My experience was phenomenal!",
                        fontSize = 14.sp,
                        color = TextWhite,
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
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Home, contentDescription = "Home", tint = TextWhite)
        Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = TextWhite)
        Icon(imageVector = Icons.Default.Favorite, contentDescription = "My List", tint = TextWhite)
        Icon(imageVector = Icons.Default.Person, contentDescription = "Profile", tint = TextWhite)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReviewPage() {
    ReviewPageUI()
}
