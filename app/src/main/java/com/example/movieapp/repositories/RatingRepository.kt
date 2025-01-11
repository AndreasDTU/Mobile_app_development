package com.example.movieapp.repositories

import com.example.movieapp.data.model.Rating
import com.example.movieapp.data.model.UserProfile

class RatingsRepository(private val userRepository: UserRepository) {
    private var userProfile: UserProfile = userRepository.getUserProfile()

    // Add or update a rating
    fun addRating(movieId: Int, rating: Float) {
        val existingRating = userProfile.ratings.find { it.movieId == movieId }
        if (existingRating != null) {
            existingRating.rating = rating // Update existing rating
        } else {
            userProfile.ratings.add(Rating(movieId, rating)) // Add new rating
        }
        userRepository.saveUserProfile(userProfile) // Persist updated user profile
    }

    // Retrieve all ratings
    fun getRatings(): List<Rating> {
        return userProfile.ratings
    }

    // Retrieve a specific movie's rating
    fun getRatingForMovie(movieId: Int): Float? {
        return userProfile.ratings.find { it.movieId == movieId }?.rating
    }
}
