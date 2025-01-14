package com.example.movieapp.nav

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
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
import com.example.movieapp.ui.screen.mylist.MyList
import com.example.movieapp.ui.screen.ratings.ProfileScreen
import com.example.movieapp.ui.screen.redundant.EditProfileScreen
import com.example.movieapp.ui.screen.moviedetails.MovieDetailViewModelFactory
import com.example.movieapp.ui.screen.mylist.MyList
import com.example.movieapp.ui.screen.mylist.MyListViewModel
import com.example.movieapp.ui.screen.mylist.MyListViewModelFactory
import com.example.movieapp.ui.screen.redundant.MyFriendsScreen
import com.example.movieapp.ui.screen.redundant.EditProfileScreen
import com.example.movieapp.ui.screen.ratings.ProfileScreen
import com.example.movieapp.ui.screen.search.SearchScreen
import com.example.movieapp.viewmodels.UserViewModel
import com.example.movieapp.ui.theme.DarkPurple
import com.example.movieapp.ui.theme.LightPurple
import com.example.movieapp.ui.theme.TextWhite
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
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
    val repository = MovieRepository(LocalContext.current)
    AppBackground {
        Scaffold(
            topBar = {
                TopAppBar(
                    title= {
                        Text(
                            text = "FLICK-FINDER",
                            color = TextWhite,
                            style = MaterialTheme.typography.titleSmall
                        )
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("ProfileScreen")}) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = TextWhite
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = DarkPurple
                    )
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = DarkPurple,
                    contentColor = TextWhite
                ) {
                    NavigationBarItem(
                        selected = parentRoute == "MainScreen",
                        onClick = {
                            scope.launch {
                                navController.navigate("SwipeableScreens") {
                                    popUpTo("SwipeableScreens") { inclusive = true }
                                }
                                pagerState.animateScrollToPage(0)
                            }
                        },
                        label = { Text("Home") },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = LightPurple) }
                    )

                    NavigationBarItem(
                        selected = parentRoute == "SearchScreen",
                        onClick = {
                            scope.launch {
                                navController.navigate("SwipeableScreens") {
                                    popUpTo("SwipeableScreens") { inclusive = true }
                                }
                                pagerState.animateScrollToPage(1)
                            }
                        },
                        label = { Text("Search") },
                        icon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = LightPurple) }
                    )

                    NavigationBarItem(
                        selected = parentRoute == "MyListScreen",
                        onClick = {
                            scope.launch {
                                navController.navigate("SwipeableScreens") {
                                    popUpTo("SwipeableScreens") { inclusive = true }
                                }
                                pagerState.animateScrollToPage(2)
                            }
                        },
                        label = { Text("My List") },
                        icon = {
                            Icon(Icons.AutoMirrored.Filled.List, contentDescription = "My List", tint = LightPurple
                            )
                        }
                    )

                    NavigationBarItem(
                        selected = parentRoute == "MyFriendsScreen",
                        onClick = {
                            scope.launch {
                                navController.navigate("SwipeableScreens") {
                                    popUpTo("SwipeableScreens") { inclusive = true }
                                }
                                pagerState.animateScrollToPage(3)
                            }
                        },
                        label = { Text("Friends") },
                        icon = { Icon(Icons.Default.Person, contentDescription = "Friends", tint = LightPurple) }
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
                            "MainScreen" -> MainScreen(navController, ViewModelProvider(
                                LocalViewModelStoreOwner.current!!,
                                MovieViewModelFactory(repository)
                            )[MovieViewModel::class.java])
                            "SearchScreen" -> SearchScreen()
                            "MyListScreen" -> MyList(navController,ViewModelProvider(
                                LocalViewModelStoreOwner.current!!,
                                MyListViewModelFactory(repository)
                            )[MyListViewModel::class.java])
                            "MyFriendsScreen" -> MyFriendsScreen()
                        }
                    }
                }
                composable("MainScreen") {
                    val viewModel = ViewModelProvider(
                        LocalViewModelStoreOwner.current!!,
                        MovieViewModelFactory(repository)
                    )[MovieViewModel::class.java]
                    MainScreen(navController, viewModel)
                }
                composable(
                    "MovieDetailScreen/{id}",
                    arguments = listOf(
                        navArgument("id") { defaultValue = 0 },
                        navArgument("parent") { defaultValue = "MainScreen" }
                    )
                ) { navBackStackEntry ->
                    val id = navBackStackEntry.arguments?.getInt("id") ?: 0
                    val detailViewModel = ViewModelProvider(
                        LocalViewModelStoreOwner.current!!,
                        MovieDetailViewModelFactory(id, repository)
                    )[MovieDetailViewModel::class.java]
                    val myListViewModel = ViewModelProvider(
                        LocalViewModelStoreOwner.current!!,
                        MyListViewModelFactory(repository)
                    )[MyListViewModel::class.java]
                    MovieDetailScreen(
                        id,
                        navController,
                        detailViewModel,
                        myListViewModel
                    )
                }
                composable("MyFriendsScreen") { MyFriendsScreen() }
                composable("MyListScreen") {
                    val viewModel =ViewModelProvider(
                        LocalViewModelStoreOwner.current!!,
                        MyListViewModelFactory(repository)
                    )[MyListViewModel::class.java]
                    MyList(navController, viewModel) }
                composable("ProfileScreen") {
                    ProfileScreen(
                        userViewModel = UserViewModel(),
                        onEditClick = {
                            navController.navigate("EditProfileScreen")
                        }
                    )
                }
                composable("EditProfileScreen") {
                    EditProfileScreen(
                        userViewModel = UserViewModel(),
                        onSaveClick = {navController.popBackStack()}
                    )
                }
                /*
                composable("SearchScreen") {
                    val viewModel = ViewModelProvider(
                        LocalViewModelStoreOwner.current!!,
                        SearchViewModelFactory(repository)
                    )[SearchViewModel::class.java]
                    SearchScreen(navController, viewModel)
                }
                */

            }
        }
    }
}
