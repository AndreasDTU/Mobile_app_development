package com.example.movieapp.nav

import com.example.movieapp.ui.screen.mylist.MyListViewModelFactory

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavType

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
import com.example.movieapp.ui.screen.mainscreen.MovieViewModelFactory
import com.example.movieapp.ui.screen.moviedetails.MovieDetailViewModelFactory
import com.example.movieapp.ui.screen.mylist.MyListViewModel

@Composable
fun simplenav() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current
    val repository = MovieRepository(context) // Declare repository here

    Scaffold(bottomBar = {
        BottomAppBar {
            NavigationBarItem(selected = currentRoute == "MainScreen",
                onClick = { navController.navigate("MainScreen") },
                label = { Text("Home") },
                icon = {})
            NavigationBarItem(selected = currentRoute == "MovieDetailScreen",
                onClick = { navController.navigate("MovieDetailScreen/0") },
                label = { Text("Details") },
                icon = {})
            NavigationBarItem(selected = currentRoute == "MyFriendsScreen",
                onClick = { navController.navigate("MyFriendsScreen") },
                label = { Text("Friends") },
                icon = {})
            NavigationBarItem(selected = currentRoute == "MyListScreen",
                onClick = { navController.navigate("MyListScreen") },
                label = { Text("My List") },
                icon = {})
            NavigationBarItem(selected = currentRoute == "SearchScreen",
                onClick = { navController.navigate("SearchScreen") },
                label = { Text("Search") },
                icon = {})
        }
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "MainScreen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("MainScreen") {
                val viewModel = ViewModelProvider(
                    LocalViewModelStoreOwner.current!!,
                    MovieViewModelFactory(repository) // Use repository
                )[MovieViewModel::class.java]
                MainScreen(navController, viewModel)
            }
            composable(
                "MovieDetailScreen/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("id") ?: 0
                val detailViewModel = ViewModelProvider(
                    LocalViewModelStoreOwner.current!!,
                    MovieDetailViewModelFactory(movieId, repository)
                )[MovieDetailViewModel::class.java]

                val myListViewModel = ViewModelProvider(
                    LocalViewModelStoreOwner.current!!, MyListViewModelFactory(repository)
                )[MyListViewModel::class.java]

                MovieDetailScreen(
                    id = movieId,
                    navController = navController,
                    viewModel = detailViewModel,
                    myListViewModel = myListViewModel
                )
            }
            composable("MyFriendsScreen") { MyFriendsScreen() }
            composable("MyListScreen") {
                val viewModel = ViewModelProvider(
                    LocalViewModelStoreOwner.current!!,
                    MyListViewModelFactory(repository) // Use repository
                )[MyListViewModel::class.java]
                MyList(navController = navController, viewModel = viewModel)
            }

            composable("SearchScreen") { SearchScreen() }
        }
    }
}