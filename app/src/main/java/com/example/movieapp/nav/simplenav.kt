package com.example.movieapp.nav
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.repositories.MovieRepository
import com.example.movieapp.ui.components.AppBackground
import com.example.movieapp.ui.screen.mainscreen.MainScreen
import com.example.movieapp.ui.screen.mainscreen.MovieViewModel
import com.example.movieapp.ui.screen.moviedetails.MovieDetailScreen
import com.example.movieapp.ui.screen.redundant.MyFriendsScreen
import com.example.movieapp.ui.screen.search.SearchScreen
import com.example.movieapp.ui.screen.mylist.MyList
import com.example.movieapp.ui.screen.redundant.EditProfileScreen
import com.example.movieapp.ui.screen.ratings.ProfileScreen
import com.example.movieapp.viewmodels.UserViewModel
import com.example.movieapp.ui.theme.DarkPurple
import com.example.movieapp.ui.theme.LightPurple
import com.example.movieapp.ui.theme.TextWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun simplenav() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    AppBackground {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "FLICK-FINDER",
                            color = TextWhite,
                            style = MaterialTheme.typography.titleSmall // Adjust font size here
                        )
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("ProfileScreen") }) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = TextWhite
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = DarkPurple // Match the theme with this color
                    )
                )
            },
            bottomBar = {
                BottomAppBar(
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
                    NavigationBarItem(
                        selected = currentRoute == "MyFriendsScreen",
                        onClick = { navController.navigate("MyFriendsScreen") },
                        label = { Text("Friends", color = TextWhite) },
                        icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Myfriends", tint = LightPurple) }
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
                composable("MyListScreen") { MyList() }
                composable("SearchScreen") { SearchScreen() }
                composable("ProfileScreen") {
                    ProfileScreen(
                        userViewModel = UserViewModel(),
                        onEditClick = { navController.navigate("EditProfileScreen") }
                    )
                }
                composable("EditProfileScreen") {
                    EditProfileScreen(
                        userViewModel = UserViewModel(),
                        onSaveClick = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SimplenavPreview() {
    simplenav()
}
