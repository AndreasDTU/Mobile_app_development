package com.example.movieapp.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.nav.simplenav
import com.example.movieapp.repositories.MovieRepository
import com.example.movieapp.ui.screen.mainscreen.MovieViewModel
import com.example.movieapp.ui.screen.mainscreen.MovieViewModelFactory
import com.example.movieapp.ui.screen.redundant.FirstTimeScreen
import com.example.movieapp.ui.theme.MovieappTheme
import kotlinx.coroutines.*
import com.example.movieapp.ui.screen.settings.SettingsScreen

class MainActivity : ComponentActivity() {
    private lateinit var movieViewModel: MovieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val repository = MovieRepository(applicationContext)
        val factory = MovieViewModelFactory(repository)
        val movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
        setContent {
            var isDarkTheme by remember {
                mutableStateOf(true)
            }

                MovieappTheme(darkTheme = isDarkTheme) { // Apply theme based on the state
                    simplenav(
                        movieViewModel = movieViewModel, // Pass shared ViewModel
                        isDarkTheme = isDarkTheme,
                        onThemeChange = { isDarkTheme = it }
                    ) // Replace with your main screen composable function
                }
            }
    }
}
