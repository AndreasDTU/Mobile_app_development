package com.example.movieapp.nav

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.repositories.MovieRepository
import com.example.movieapp.ui.screen.mainscreen.MainScreen
import com.example.movieapp.ui.screen.moviedetails.MovieDetailScreen
import com.example.movieapp.ui.screen.mainscreen.MovieViewModel
import com.example.movieapp.ui.screen.moviedetails.MovieDetailViewModel
import com.example.movieapp.ui.screen.mylist.MyList
import com.example.movieapp.ui.screen.redundant.MyFriendsScreen
import com.example.movieapp.ui.screen.search.SearchScreen

import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun simplenav() {
    val navController = rememberNavController()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 4 }
    )
    val scope = rememberCoroutineScope()

    // Define pages
    val pages = listOf("MainScreen", "SearchScreen", "MyListScreen", "MyFriendsScreen")
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val parentRoute = navBackStackEntry?.arguments?.getString("parent") ?: currentRoute
    Scaffold(
        bottomBar = {
            BottomAppBar {
                    NavigationBarItem(
                        selected = pagerState.currentPage == 0 && parentRoute == "MainScreen",
                        onClick = { scope.launch { navController.navigate("SwipeableScreens"); pagerState.animateScrollToPage(0) }  },
                        label = { Text("Home") },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") }
                    )

                    NavigationBarItem(
                        selected = pagerState.currentPage == 1 && parentRoute == "SearchScreen",
                        onClick = { scope.launch {navController.navigate("SwipeableScreens"); pagerState.animateScrollToPage(1) } },
                        label = { Text("Search") },
                        icon = { Icon(Icons.Default.Search, contentDescription = "Search") }
                    )
                    NavigationBarItem(
                        selected = pagerState.currentPage == 2 && parentRoute == "MyListScreen",
                        onClick = { scope.launch { navController.navigate("SwipeableScreens"); pagerState.animateScrollToPage(2) } },
                        label = { Text("My List") },
                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "My List") }
                    )
                    NavigationBarItem(
                        selected = pagerState.currentPage == 3 && parentRoute == "MyFriendsScreen",
                        onClick = { scope.launch { navController.navigate("SwipeableScreens"); pagerState.animateScrollToPage(3) } },
                        label = { Text("Friends") },
                        icon = { Icon(Icons.Default.Person, contentDescription = "Friends") }
                    )
                }
            }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "SwipeableScreens",
            modifier = Modifier.padding(innerPadding)
        ) {
            // Swipeable Screens
            composable("SwipeableScreens") {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    when (pages[page]) {
                        "MainScreen" -> MainScreen(navController, MovieViewModel(MovieRepository()))
                        "SearchScreen" -> SearchScreen()
                        "MyListScreen" -> MyList()
                        "MyFriendsScreen" -> MyFriendsScreen()
                    }
                }
            }
            composable("MainScreen") {
                MainScreen(navController, MovieViewModel(MovieRepository()))
            }
            composable(
                "MovieDetailScreen/{id}",
                arguments = listOf(
                    navArgument("id") { defaultValue = 0 }
                )
            ) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("id") ?: 0
                MovieDetailScreen(id, navController, MovieDetailViewModel(id, MovieRepository()))
            }
            composable("MyFriendsScreen") { MyFriendsScreen() }
            composable("MyListScreen") { MyList() }
            composable("SearchScreen") { SearchScreen() }
        }
    }
}
