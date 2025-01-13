package com.example.movieapp.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.movieapp.nav.simplenav
import com.example.movieapp.ui.screen.redundant.FirstTimeScreen
import com.example.movieapp.ui.theme.MovieappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
MovieappTheme {
    val context = LocalContext.current
    val isFirstLaunch = remember { mutableStateOf(isFirstTimeLaunch(context)) }
    if (isFirstLaunch.value) {
        // Show FirstTimeScreen if this is the first launch
        FirstTimeScreen(
            onLoginClick = { /* Navigate to Login Screen */ },
            onGetStartedClick = {
                markFirstTimeLaunchComplete(context)
                isFirstLaunch.value = false
            }
        )
    } else {
        // Show main screen
        simplenav() // Replace with your main screen composable function
    }
}
        }
    }

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