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

    init {
        loadRatings()
    }

    fun loadRatings() {
        viewModelScope.launch {
            _ratings.value = ratingsRepository.getRatings()
        }
    }

    fun addRating(movieId: Int, rating: Float) {
        viewModelScope.launch {
            ratingsRepository.addRating(movieId, rating)
            loadRatings() // Reload ratings after adding
        }
    }

    fun getRatingForMovie(movieId: Int): Float? {
        return ratingsRepository.getRatingForMovie(movieId)
    }
}
