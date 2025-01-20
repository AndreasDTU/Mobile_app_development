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
                Log.d("SearchViewModel", "Query: $query, Year: $year, Genre: $genre")

                // Check if the query is provided
                val results = if (query.isNotBlank()) {
                    // Search by query
                    repository.searchMovies(query)
                } else {
                    // If no query, use year and genre filters
                    val genreMap = mapOf(
                        "Action" to 28,
                        "Comedy" to 35,
                        "Drama" to 18,
                        "Horror" to 27,
                        "Sci-Fi" to 878
                    )
                    val genreId = if (genre != "All") genreMap[genre] else null
                    repository.getMoviesByYearAndGenre(year, genreId)
                }

                // Update results
                _searchResults.value = results
                _errorMessage.value = if (results.isEmpty()) "No movies found" else null

                Log.d("SearchViewModel", "Results size: ${results.size}")

            } catch (e: Exception) {
                Log.e("SearchViewModel", "Error during search: ${e.message}")
                _errorMessage.value = "Failed to search movies: ${e.message}"
            }
        }
    }
}