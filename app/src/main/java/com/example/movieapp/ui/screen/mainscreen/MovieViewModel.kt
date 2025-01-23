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
    // Existing movie flows
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

    // New movie flows for additional genres
    private val _actionMovies = MutableStateFlow<List<Movie>>(emptyList())
    val actionMovies: StateFlow<List<Movie>> = _actionMovies
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _dramaMovies = MutableStateFlow<List<Movie>>(emptyList())
    val dramaMovies: StateFlow<List<Movie>> = _dramaMovies
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _adventureMovies = MutableStateFlow<List<Movie>>(emptyList())
    val adventureMovies: StateFlow<List<Movie>> = _adventureMovies
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _romanceMovies = MutableStateFlow<List<Movie>>(emptyList())
    val romanceMovies: StateFlow<List<Movie>> = _romanceMovies
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _sciFiMovies = MutableStateFlow<List<Movie>>(emptyList())
    val sciFiMovies: StateFlow<List<Movie>> = _sciFiMovies
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    init {
        fetchall()
    }
    fun fetchall() {
        fetchPopularMovies()
        fetchScaryMovies()
        fetchFunnyMovies()
        fetchActionMovies()
        fetchDramaMovies()
        fetchAdventureMovies()
        fetchRomanceMovies()
        fetchSciFiMovies()
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
                val scaryMovies = movieRepository.getMovieByGenre(27)
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
                val funnyMovies = movieRepository.getMovieByGenre(35)
                _funnyMovies.value = funnyMovies
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load funny movies: ${e.message}"
            }
        }
    }

    // Fetch methods for new genres
    private fun fetchActionMovies() {
        viewModelScope.launch {
            try {
                val actionMovies = movieRepository.getMovieByGenre(28)
                _actionMovies.value = actionMovies
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load action movies: ${e.message}"
            }
        }
    }

    private fun fetchDramaMovies() {
        viewModelScope.launch {
            try {
                val dramaMovies = movieRepository.getMovieByGenre(18)
                _dramaMovies.value = dramaMovies
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load drama movies: ${e.message}"
            }
        }
    }

    private fun fetchAdventureMovies() {
        viewModelScope.launch {
            try {
                val adventureMovies = movieRepository.getMovieByGenre(12)
                _adventureMovies.value = adventureMovies
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load adventure movies: ${e.message}"
            }
        }
    }

    private fun fetchRomanceMovies() {
        viewModelScope.launch {
            try {
                val romanceMovies = movieRepository.getMovieByGenre(10749)
                _romanceMovies.value = romanceMovies
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load romance movies: ${e.message}"
            }
        }
    }

    private fun fetchSciFiMovies() {
        viewModelScope.launch {
            try {
                val sciFiMovies = movieRepository.getMovieByGenre(878)
                _sciFiMovies.value = sciFiMovies
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load science fiction movies: ${e.message}"
            }
        }
    }
}