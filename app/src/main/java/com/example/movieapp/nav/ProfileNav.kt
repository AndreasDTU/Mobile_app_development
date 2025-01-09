package com.example.movieapp.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieapp.ui.screen.redundant.EditProfileScreen
import com.example.movieapp.ui.screen.ratings.ProfileScreen
import com.example.movieapp.viewmodels.UserViewModel

@Composable
fun ProfileNav(navController: NavHostController) {
    val userViewModel = UserViewModel()

    NavHost(navController = navController, startDestination = "profile") {
        composable("profile") {
            ProfileScreen(
                userViewModel = userViewModel,
                onEditClick = { navController.navigate("editProfile") }
            )
        }
        composable("editProfile") {
            EditProfileScreen(
                userViewModel = userViewModel,
                onSaveClick = { navController.popBackStack() }
            )
        }
    }
}

