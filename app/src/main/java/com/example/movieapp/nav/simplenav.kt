package com.example.movieapp.nav
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.repositories.MovieRepository
import com.example.movieapp.ui.screen.MainScreen
import com.example.movieapp.ui.screen.FirstTimeScreen
import com.example.movieapp.ui.screen.LogInScreen
import com.example.movieapp.ui.screen.SignUpScreen
import com.example.movieapp.ui.screen.MovieDetailScreen
import com.example.movieapp.ui.screen.MyFriendsScreen
import com.example.movieapp.viewmodels.MovieDetailViewModel
import com.example.movieapp.viewmodels.MovieViewModel
import com.example.movieapp.ui.screen.MyList

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

                    selected = currentRoute == "MyListScreen",
                    onClick = { navController.navigate("MyListScreen") },
                    label = { Text("My List") },
                    icon = {} )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "MainScreen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("MainScreen") { MainScreen(navController, MovieViewModel(MovieRepository())) }
            composable("MovieDetailScreen/{id}", arguments = listOf(navArgument("id") {defaultValue = 0})
            ) {navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("id") ?:0
                Log.d("Id","Id: $id")
                MovieDetailScreen(id, navController, MovieDetailViewModel(id, MovieRepository()))
            }


            composable("MyFriendsScreen") {
                MyFriendsScreen()
            }

            composable("MyListScreen") {
                MyList()
            }
        }
    }
}