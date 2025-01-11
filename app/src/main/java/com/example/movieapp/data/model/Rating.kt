package com.example.movieapp.data.model

import com.squareup.moshi.Json

data class Rating(
    @Json(name = "movie_id") val movieId: Int,
    @Json(name = "rating") var rating: Float
)
