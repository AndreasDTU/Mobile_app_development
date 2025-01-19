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
                val genres = movieDetails.genres?.map { it.id } ?: emptyList()
                val castIds = movieDetails.cast?.map { it.id } ?: emptyList()
                val keywordIds = movieDetails.keywords?.map { it.id } ?: emptyList()

                // Fetch movies based on genres, cast, and keywords
                val genreBased = repository.getMoviesByGenres(genres)
                val castBased = repository.getMoviesByCast(castIds.take(5)) // Limit cast fetch
                val keywordBased = repository.getMoviesByKeywords(keywordIds)

                // Combine and score movies
                val allMovies = (genreBased + castBased + keywordBased).distinct()
                val scoredMovies = scoreMovies(allMovies, genres, castIds, keywordIds)

                _similarMovies.value = scoredMovies.take(10) // Top 10 recommendations
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