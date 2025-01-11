package com.example.movieapp.nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieapp.repositories.MovieRepository
import com.example.movieapp.repositories.RatingsRepository
import com.example.movieapp.ui.screen.redundant.EditProfileScreen
import com.example.movieapp.ui.screen.ratings.ProfileScreen
import com.example.movieapp.ui.screen.ratings.RatingsScreen
import com.example.movieapp.ui.screen.ratings.RatingsViewModel
import com.example.movieapp.ui.screen.ratings.RatingsViewModelFactory
import com.example.movieapp.viewmodels.UserViewModel

@Composable
fun ProfileNav(
    navController: NavHostController,
    userViewModel: UserViewModel = UserViewModel(),
    ratingsRepository: RatingsRepository,
    movieRepository: MovieRepository
) {

    NavHost(navController = navController, startDestination = "profile") {
        composable("profile") {
            ProfileScreen(
                userViewModel = userViewModel,
                onEditClick = { navController.navigate("editProfile") },
                ratings = ratingsRepository.getRatings(),
                onViewRatingsClick = { navController.navigate("ratingsScreen") }
            )
        }
        composable("editProfile") {
            EditProfileScreen(
                userViewModel = userViewModel,
                onSaveClick = { navController.popBackStack() }
            )
        }
        // RatingsScreen Navigation
        composable("ratingsScreen") {
            val ratingsViewModel = ViewModelProvider(
                LocalViewModelStoreOwner.current!!,
                RatingsViewModelFactory(ratingsRepository)
            )[RatingsViewModel::class.java]

            RatingsScreen(
                viewModel = ratingsViewModel,
                movieRepository = movieRepository
            )
        }
    }
}