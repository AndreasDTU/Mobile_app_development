package com.example.movieapp.repositories

import android.content.Context
import android.util.Log
import com.example.movieapp.data.model.Rating
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.tasks.await


class RatingsRepository(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("ratings_prefs", Context.MODE_PRIVATE) // * SharedPreferences for ratings
    private val gson = Gson()

    private val ratingsMap: MutableMap<Int, Rating> = mutableMapOf()



    // setup for firestore for ratings
    private val firestore = FirebaseFirestore.getInstance()
    private val ratingsCollection = firestore.collection("movieRatings") // * Collection in Firestore

    init {
        loadRatings() // * Load ratings from SharedPreferences
    }


    // Add or update a rating
    suspend fun addRating(movieId: Int, movieTitle: String, posterPath: String, rating: Float): Boolean {
        // Logging the input
        Log.d("FirestoreDebug", "Updating rating for MovieID: $movieId, Rating: $rating")

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

        // Update Firestore
        try {
            val document = ratingsCollection.document(movieId.toString())
            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(document)

                val totalRating = snapshot.getDouble("totalRating") ?: 0.0
                val ratingCount = snapshot.getDouble("ratingCount") ?: 0.0


                // Update totalRating and ratingCount in Firestore
                transaction.update(document, mapOf(
                    "totalRating" to totalRating + rating,
                    "ratingCount" to ratingCount + 1
                ))
            }.await()
            Log.d("FirestoreDebug", "Firestore successfully updated for MovieID: $movieId")

        } catch (e: Exception) {
            Log.e("FirestoreDebug", "Error updating Firestore: ${e.message}")

            //  If Firestore document doesn't exist, create it
            ratingsCollection.document(movieId.toString()).set(
                mapOf(
                    "totalRating" to rating.toDouble(),
                    "ratingCount" to 1.0,
                )
            ).await()
            Log.d("FirestoreDebug", "Firestore document created for MovieID: $movieId")

        }
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

    //  Fetch average rating from Firestore
    suspend fun getAverageRating(movieId: Int): Float {
        return try {
            val document = ratingsCollection.document(movieId.toString()).get().await()
            val totalRating = document.getDouble("totalRating") ?: 0.0
            val ratingCount = document.getDouble("ratingCount") ?: 0.0

            // * Calculate average rating
            if (ratingCount == 0.0) 0f else String.format("%.1f", totalRating / ratingCount).toFloat()        } catch (e: Exception) {
            0f // Return 0 if no document exists
        }
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
    val ratingsList: List<Rating> =
        gson.fromJson(json, type) // * Convert JSON back to Rating objects
    ratingsList.forEach { rating ->
        ratingsMap[rating.movieId] = rating
    }
}
    // Remove ratings
    suspend fun removeRating(movieId: Int): Boolean {
        val existingRating = ratingsMap[movieId] ?: return false // If no local rating exists, exit

        ratingsMap.remove(movieId)
        saveRatings()

        try {
            val document = ratingsCollection.document(movieId.toString())
            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(document)

                val totalRating = snapshot.getDouble("totalRating") ?: 0.0
                val ratingCount = snapshot.getDouble("ratingCount") ?: 0.0

                if (ratingCount > 0) {
                    val updatedTotalRating = totalRating - existingRating.rating
                    val updatedRatingCount = ratingCount - 1

                    transaction.update(document, mapOf(
                        "totalRating" to updatedTotalRating,
                        "ratingCount" to updatedRatingCount
                    ))
                }
            }.await()
            Log.d("FirestoreDebug", "Rating removed for MovieID: $movieId")
        } catch (e: Exception) {
            Log.e("FirestoreDebug", "Error removing rating from Firestore: ${e.message}")
            return false
        }

        return true
    }
}
