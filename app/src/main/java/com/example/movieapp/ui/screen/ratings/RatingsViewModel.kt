package com.example.movieapp.ui.screen.ratings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.Rating
import com.example.movieapp.repositories.RatingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch




class RatingsViewModel(private val ratingsRepository: RatingsRepository) : ViewModel() {
    private val _ratings = MutableStateFlow<List<Rating>>(emptyList())
    val ratings: StateFlow<List<Rating>> = _ratings

    private val _averageRating = MutableStateFlow<Float?>(null) // Track the average rating
    val averageRating: StateFlow<Float?> = _averageRating


    init {
        loadRatings()
    }

    fun loadRatings() {
        viewModelScope.launch {
            _ratings.value = ratingsRepository.getRatings()
        }
    }

    fun addRating(movieId: Int, title: String, posterPath: String, rating: Float) {
        viewModelScope.launch {
            ratingsRepository.addRating(movieId, title, posterPath, rating)
            loadRatings() // Reload ratings after adding
            loadRatings() // Reload ratings after adding
            loadAverageRating(movieId) // Load the average rating
        }
    }
        fun loadAverageRating(movieId: Int) {
            viewModelScope.launch {
                val avgRating = ratingsRepository.getAverageRating(movieId)
                _averageRating.value = avgRating
            }
        }


        fun getRatingForMovie(movieId: Int): Rating? {
        return ratingsRepository.getRatingForMovie(movieId)
    }

    fun removeRating(movieId: Int) {
        viewModelScope.launch {
            val isRemoved = ratingsRepository.removeRating(movieId)
            if (isRemoved) {
                // Reload the average rating after removal
                loadAverageRating(movieId) // Refresh the average rating immediately
            }
        }
    }
}
