package com.example.movieapp.ui.screen.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.Movie
import com.example.movieapp.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies: StateFlow<List<Movie>> get() = _popularMovies

    private val _scaryMovies = MutableStateFlow<List<Movie>>(emptyList())
    val scaryMovies: StateFlow<List<Movie>> get() = _scaryMovies

    private val _funnyMovies = MutableStateFlow<List<Movie>>(emptyList())
    val funnyMovies: StateFlow<List<Movie>> get() = _funnyMovies

    init {
        fetchPopularMovies()
        fetchScaryMovies()
        fetchFunnyMovies()
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            try {
                val movies = movieRepository.getPopularMovies()
                _popularMovies.value = movies
            } catch (e: Exception) {
                println("Error fetching popular movies: ${e.message}")
            }
        }
    }

    private fun fetchScaryMovies() {
        viewModelScope.launch {
            try {
                val scaryMovies = movieRepository.getScaryMovies()
                _scaryMovies.value = scaryMovies
            } catch (e: Exception) {
                println("Error fetching scary movies: ${e.message}")
            }
        }
    }

    private fun fetchFunnyMovies() {
        viewModelScope.launch {
            try {
                val funnyMovies = movieRepository.getFunnyMovies()
                _funnyMovies.value = funnyMovies
            } catch (e: Exception) {
                println("Error fetching funny movies: ${e.message}")
            }
        }
    }
}
