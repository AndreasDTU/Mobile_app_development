package com.example.movieapp.nav

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.repositories.MovieRepository
import com.example.movieapp.ui.components.AppBackground
import com.example.movieapp.ui.screen.mainscreen.MainScreen
import com.example.movieapp.ui.screen.moviedetails.MovieDetailScreen
import com.example.movieapp.ui.screen.redundant.MyFriendsScreen
import com.example.movieapp.ui.screen.search.SearchScreen
import com.example.movieapp.ui.screen.mylist.MyList
import com.example.movieapp.ui.screen.moviedetails.MovieDetailViewModel
import com.example.movieapp.ui.screen.mainscreen.MovieViewModel
import com.example.movieapp.ui.theme.DarkPurple
import com.example.movieapp.ui.theme.LightPurple
import com.example.movieapp.ui.theme.TextWhite

@Composable
fun simplenav() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    AppBackground {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier.background(DarkPurple),
                    containerColor = DarkPurple,
                    contentColor = TextWhite
                ) {
                    NavigationBarItem(
                        selected = currentRoute == "MainScreen",
                        onClick = { navController.navigate("MainScreen") },
                        label = { Text("Home", color = TextWhite) },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = LightPurple) }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "MovieDetailScreen",
                        onClick = { navController.navigate("MovieDetailScreen/0") },
                        label = { Text("Details", color = TextWhite) },
                        icon = { Icon(Icons.Default.Info, contentDescription = "Details", tint = LightPurple) }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "MyFriendsScreen",
                        onClick = { navController.navigate("MyFriendsScreen") },
                        label = { Text("Friends", color = TextWhite) },
                        icon = { Icon(Icons.Default.AccountBox, contentDescription = "Friends", tint = LightPurple) }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "MyListScreen",
                        onClick = { navController.navigate("MyListScreen") },
                        label = { Text("My List", color = TextWhite) },
                        icon = { Icon(Icons.Default.Favorite, contentDescription = "My List", tint = LightPurple) }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "SearchScreen",
                        onClick = { navController.navigate("SearchScreen") },
                        label = { Text("Search", color = TextWhite) },
                        icon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = LightPurple) }
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "MainScreen",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("MainScreen") { MainScreen(navController, MovieViewModel(MovieRepository())) }
                composable(
                    "MovieDetailScreen/{id}",
                    arguments = listOf(navArgument("id") { defaultValue = 0 })
                ) { navBackStackEntry ->
                    val id = navBackStackEntry.arguments?.getInt("id") ?: 0
                    Log.d("Id", "Id: $id")
                    MovieDetailScreen(id, navController, MovieDetailViewModel(id, MovieRepository()))
                }
                composable("MyFriendsScreen") { MyFriendsScreen() }
                composable("MyListScreen") { MyList() }
                composable("SearchScreen") { SearchScreen() }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SimplenavPreview() {
    simplenav()
}
