package com.example.movieapp.data.model

import com.squareup.moshi.Json

data class Rating(
    @Json(name = "movie_id") val movieId: Int, // Movie ID
    @Json(name = "title") val title: String,  // Movie title
    @Json(name = "poster_path") val posterPath: String, // Poster path for the movie
    @Json(name = "rating") var rating: Float // User's rating
)
