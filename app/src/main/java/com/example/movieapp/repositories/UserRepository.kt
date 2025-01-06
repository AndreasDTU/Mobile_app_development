package com.example.movieapp.repositories

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
            "favorite_genre": "Romantic"
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
}



