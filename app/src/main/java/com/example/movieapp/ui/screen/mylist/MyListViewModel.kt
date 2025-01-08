package com.example.movieapp.ui.screen.mylist

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.Movie
import com.example.movieapp.repositories.MovieRepository
import kotlinx.coroutines.launch

class MyListViewModel(private val repository: MovieRepository) : ViewModel() {

    val myList = mutableStateListOf<Movie>()
    val recentlyWatched = mutableStateListOf<Movie>()
    val favorites = mutableStateListOf<Movie>()

    init {
        loadMovies() // Load movies when ViewModel is initialized
    }

    // not used (yet)
    @Suppress("unused")
    fun fetchMovies() {
        viewModelScope.launch {
            try {
                val movies = repository.getPopularMovies()
                myList.addAll(movies)
            } catch (e: Exception) {
                println("Error fetching movies: ${e.message}")
            }
        }
    }

    fun isMovieLiked(movie: Movie): Boolean {
        return favorites.any { it.id == movie.id }
    }

    fun toggleLike(movie: Movie) {
        val isAlreadyLiked = favorites.any { it.id == movie.id }

        if (isAlreadyLiked) {
            // Remove movie from favorites
            favorites.removeIf { it.id == movie.id }
        } else {
            // Add movie to favorites
            favorites.add(movie)
        }

        // Save changes
        saveMovies()
    }

    private fun saveMovies() {
        repository.saveMovies("favorites", favorites)
    }

    private fun loadMovies() {
        favorites.addAll(repository.loadMovies("favorites"))
    }
}
