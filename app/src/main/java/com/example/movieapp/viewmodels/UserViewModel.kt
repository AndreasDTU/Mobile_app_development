package com.example.movieapp.viewmodels
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.Rating
import com.example.movieapp.repositories.RatingRepository
import com.example.movieapp.data.model.UserProfile
import com.example.movieapp.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class UserViewModel(
    private val userRepository: UserRepository = UserRepository(),
    private val ratingRepository: RatingRepository = RatingRepository(context)
) : ViewModel() {


    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile

    private val _userRatings = MutableStateFlow<List<Rating>>(emptyList())
    val userRatings: StateFlow<List<Rating>> = _userRatings

    init {
        fetchUserProfile()
        loadUserRatings()

    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            val profile = userRepository.getUserProfile()
            _userProfile.value = profile
        }
    }

    fun saveUserProfile(updatedProfile: UserProfile) {
        viewModelScope.launch {
            userRepository.saveUserProfile(updatedProfile)
            _userProfile.value = updatedProfile
        }
    }
    fun loadUserRatings() {
        viewModelScope.launch {
            _userRatings.value = ratingRepository.loadRatings()
        }
    }

    fun addOrUpdateRating(mediaId: String, movieTitle: String, rating: Double) {
        viewModelScope.launch {
            val currentRatings = _userRatings.value.toMutableList()
            val existingRatingIndex = currentRatings.indexOfFirst { it.mediaId == mediaId }

            if (existingRatingIndex >= 0) {
                currentRatings[existingRatingIndex] = Rating(mediaId, movieTitle, rating)
            } else {
                currentRatings.add(Rating(mediaId, movieTitle, rating))
            }

            _userRatings.value = currentRatings
            ratingRepository.saveRatings(currentRatings)
        }
    }
}