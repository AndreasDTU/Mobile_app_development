package com.example.movieapp.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.movieapp.ui.MainActivity
import com.example.movieapp.ui.theme.MovieAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                SplashScreen()
            }
        }

        // Start preloading data during the splash screen
        lifecycleScope.launch {
            val movieData = preloadMovieData() // Load movies in the background
            delay(1000) // Ensure splash screen is displayed for at least 1 second
            navigateToMainScreen(movieData)
        }
    }

    private suspend fun preloadMovieData(): List<String> {
        // Simulate loading movie data (Replace this with your actual API call or database query)
        return withContext(Dispatchers.IO) {
            delay(1000) // Simulate network or database delay
            listOf(
                "Sonic the Hedgehog 3",
                "Kraven the Hunter",
                "Avatar: The Way of Water",
                "The Batman"
            )
        }
    }

    private fun navigateToMainScreen(movieData: List<String>) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putStringArrayListExtra("MOVIE_DATA", ArrayList(movieData)) // Pass movie data
        startActivity(intent)
        finish()
    }
}