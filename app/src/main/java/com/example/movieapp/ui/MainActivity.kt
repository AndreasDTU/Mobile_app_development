package com.example.movieapp.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.movieapp.nav.simplenav
import com.example.movieapp.ui.screen.redundant.FirstTimeScreen
import com.example.movieapp.ui.theme.MovieappTheme
import kotlinx.coroutines.*
import com.example.movieapp.ui.screen.settings.SettingsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install Splash Screen
        val splashScreen = installSplashScreen()

        // Declare splash-related states
        var isLoading by mutableStateOf(true)

        // Keep splash visible until initialization is complete
        splashScreen.setKeepOnScreenCondition { isLoading }

        super.onCreate(savedInstanceState)

        setContent {
            var isDarkTheme by remember {
                mutableStateOf(true)
            }
                MovieappTheme(darkTheme = isDarkTheme) { // Apply theme based on the state
                    val context = LocalContext.current

                    var mainScreenDataLoaded by remember { mutableStateOf(false) }
                    LaunchedEffect(Unit) {
                        // Launch both splash and main screen resource loading concurrently
                        withContext(Dispatchers.Default) {
                            // Simulate data loading for the Main Screen
                            delay(1000) // Replace this with actual resource loading logic
                            mainScreenDataLoaded = true
                        }
                    }
                    // Finalize splash screen logic
                    isLoading = false

                     // Allow theme switching
                    simplenav(
                        isDarkTheme = isDarkTheme,
                        onThemeChange = { isDarkTheme = it }
                    ) // Replace with your main screen composable function
                }
            }
    }
}
