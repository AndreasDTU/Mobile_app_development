package com.example.movieapp.repositories

import android.content.Context
import android.util.Log
import com.example.movieapp.data.remote.ApiClient
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieResponse
import retrofit2.Response
import com.example.movieapp.utils.Constants.API_KEY
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MovieRepository(private val context: Context) {
    private val apiService = ApiClient.instance
    private val sharedPreferences =
        context.getSharedPreferences("movie_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()


    suspend fun getPopularMovies(): List<Movie> {
        val response = apiService.getPopularMovies(API_KEY)
        Log.d("Movies", response.toString())
        if (response.isSuccessful) {
            return response.body()?.results ?: emptyList()
        } else {
            return emptyList()
        }
    }

    fun saveMovies(key: String, movies: List<Movie>) {
        val json = gson.toJson(movies)
        sharedPreferences.edit().putString(key, json).apply()
    }

    fun loadMovies(key: String): List<Movie> {
        val json = sharedPreferences.getString(key, null) ?: return emptyList()
        val type = object : TypeToken<List<Movie>>() {}.type
        return gson.fromJson(json, type)
    }


    suspend fun getMovieDetails(id: Int): Movie {
        val movieResponse = apiService.getMovieDetails(id, API_KEY)
        if (!movieResponse.isSuccessful) {
            throw Exception("Failed to load movie details: ${movieResponse.message()}")
        }
        val movie = movieResponse.body() ?: throw Exception("No movie details found")

        val castResponse = apiService.getMovieCast(id, API_KEY)
        val cast = castResponse.body()?.cast ?: emptyList()

        val keywordResponse = apiService.getMovieKeywords(id, API_KEY)
        val keywords = keywordResponse.body()?.keywords ?: emptyList()

        return movie.copy(cast = cast, keywords = keywords)
    }

    suspend fun searchMovies(query: String): List<Movie> {
        val response = apiService.searchMovies(API_KEY, query)
        if (response.isSuccessful) {
            val results = response.body()?.results ?: emptyList()
            Log.d("MovieRepository", "Search results: ${results.size}")
            return results
        } else {
            Log.e("MovieRepository", "Search failed: ${response.message()}")
            return emptyList()
        }
    }


    suspend fun getMovieByGenre(genre: Int): List<Movie> {
        val response = apiService.getMovieByGenre(API_KEY, genre, "en-US")
        if (response.isSuccessful) {
            val body = response.body()
            Log.d("MovieRepository", "Response Body: $body") // Log the entire response body
            // Log the results to check poster paths
            return body?.results ?: emptyList()
        } else {
            Log.e("MovieRepository", "Error fetching movies: ${response.errorBody()?.string()}")
            return emptyList()
        }
    }

    suspend fun getMovieTrailer(movieId: Int): String? {
        val response = apiService.getMovieVideos(movieId, API_KEY)
        if (response.isSuccessful) {
            val videos = response.body()?.results
            return videos?.firstOrNull { it.type == "Trailer" && it.site == "YouTube" }?.key
        }
        return null
    }


    suspend fun getMoviesByGenres(genreIds: List<Int>): List<Movie> {
        val response =
            apiService.discoverMovies(API_KEY, mapOf("with_genres" to genreIds.joinToString(",")))
        if (response.isSuccessful) {
            return response.body()?.results ?: emptyList()
        }
        return emptyList()
    }

    suspend fun getMoviesByCast(castIds: List<Int>): List<Movie> {
        val response =
            apiService.discoverMovies(API_KEY, mapOf("with_cast" to castIds.joinToString(",")))
        if (response.isSuccessful) {
            return response.body()?.results ?: emptyList()
        }
        return emptyList()
    }

    suspend fun getMoviesByKeywords(keywordIds: List<Int>): List<Movie> {
        val response = apiService.discoverMovies(
            API_KEY,
            mapOf("with_keywords" to keywordIds.joinToString(","))
        )
        if (response.isSuccessful) {
            return response.body()?.results ?: emptyList()
        }
        return emptyList()
    }

    suspend fun getMoviesByYearAndGenre(year: String, genreId: Int?): List<Movie> {
        val response = if (genreId != null) {
            apiService.getMovieByGenre(apiKey = API_KEY, genre = genreId, page = 1)
        } else {
            apiService.getPopularMovies(apiKey = API_KEY, page = 1)
        }

        Log.d("MovieRepository", "API response successful: ${response.isSuccessful}")

        if (response.isSuccessful) {
            // Log the full response for debugging
            Log.d("MovieRepository", "Response Body: ${response.body()}")

            // Filter movies by year
            val movies = response.body()?.results?.filter {
                year == "All" || it.releaseDate?.startsWith(year) == true
            }

            Log.d("MovieRepository", "Movies filtered by year: ${movies?.size}")
            return movies ?: emptyList()
        } else {
            // Log the error details
            Log.e("MovieRepository", "Error: ${response.errorBody()?.string()}")
            return emptyList()
        }
    }
}

