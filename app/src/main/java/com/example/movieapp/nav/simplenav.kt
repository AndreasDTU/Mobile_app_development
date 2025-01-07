package com.example.movieapp.nav

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.repositories.MovieRepository
import com.example.movieapp.ui.screen.mainscreen.MainScreen
import com.example.movieapp.ui.screen.moviedetails.MovieDetailScreen
import com.example.movieapp.ui.screen.redundant.MyFriendsScreen
import com.example.movieapp.ui.screen.search.SearchScreen
import com.example.movieapp.ui.screen.mylist.MyList
import com.example.movieapp.ui.screen.moviedetails.MovieDetailViewModel
import com.example.movieapp.ui.screen.mainscreen.MovieViewModel

@Composable
fun simplenav() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Retrieve parent route from arguments
    val parentRoute = navBackStackEntry?.arguments?.getString("parent") ?: currentRoute

    Scaffold(
        bottomBar = {
            BottomAppBar {
                NavigationBarItem(
                    selected = parentRoute == "MainScreen",
                    onClick = { navController.navigate("MainScreen") },
                    label = { Text("Home") },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") }
                )
                NavigationBarItem(
                    selected = parentRoute == "SearchScreen",
                    onClick = { navController.navigate("SearchScreen") },
                    label = { Text("Search") },
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") }
                )
                NavigationBarItem(
                    selected = parentRoute == "MyListScreen",
                    onClick = { navController.navigate("MyListScreen") },
                    label = { Text("My List") },
                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "My List") }
                )
                NavigationBarItem(
                    selected = parentRoute == "MyFriendsScreen",
                    onClick = { navController.navigate("MyFriendsScreen") },
                    label = { Text("Friends") },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Friends") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "MainScreen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("MainScreen") {
                MainScreen(navController, MovieViewModel(MovieRepository()))
            }
            composable(
                "MovieDetailScreen/{id}?parent={parent}",
                arguments = listOf(
                    navArgument("id") { defaultValue = 0 },
                    navArgument("parent") { defaultValue = "MainScreen" }
                )
            ) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("id") ?: 0
                val parent = navBackStackEntry.arguments?.getString("parent") ?: "MainScreen"
                Log.d("Id", "Id: $id, Parent: $parent")
                MovieDetailScreen(id, navController, MovieDetailViewModel(id, MovieRepository()))
            }
            composable("MyFriendsScreen") { MyFriendsScreen() }
            composable("MyListScreen") { MyList() }
            composable("SearchScreen") { SearchScreen() }
        }
    }
}