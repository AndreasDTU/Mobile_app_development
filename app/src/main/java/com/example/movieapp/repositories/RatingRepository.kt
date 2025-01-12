package com.example.movieapp.repositories

import com.example.movieapp.data.model.Rating
import com.example.movieapp.data.model.UserProfile

class RatingsRepository(private val userRepository: UserRepository) {

    fun addRating(movieId: Int, rating: Float): Boolean {
        val userProfile = userRepository.getUserProfile()
        val existingRating = userProfile.ratings.find { it.movieId == movieId }
        if (existingRating != null) {
            existingRating.rating = rating // Update the existing rating
        } else {
            userProfile.ratings.add(Rating(movieId, rating)) // Add a new rating
        }
        userRepository.saveUserProfile(userProfile) // Persist changes
        return true
    }

    fun getRatings(): List<Rating> {
        return userRepository.getUserProfile().ratings
    }

    fun getRatingForMovie(movieId: Int): Float? {
        return userRepository.getUserProfile().ratings.find { it.movieId == movieId }?.rating
    }
}