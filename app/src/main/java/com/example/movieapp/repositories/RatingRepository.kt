package com.example.movieapp.repositories
import android.content.Context
import com.example.movieapp.data.model.Rating
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class RatingRepository(private val context: Context) {
    private val moshi = Moshi.Builder().build()
    private val ratingListType = Types.newParameterizedType(List::class.java, Rating::class.java)
    private val adapter = moshi.adapter<List<Rating>>(ratingListType)

    private val fileName = "ratings.json"

    fun saveRatings(ratings: List<Rating>) {
        val json = adapter.toJson(ratings)
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
    }

    fun loadRatings(): List<Rating> {
        return try {
            val file = context.getFileStreamPath(fileName)
            if (file.exists()) {
                val json = context.openFileInput(fileName).bufferedReader().use { it.readText() }
                adapter.fromJson(json) ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
