package com.example.movieapp.repositories

import android.content.SharedPreferences
import com.example.movieapp.data.model.Rating
import com.example.movieapp.data.model.UserProfile
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class UserRepository() {
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val adapter = moshi.adapter(UserProfile::class.java)
    private var cachedUserProfile: UserProfile? = null // In-memory storage for the user profile


    // Example JSON string representing a user profile
    private val userProfileJson = """
        {
            "name": "Unknown",
            "age": 0,
            "location": "Unknown",
            "favorite_genre": "Unknown",
             "ratings": []
        }
    """

    // Deserialize JSON to UserProfile object (or use cached if available)
    fun getUserProfile(): UserProfile {
        if (cachedUserProfile == null) {
            cachedUserProfile = adapter.fromJson(userProfileJson)
        }
        return cachedUserProfile ?: UserProfile("Unknown", 0, "Unknown", "Unknown")
    }

    // Serialize UserProfile object to JSON (only for debugging/logging purposes)
    fun saveUserProfile(userProfile: UserProfile) {
        cachedUserProfile = userProfile // Update the in-memory cache
        println("Saved Profile: ${adapter.toJson(userProfile)}") // Debug log
    }

    // Add or update a rating for a movie
    fun addRating(movieId: Int, title: String, posterPath: String, rating: Float) {
        val userProfile = getUserProfile()
        val existingRating = userProfile.ratings.find { it.movieId == movieId }
        if (existingRating != null) {
            existingRating.rating = rating // Update the rating if it exists
        } else {
            userProfile.ratings.add(Rating(movieId, title, posterPath, rating)) // Add a new rating
        }
        saveUserProfile(userProfile) // Update the cache
    }
    // Retrieve a specific movie's rating
    fun getRatingForMovie(movieId: Int): Float? {
        return getUserProfile().ratings.find { it.movieId == movieId }?.rating
    }

    // Retrieve all ratings
    fun getAllRatings(): List<Rating> {
        return getUserProfile().ratings
    }
}