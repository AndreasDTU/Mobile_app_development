package com.example.movieapp.repositories

import com.example.movieapp.data.model.Rating
import com.example.movieapp.data.model.UserProfile
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class UserRepository {
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val adapter = moshi.adapter(UserProfile::class.java)

    // Example JSON string representing a user profile
    private val userProfileJson = """
        {
            "name": "Johnny",
            "age": 29,
            "location": "USA",
            "favorite_genre": "Romantic",
             "ratings": []
        }
    """

    // Deserialize JSON to UserProfile object
    fun getUserProfile(): UserProfile {
        return adapter.fromJson(userProfileJson) ?: UserProfile("Unknown", 0, "Unknown", "Unknown")
    }

    // Serialize UserProfile object to JSON
    fun saveUserProfile(userProfile: UserProfile) {
        val json = adapter.toJson(userProfile)
        // Save the JSON string to a file, database, or API call for next iteration (logic not implemented)
        println("Saved JSON: $json")
    }
    // Add or update a rating for a movie
    fun addRating(movieId: Int, rating: Float) { // * Method to add or update a movie rating
        val userProfile = getUserProfile()
        val existingRating = userProfile.ratings.find { it.movieId == movieId }
        if (existingRating != null) {
            existingRating.rating = rating // Update the rating if it exists
        } else {
            userProfile.ratings.add(Rating(movieId, rating)) // Add a new rating
        }
        saveUserProfile(userProfile) // Save the updated user profile
    }

    // Retrieve a specific movie's rating
    fun getRatingForMovie(movieId: Int): Float? { // * Fetch rating for a specific movie
        val userProfile = getUserProfile()
        return userProfile.ratings.find { it.movieId == movieId }?.rating
    }

    // Retrieve all ratings
    fun getAllRatings(): List<Rating> { // * Fetch all user ratings
        return getUserProfile().ratings
    }
}




