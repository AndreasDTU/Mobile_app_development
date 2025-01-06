package com.example.movieapp.ui.screen.mylist

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.Movie
import com.example.movieapp.repositories.MovieRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class MyListViewModel(private val repository: MovieRepository) : ViewModel() {
    private val gson = Gson()

    val myList = mutableStateListOf<Movie>()
    val recentlyWatched = mutableStateListOf<Movie>()
    val favorites = mutableStateListOf<Movie>()

    init {
        loadMovies() // Load movies when ViewModel is initialized
    }

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

    fun toggleLike(movie: Movie) {
        val index = myList.indexOf(movie)
        if (index != -1) {
            val updatedMovie = movie.copy(isLiked = !movie.isLiked)
            myList[index] = updatedMovie

            if (updatedMovie.isLiked) {
                favorites.add(updatedMovie)
            } else {
                favorites.removeIf { it.id == updatedMovie.id }
            }

            saveMovies()
        }
    }

    private fun saveMovies() {
        val favoritesJson = gson.toJson(favorites)
        repository.saveMovies("favorites", favorites)
    }

    private fun loadMovies() {
        favorites.addAll(repository.loadMovies("favorites"))
    }
}
