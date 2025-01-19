package com.example.movieapp.ui.screen.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.Movie
import com.example.movieapp.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieId: Int,
    private val repository: MovieRepository
) : ViewModel() {

    private val _movieDetails = MutableStateFlow<Movie?>(null)
    val movieDetails: StateFlow<Movie?> get() = _movieDetails
    private val _similarMovies = MutableStateFlow<List<Movie>>(emptyList())
    val similarMovies: StateFlow<List<Movie>> = _similarMovies
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        fetchMovieDetails()
    }

    private fun fetchMovieDetails() {
        viewModelScope.launch {
            try {
                val movie = repository.getMovieDetails(movieId)
                _movieDetails.value = movie
            } catch (e: Exception) {
                println("Error fetching movie details: ${e.message}")
            }
        }
    }
    fun fetchSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            try {
                val movieDetails = repository.getMovieDetails(movieId)
                val genres = movieDetails.genres.map { it.id }
                val castIds = movieDetails.cast.map { it.id }
                val keywordIds = movieDetails.keywords.map { it.id }

                // Fetch movies based on different criteria
                val genreBased = repository.getMoviesByGenres(genres)
                val castBased = repository.getMoviesByCast(castIds.take(5)) // Limit cast-based fetch
                val keywordBased = repository.getMoviesByKeywords(keywordIds)

                // Combine results and score them
                val allMovies = (genreBased + castBased + keywordBased).distinct()
                val scoredMovies = scoreMovies(allMovies, genres, castIds, keywordIds)

                // Exclude the current movie
                val filteredMovies = scoredMovies.filter { it.id != movieId }

                // Update the state with top recommendations
                _similarMovies.value = filteredMovies.take(10) // Limit to top 10
            } catch (e: Exception) {
                // Handle errors
            }
        }
    }
    fun scoreMovies(
        movies: List<Movie>,
        genres: List<Int>,
        castIds: List<Int>,
        keywordIds: List<Int>
    ): List<Movie> {
        return movies.map { movie ->
            var score = 0

            // Safely handle nullable fields with default values
            val matchingGenres = movie.genres?.count { it.id in genres } ?: 0
            score += matchingGenres * 3

            val matchingCast = movie.cast?.count { it.id in castIds } ?: 0
            score += matchingCast * 2

            val matchingKeywords = movie.keywords?.count { it.id in keywordIds } ?: 0
            score += matchingKeywords * 1

            // Update the score field and return the movie
            movie.copy(score = score)
        }.sortedByDescending { it.score } // Sort movies by their score in descending order
    }

    suspend fun getMovieTrailer(movieId: Int): String? {
        return repository.getMovieTrailer(movieId)
    }
}