package com.example.movieapp.ui.screen.mylist

import android.util.Log
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
        if (favorites.removeIf { it.id == movie.id }) {
            saveMovies()
        } else {
            favorites.add(movie)
            saveMovies()
        }
    }

    private fun saveMovies() {
        repository.saveMovies("favorites", favorites)
    }

    private fun loadMovies() {
        try {
            favorites.addAll(repository.loadMovies("favorites"))
        } catch (e: Exception) {
            Log.e("MyListViewModel", "Error loading favorites: ${e.message}")
        }
    }

}