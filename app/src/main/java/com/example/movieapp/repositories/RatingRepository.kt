package com.example.movieapp.repositories

import android.content.Context
import com.example.movieapp.data.model.Rating
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class RatingsRepository(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("ratings_prefs", Context.MODE_PRIVATE) // * SharedPreferences for ratings
    private val gson = Gson()

    private val ratingsMap: MutableMap<Int, Rating> = mutableMapOf()

    init {
        loadRatings() // * Load ratings from SharedPreferences
    }


    // Add or update a rating
    fun addRating(movieId: Int, movieTitle: String, posterPath: String, rating: Float): Boolean {
        if (rating < 0 || rating > 5) {
            println("Invalid rating value: $rating. Must be between 0 and 5.")
            return false
        }

        val existingRating = ratingsMap[movieId]
        if (existingRating != null) {
            existingRating.rating = rating // * Update existing rating
        } else {
            val newRating = Rating(movieId, movieTitle, posterPath, rating)
            ratingsMap[movieId] = newRating // * Add new rating to the map
        }

        saveRatings() // * Save updated ratings to SharedPreferences
        return true
    }

    // Retrieve all ratings
    fun getRatings(): List<Rating> {
        return ratingsMap.values.toList()
    }

// Retrieve a specific movie's rating
fun getRatingForMovie(movieId: Int): Rating? {
    return ratingsMap[movieId]
}

// Save ratings to SharedPreferences
private fun saveRatings() {
    val ratingsList = ratingsMap.values.toList()
    val json = gson.toJson(ratingsList) // * Convert ratings to JSON
    sharedPreferences.edit().putString("ratings", json).apply() // * Save JSON to SharedPreferences
}

// Load ratings from SharedPreferences
private fun loadRatings() {
    val json = sharedPreferences.getString("ratings", null) ?: return // * Load JSON string
    val type = object : TypeToken<List<Rating>>() {}.type
    val ratingsList: List<Rating> = gson.fromJson(json, type) // * Convert JSON back to Rating objects
    ratingsList.forEach { rating ->
        ratingsMap[rating.movieId] = rating
    }
}
}