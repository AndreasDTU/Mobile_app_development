package com.example.movieapp.data.model

import com.squareup.moshi.Json

data class Movie(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "poster_path") val posterPath: String?, // Correct field name
    @Json(name = "vote_average") val voteAverage: Double, // Correct field name
    @Json(name = "release_date") val releaseDate: String, // Correct field name
    var isLiked: Boolean = false // Local property
)