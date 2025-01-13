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
    val popularMovies: StateFlow<List<Movie>> = _popularMovies
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _scaryMovies = MutableStateFlow<List<Movie>>(emptyList())
    val scaryMovies: StateFlow<List<Movie>> = _scaryMovies
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _funnyMovies = MutableStateFlow<List<Movie>>(emptyList())
    val funnyMovies: StateFlow<List<Movie>> = _funnyMovies
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _topMovie = MutableStateFlow<Movie?>(null)
    val topMovie: StateFlow<Movie?> = _topMovie
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        fetchPopularMovies()
        fetchScaryMovies()
        fetchFunnyMovies()
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val movies = movieRepository.getPopularMovies()
                _popularMovies.value = movies
                _topMovie.value = movies.firstOrNull()
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load popular movies: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun fetchScaryMovies() {
        viewModelScope.launch {
            try {
                val scaryMovies = movieRepository.getScaryMovies()
                _scaryMovies.value = scaryMovies
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load scary movies: ${e.message}"
            }
        }
    }

    private fun fetchFunnyMovies() {
        viewModelScope.launch {
            try {
                val funnyMovies = movieRepository.getFunnyMovies()
                _funnyMovies.value = funnyMovies
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load funny movies: ${e.message}"
            }
        }
    }
}
