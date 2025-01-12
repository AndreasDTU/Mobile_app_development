package com.example.movieapp.nav

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.movieapp.ui.components.AppBackground
import com.example.movieapp.ui.screen.mainscreen.MainScreen
import com.example.movieapp.ui.screen.mainscreen.MovieViewModel
import com.example.movieapp.ui.screen.mainscreen.MovieViewModelFactory
import com.example.movieapp.ui.screen.moviedetails.MovieDetailScreen
import com.example.movieapp.ui.screen.moviedetails.MovieDetailViewModel
import com.example.movieapp.ui.screen.moviedetails.MovieDetailViewModelFactory
import com.example.movieapp.ui.screen.mylist.MyList
import com.example.movieapp.ui.screen.mylist.MyListViewModel
import com.example.movieapp.ui.screen.mylist.MyListViewModelFactory
import com.example.movieapp.ui.screen.redundant.MyFriendsScreen
import com.example.movieapp.ui.screen.redundant.EditProfileScreen
import com.example.movieapp.ui.screen.ratings.ProfileScreen
import com.example.movieapp.ui.screen.search.SearchScreen
import com.example.movieapp.ui.screen.search.SearchViewModel
import com.example.movieapp.ui.screen.search.SearchViewModelFactory
import com.example.movieapp.ui.theme.DarkPurple
import com.example.movieapp.ui.theme.LightPurple
import com.example.movieapp.ui.theme.TextWhite
import com.example.movieapp.viewmodels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleNav() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current
    val repository = MovieRepository(context) // Declare repository here

    AppBackground {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "FLICK-FINDER",
                            color = TextWhite,
                            style = MaterialTheme.typography.titleSmall
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
                        containerColor = DarkPurple // Match the theme
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
                        icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Friends", tint = LightPurple) }
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
                    val viewModel = ViewModelProvider(
                        LocalViewModelStoreOwner.current!!,
                        MovieViewModelFactory(repository)
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
                        MyListViewModelFactory(repository)
                    )[MyListViewModel::class.java]
                    MyList(navController = navController, viewModel = viewModel)
                }
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
                composable(route = "SearchScreen") {
                    val viewModel = ViewModelProvider(
                        LocalViewModelStoreOwner.current!!,
                        SearchViewModelFactory(repository)
                    )[SearchViewModel::class.java]

                    SearchScreen(navController = navController, searchViewModel = viewModel)
                }


            }
        }
    }
}