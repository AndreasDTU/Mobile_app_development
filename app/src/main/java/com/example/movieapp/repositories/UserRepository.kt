package com.example.movieapp.repositories

import android.content.SharedPreferences
import com.example.movieapp.data.model.Rating
import com.example.movieapp.data.model.UserProfile
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File

class UserRepository {
    private val filePath = "user_profile.json"  // File path for storing the user profile in JSON
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val adapter = moshi.adapter(UserProfile::class.java)
    private var cachedUserProfile: UserProfile? = null // In-memory cache for the user profile

    // Fetch user profile from cache or from the JSON file
    fun getUserProfile(): UserProfile? {
        // First, check the cache
        if (cachedUserProfile != null) return cachedUserProfile

        // If no cached profile, load from file
        val file = File(filePath)
        if (!file.exists()) return null // Return null if no file exists

        val json = file.readText()
        return if (json.isNotBlank()) {
            try {
                // Parse JSON into UserProfile
                cachedUserProfile = Gson().fromJson(json, UserProfile::class.java)
                cachedUserProfile // Return the profile from file
            } catch (e: Exception) {
                null // Handle parsing errors gracefully
            }
        } else {
            null
        }
    }

    // Save the user profile to both memory cache and JSON file
    fun saveUserProfile(userProfile: UserProfile) {
        cachedUserProfile = userProfile // Update the in-memory cache

        // Save the user profile to the JSON file
        val json = Gson().toJson(userProfile)
        File(filePath).writeText(json)

        println("Saved Profile: $json") // Debug log
    }

    // Add or update a rating for a movie
    fun addRating(movieId: Int, title: String, posterPath: String, rating: Float) {
        val userProfile = getUserProfile() ?: createDefaultUserProfile()
        val existingRating = userProfile.ratings.find { it.movieId == movieId }
        if (existingRating != null) {
            existingRating.rating = rating // Update the rating if it exists
        } else {
            userProfile.ratings.add(Rating(movieId, title, posterPath, rating)) // Add a new rating
        }
        saveUserProfile(userProfile) // Update both cache and file
    }

    // Retrieve a specific movie's rating
    fun getRatingForMovie(movieId: Int): Float? {
        return getUserProfile()?.ratings?.find { it.movieId == movieId }?.rating
    }

    // Retrieve all ratings
    fun getAllRatings(): List<Rating> {
        return getUserProfile()?.ratings ?: emptyList()
    }

    // Helper: Create a default user profile if needed
    private fun createDefaultUserProfile(): UserProfile {
        return UserProfile(
            name = "Unknown",
            age = 0,
            location = "Unknown",
            favoriteGenre = "Unknown",
            ratings = mutableListOf()
        )
    }
}