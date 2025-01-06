package com.example.movieapp.ui.screen.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.ui.components.AppBackground
import com.example.movieapp.ui.theme.LightPurple
import com.example.movieapp.ui.theme.MovieappTheme
import com.example.movieapp.ui.theme.TextWhite

@Composable
fun SearchScreen() {
    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Top Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            ) {
                Text(
                    text = "Search Movies",
                    color = TextWhite,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            SearchBar()

            Spacer(modifier = Modifier.height(16.dp))

            // Popular Searches
            SectionTitle("Popular Searches")
            SearchButton("Popular Search 1")
            SearchButton("Popular Search 2")
            SearchButton("Popular Search 3")

            Spacer(modifier = Modifier.height(16.dp))

            // Recent Searches
            SectionTitle("Recent Searches")
            SearchButton("Recent Search 1")
            SearchButton("Recent Search 2")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var searchQuery by remember { mutableStateOf("") }

    TextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Search movies...", color = LightPurple) },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = LightPurple,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = LightPurple,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(vertical = 9.dp)
    )
}

@Composable
fun SearchButton(text: String) {
    Button(
        onClick = { },
        colors = ButtonDefaults.buttonColors(
            containerColor = LightPurple,
            contentColor = TextWhite
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    MovieappTheme {
        SearchScreen()
    }
}