package com.example.movieapp.nav

import EditProfileScreen
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.repositories.MovieRepository
import com.example.movieapp.repositories.RatingsRepository
import com.example.movieapp.repositories.UserRepository
import com.example.movieapp.ui.components.AppBackground
import com.example.movieapp.ui.screen.mainscreen.MainScreen
import com.example.movieapp.ui.screen.mainscreen.MovieViewModel
import com.example.movieapp.ui.screen.mainscreen.MovieViewModelFactory
import com.example.movieapp.ui.screen.moviedetails.MovieDetailScreen
import com.example.movieapp.ui.screen.moviedetails.MovieDetailViewModel
import com.example.movieapp.ui.screen.mylist.MyList
import com.example.movieapp.ui.screen.ratings.ProfileScreen
import com.example.movieapp.ui.screen.moviedetails.MovieDetailViewModelFactory
import com.example.movieapp.ui.screen.mylist.MyListViewModel
import com.example.movieapp.ui.screen.mylist.MyListViewModelFactory
import com.example.movieapp.ui.screen.ratings.RatingsScreen
import com.example.movieapp.ui.screen.ratings.RatingsViewModel
import com.example.movieapp.ui.screen.ratings.RatingsViewModelFactory
import com.example.movieapp.ui.screen.search.SearchScreen
import com.example.movieapp.viewmodels.UserViewModel
import com.example.movieapp.ui.theme.DarkPurple
import com.example.movieapp.ui.theme.LightPurple
import com.example.movieapp.ui.theme.TextWhite
import kotlinx.coroutines.launch
import com.example.movieapp.ui.screen.search.SearchViewModel
import com.example.movieapp.ui.screen.search.SearchViewModelFactory
import com.example.movieapp.ui.screen.settings.SettingsScreen


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun simplenav(isDarkTheme: Boolean, onThemeChange: (Boolean) -> Unit) {
    val navController = rememberNavController()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 4 }
    )
    val scope = rememberCoroutineScope()

    // Define pages
    val pages = listOf("MainScreen", "SearchScreen", "MyListScreen", "ProfileScreen")
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val parent = navBackStackEntry?.arguments?.getString("parent") ?: currentRoute
    val repository = MovieRepository(LocalContext.current)
    val userRepository = UserRepository()
    val ratingsRepository = RatingsRepository(LocalContext.current) // Manage ratings through UserRepository
    val ratingsViewModel = ViewModelProvider(
        LocalViewModelStoreOwner.current!!,
        RatingsViewModelFactory(ratingsRepository) // Pass ratingsRepository to RatingsViewModelFactory
    )[RatingsViewModel::class.java]
    AppBackground(isDarkTheme = isDarkTheme) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "FLICK-FINDER",
                            color = if (isDarkTheme) Color.White else Color.Black,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("SettingsScreen") }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = if (isDarkTheme) Color.White else Color.Black
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = if (isDarkTheme) DarkPurple else Color(0xFFFFC0CB) // Light pink for light theme
                    )
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = if (isDarkTheme) DarkPurple else Color(0xFFFFA6C9), // Lighter pink for light theme
                    contentColor = if (isDarkTheme) Color.White else Color.Black
                ) {
                    pages.forEachIndexed { index, page ->
                        val icon = when (page) {
                            "MainScreen" -> Icons.Default.Home
                            "SearchScreen" -> Icons.Default.Search
                            "MyListScreen" -> Icons.AutoMirrored.Filled.List
                            "ProfileScreen" -> Icons.Default.Person
                            else -> Icons.Default.Home
                        }

                        NavigationBarItem(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    navController.navigate("SwipeableScreens") {
                                        popUpTo("SwipeableScreens") { inclusive = true }
                                    }
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            label = { Text(page.replace("Screen", "")) },
                            icon = {
                                Icon(
                                    icon,
                                    contentDescription = page,
                                    tint = if (pagerState.currentPage == index) LightPurple else TextWhite
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = TextWhite,
                                selectedTextColor = TextWhite,
                                indicatorColor = Color.Transparent,
                                unselectedIconColor = TextWhite,
                                unselectedTextColor = TextWhite
                            )
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "SwipeableScreens",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("SwipeableScreens") {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        when (pages[page]) {
                            "MainScreen" -> MainScreen(
                                navController = navController,
                                viewModel = ViewModelProvider(
                                    LocalViewModelStoreOwner.current!!,
                                    MovieViewModelFactory(repository)
                                )[MovieViewModel::class.java],
                                isDarkTheme = isDarkTheme // Pass the isDarkTheme value here
                            )
                            "SearchScreen" -> SearchScreen(navController, ViewModelProvider(
                                LocalViewModelStoreOwner.current!!,
                                SearchViewModelFactory(repository)
                            )[SearchViewModel::class.java], isDarkTheme = isDarkTheme)
                            "MyListScreen" -> MyList(
                                navController = navController,
                                viewModel = ViewModelProvider(
                                    LocalViewModelStoreOwner.current!!,
                                    MyListViewModelFactory(repository)
                                )[MyListViewModel::class.java],
                                isDarkTheme = isDarkTheme // Pass isDarkTheme here
                            )
                            "ProfileScreen" -> ProfileScreen(
                                userViewModel = UserViewModel(),
                                ratingsViewModel = ratingsViewModel,
                                onEditClick = { navController.navigate("EditProfileScreen") },
                                onViewMoreRatingsClick = { navController.navigate("RatingsScreen") }
                            )
                        }
                    }
                }
                composable("MainScreen") {
                    val viewModel = ViewModelProvider(
                        LocalViewModelStoreOwner.current!!,
                        MovieViewModelFactory(repository)
                    )[MovieViewModel::class.java]
                    MainScreen(
                        navController = navController,
                        viewModel = viewModel,
                        isDarkTheme = isDarkTheme // Pass isDarkTheme here
                    )
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
                        id = id,
                        navController = navController,
                        viewModel = detailViewModel,
                        myListViewModel = myListViewModel,
                        ratingsViewModel = ratingsViewModel,
                        isDarkTheme = isDarkTheme // Pass the isDarkTheme value here
                    )
                }
                composable("MyListScreen") {
                    val viewModel = ViewModelProvider(
                        LocalViewModelStoreOwner.current!!,
                        MyListViewModelFactory(repository)
                    )[MyListViewModel::class.java]
                    MyList(
                        navController = navController,
                        viewModel = viewModel,
                        isDarkTheme = isDarkTheme // Pass the theme state
                    )
                }
                composable("ProfileScreen") {
                    ProfileScreen(
                        userViewModel = UserViewModel(),
                        ratingsViewModel = ratingsViewModel,
                        onEditClick = { navController.navigate("EditProfileScreen") },
                        onViewMoreRatingsClick = { navController.navigate("RatingsScreen") }
                    )
                }
                composable("EditProfileScreen") {
                    EditProfileScreen(
                        userViewModel = UserViewModel(),
                        onSaveClick = { navController.popBackStack() },
                        isDarkTheme = isDarkTheme // Pass the theme state
                    )
                }
                composable("RatingsScreen") {
                    RatingsScreen(
                        viewModel = ratingsViewModel,
                        movieRepository = repository
                    )
                }
                composable(route = "SearchScreen") {
                    val viewModel = ViewModelProvider(
                        LocalViewModelStoreOwner.current!!,
                        SearchViewModelFactory(repository)
                    )[SearchViewModel::class.java]

                    SearchScreen(navController = navController, searchViewModel = viewModel, isDarkTheme = true)
                }

                composable("SettingsScreen") {
                    SettingsScreen(
                        isDarkTheme = isDarkTheme,
                        onThemeChange = onThemeChange
                    )
                }
            }
        }
    }
}