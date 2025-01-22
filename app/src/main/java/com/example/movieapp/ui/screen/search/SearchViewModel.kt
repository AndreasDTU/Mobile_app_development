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
                Log.d("SearchViewModel", "Starting search with query='$query', year='$year', genre='$genre'")

                // Fetch movies from API
                val allResults = if (query.isNotBlank()) {
                    repository.searchMovies(query)
                } else {
                    repository.getPopularMovies()
                }

                // Filter by year
                val filteredByYear = if (year != "All" && year.isNotEmpty()) {
                    allResults.filter { it.releaseDate?.startsWith(year) == true }
                } else {
                    allResults
                }

                // Filter by genre
                val genreMap = mapOf(
                    "Action" to 28,
                    "Comedy" to 35,
                    "Drama" to 18,
                    "Horror" to 27,
                    "Sci-Fi" to 878
                )
                val genreId = genreMap[genre]
                val filteredByGenre = if (genre != "All" && genreId != null) {
                    filteredByYear.filter { movie ->
                        movie.genres?.any { it.id == genreId } == true
                    }
                } else {
                    filteredByYear
                }

                // Update results
                _searchResults.value = filteredByGenre
                _errorMessage.value = if (filteredByGenre.isEmpty()) "No movies found" else null

                Log.d("SearchViewModel", "Search completed: ${filteredByGenre.size} movies found.")
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Error during search: ${e.message}")
                _errorMessage.value = "Failed to search movies: ${e.message}"
            }
        }
    }
}
