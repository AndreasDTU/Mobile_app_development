package com.example.movieapp.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.Movie
import com.example.movieapp.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                val results = repository.searchMovies(query)
                _searchResults.value = results
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to search movies: ${e.message}"
            }
        }
    }
}
