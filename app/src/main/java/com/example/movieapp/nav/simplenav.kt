package com.example.movieapp.nav

import android.util.Log
import androidx.compose.foundation.layout.*
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

    Scaffold(
        bottomBar = {
            BottomAppBar {
                NavigationBarItem(
                    selected = currentRoute == "MainScreen",
                    onClick = { navController.navigate("MainScreen") },
                    label = { Text("Home") },
                    icon = {}
                )
                NavigationBarItem(
                    selected = currentRoute == "MovieDetailScreen",
                    onClick = { navController.navigate("MovieDetailScreen/0") },
                    label = { Text("Details") },
                    icon = {}
                )
                NavigationBarItem(
                    selected = currentRoute == "MyFriendsScreen",
                    onClick = { navController.navigate("MyFriendsScreen") },
                    label = { Text("Friends") },
                    icon = {}
                )
                NavigationBarItem(
                    selected = currentRoute == "MyListScreen",
                    onClick = { navController.navigate("MyListScreen") },
                    label = { Text("My List") },
                    icon = {}
                )
                NavigationBarItem(
                    selected = currentRoute == "SearchScreen",
                    onClick = { navController.navigate("SearchScreen") },
                    label = { Text("Search") },
                    icon = {}
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
