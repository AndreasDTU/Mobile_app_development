package com.example.movieapp.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.Movie
import com.example.movieapp.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class SearchViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun searchMoviesWithFilters(query: String, year: String, genre: String) {
        viewModelScope.launch {
            try {
                // Fetch all movies (use query if provided, otherwise fetch all)
                val allResults = if (query.isNotBlank()) {
                    repository.searchMovies(query)
                } else {
                    repository.getPopularMovies() // Fetch all popular movies when no query
                }

                // Filter by year
                val filteredByYear = if (year.isNotEmpty() && year != "All") {
                    allResults.filter { it.releaseDate?.startsWith(year) == true }
                } else {
                    allResults
                }

                // Map genres to IDs
                val genreMap = mapOf(
                    "Action" to 28,
                    "Comedy" to 35,
                    "Drama" to 18,
                    "Horror" to 27,
                    "Sci-Fi" to 878
                )

                // Filter by genre
                val filteredByGenre = if (genre != "All") {
                    filteredByYear.filter { movie ->
                        movie.genres?.any { it.name.equals(genre, ignoreCase = true) } == true
                    }
                } else {
                    filteredByYear // When "All" is selected, bypass genre filtering
                }

                // Update the search results
                _searchResults.value = filteredByGenre
                _errorMessage.value = if (filteredByGenre.isEmpty()) "No movies found" else null

                // Debug logs
                Log.d("SearchViewModel", "Results size: ${filteredByGenre.size}")
            } catch (e: Exception) {
                // Handle errors
                Log.e("SearchViewModel", "Error during search: ${e.message}")
                _errorMessage.value = "Failed to search movies: ${e.message}"
            }
        }
    }
}