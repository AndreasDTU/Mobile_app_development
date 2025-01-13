package com.example.movieapp.ui.screen.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.repositories.MovieRepository

class MovieDetailViewModelFactory(
    private val movieId: Int,
    private val repository: MovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return MovieDetailViewModel(movieId, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}