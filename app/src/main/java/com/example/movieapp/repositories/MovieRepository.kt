package com.example.movieapp.repositories
import android.content.Context
import com.example.movieapp.data.remote.ApiClient
import com.example.movieapp.data.model.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.movieapp.utils.Constants.API_KEY

class MovieRepository(private val context: Context) {
    private val apiService = ApiClient.instance
    private val sharedPreferences = context.getSharedPreferences("movie_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    suspend fun getPopularMovies(): List<Movie> {
        val response = apiService.getPopularMovies(API_KEY)
        if (response.isSuccessful) {
            return response.body()?.results ?: emptyList()
        }
        return emptyList()
    }

    // Save movies locally
    fun saveMovies(key: String, movies: List<Movie>) {
        val json = gson.toJson(movies)
        sharedPreferences.edit().putString(key, json).apply()
    }

    // Load movies locally
    fun loadMovies(key: String): List<Movie> {
        val json = sharedPreferences.getString(key, null) ?: return emptyList()
        val type = object : TypeToken<List<Movie>>() {}.type
        return gson.fromJson(json, type)
    }

    // Other API methods...
    suspend fun getScaryMovies(): List<Movie> {
        val response = apiService.getScaryMovies(API_KEY)
        if (response.isSuccessful) {
            val body = response.body()
            return body?.results ?: emptyList()
        } else {
            return emptyList()
        }
    }

    suspend fun getFunnyMovies(): List<Movie> {
        val response = apiService.getFunnyMovies(API_KEY)
        if (response.isSuccessful) {
            val body = response.body()
            return body?.results ?: emptyList()
        } else {
            return emptyList()
        }
    }

    suspend fun getMovieDetails(id: Int): Movie {
        val response = apiService.getMovieDetails(id, API_KEY)
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            } ?: throw Exception("No movie details found")
        } else {
            throw Exception("Failed to load movie details: ${response.message()}")
        }
    }
}
