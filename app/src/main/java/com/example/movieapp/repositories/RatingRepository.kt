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

    // Add or update a rating
    fun addRating(movieId: Int, movieTitle: String, posterPath: String, rating: Float): Boolean {
        if (rating < 0 || rating > 5) {
            println("Invalid rating value: $rating. Must be between 0 and 5.")
            return false
        }

        val existingRating = ratingsMap[movieId]
        if (existingRating != null) {
            // Update the existing rating
            existingRating.rating = rating
        } else {
            // Add a new rating with movie details
            val newRating = Rating(movieId, movieTitle, posterPath, rating)
            userProfile.ratings.add(newRating)
            ratingsMap[movieId] = newRating
        }

        // Persist updated profile
        userRepository.saveUserProfile(userProfile)
        return true
    }

    // Retrieve all ratings
    fun getRatings(): List<Rating> {
        return ratingsMap.values.toList()
    }

    // Retrieve a specific movie's rating (return full `Rating` object)
    fun getRatingForMovie(movieId: Int): Rating? {
        return ratingsMap[movieId]
    }
}
