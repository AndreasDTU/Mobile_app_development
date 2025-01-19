package com.example.movieapp.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.movieapp.nav.SimpleNav
import com.example.movieapp.ui.screen.redundant.FirstTimeScreen
import com.example.movieapp.ui.theme.MovieAppTheme
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install Splash Screen
        val splashScreen = installSplashScreen()

        // Declare splash-related states
        var isLoading by mutableStateOf(true)

        // Keep splash visible until initialization is complete
        splashScreen.setKeepOnScreenCondition { isLoading }

        super.onCreate(savedInstanceState)

        val isFirstLaunch = isFirstTimeLaunch(this)

        setContent {
            MovieAppTheme {
                val context = LocalContext.current
                var firstLaunch by remember { mutableStateOf(isFirstLaunch) }
                var mainScreenDataLoaded by remember { mutableStateOf(false) }

                // Concurrently load main screen data in a coroutine
                LaunchedEffect(Unit) {
                    // Launch both splash and main screen resource loading concurrently
                    withContext(Dispatchers.Default) {
                        // Simulate data loading for the Main Screen
                        delay(1000) // Replace this with actual resource loading logic
                        mainScreenDataLoaded = true
                    }
                    // Finalize splash screen logic
                    isLoading = false
                }

                // Show either the FirstTimeScreen or MainScreen based on state
                if (firstLaunch) {
                    FirstTimeScreen(
                        onLoginClick = { /* Navigate to Login Screen */ },
                        onGetStartedClick = {
                            markFirstTimeLaunchComplete(context)
                            firstLaunch = false
                        }
                    )
                } else if (mainScreenDataLoaded) {
                    SimpleNav() // Load the main app navigation
                }
            }
        }
    }

    // Helper functions for managing first-time launch state
    private fun isFirstTimeLaunch(context: Context): Boolean {
        val sharedPreferences =
            context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("is_first_time_launch", true)
    }

    private fun markFirstTimeLaunchComplete(context: Context) {
        val sharedPreferences =
            context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("is_first_time_launch", false).apply()
    }
}