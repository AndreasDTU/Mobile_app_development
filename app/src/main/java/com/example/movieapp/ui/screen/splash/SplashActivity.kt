package com.example.movieapp.ui.screen.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.repositories.MovieRepository
import com.example.movieapp.ui.MainActivity
import com.example.movieapp.ui.screen.mainscreen.MovieViewModel
import com.example.movieapp.ui.screen.mainscreen.MovieViewModelFactory
import com.example.movieapp.ui.splash.SplashScreen
import com.example.movieapp.ui.theme.MovieappTheme
import kotlinx.coroutines.launch
import com.example.movieapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewModel
        val repository = MovieRepository(applicationContext)
        val factory = MovieViewModelFactory(repository)
        movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

        setContentView(R.layout.activity_splash)

        observeDataLoading()
    }

    private fun observeDataLoading() {
        preloadData()
        lifecycleScope.launchWhenStarted {
            movieViewModel.isLoading.collect { isLoading ->
                if (!isLoading) {
                    Log.d("splash", "Going to main screen: ")
                    // Data has finished loading, navigate to MainScreen
                    navigateToMainScreen()
                }
            }
        }
    }

    private fun preloadData() {
        movieViewModel.fetchall()
    }

    private fun navigateToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Close SplashActivity
    }
}