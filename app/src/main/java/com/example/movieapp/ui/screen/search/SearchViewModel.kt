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

    fun searchMoviesWithFilters(query: String, year: String, genre: String, sort: String) {
        viewModelScope.launch {
            try {
                val allResults = repository.searchMovies(query)

                val filteredByYear = if (year.isNotEmpty() && year != "All") {
                    allResults.filter { movie ->
                        val releaseYear = movie.releaseDate?.take(4)
                        releaseYear == year
                    }
                } else allResults

                val filteredByGenre = if (genre != "All") {
                    filteredByYear.filter { movie ->
                        movie.genres?.any { it.name.equals(genre, ignoreCase = true) } == true
                    }
                } else filteredByYear

                val sortedResults = when (sort) {
                    "Release Date" -> filteredByGenre.sortedByDescending { it.releaseDate }
                    "Rating" -> filteredByGenre.sortedByDescending { it.voteAverage }
                    else -> filteredByGenre
                }

                _searchResults.value = sortedResults
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to search movies: ${e.message}"
            }
        }
    }
}
