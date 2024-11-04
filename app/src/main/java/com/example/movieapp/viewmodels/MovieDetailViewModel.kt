package com.example.movieapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.Movie
import com.example.movieapp.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(id: Int, private val movieRepository: MovieRepository) : ViewModel() {

    private val _movieDetails = MutableStateFlow<Movie?>(null)
    val movieDetails: StateFlow<Movie?> = _movieDetails


    init {
        fetchMovieDetails(id)
    }
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private fun fetchMovieDetails(id: Int) {
        viewModelScope.launch {
            try {
                val movie = movieRepository.getMovieDetails(id)
                Log.e("MovieModel", "Got the movie $movie")
                _movieDetails.value = movie
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}