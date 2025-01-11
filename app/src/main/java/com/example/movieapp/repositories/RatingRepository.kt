package com.example.movieapp.repositories

import com.example.movieapp.data.model.Rating
import com.example.movieapp.data.model.UserProfile

class RatingsRepository(private val userRepository: UserRepository) {
    private var userProfile: UserProfile = userRepository.getUserProfile()

    // Cache for quick access to ratings
    private val ratingsMap: MutableMap<Int, Rating> = mutableMapOf()

    init {
        // Populate ratings map for quick lookups
        userProfile.ratings.forEach { rating ->
            ratingsMap[rating.movieId] = rating
        }
    }

   // add or update a rating
    fun addRating(movieId: Int, rating: Float): Boolean {
        if (rating < 0 || rating > 5) {
            println("Invalid rating value: $rating. Must be between 0 and 5.")
            return false
        }

        val existingRating = ratingsMap[movieId]
        return if (existingRating != null) {
            // Update the existing rating
            existingRating.rating = rating
            userRepository.saveUserProfile(userProfile) // Persist updated profile
            false
        } else {
            // Add a new rating
            val newRating = Rating(movieId, rating)
            userProfile.ratings.add(newRating)
            ratingsMap[movieId] = newRating
            userRepository.saveUserProfile(userProfile) // Persist updated profile
            true
        }
    }

    // Retrieve all ratings
    fun getRatings(): List<Rating> {
        return ratingsMap.values.toList()
    }

    // Retrieve a specific movie's rating
    fun getRatingForMovie(movieId: Int): Float? {
        return ratingsMap[movieId]?.rating
    }
}