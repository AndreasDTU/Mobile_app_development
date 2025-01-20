package com.example.movieapp.data.model

import com.squareup.moshi.Json

data class Movie(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "overview") val overview: String? = null,
    @Json(name = "poster_path") val posterPath: String? = null,
    @Json(name = "vote_average") val voteAverage: Double? = null,
    @Json(name = "release_date") val releaseDate: String? = null,
    @Json(name = "genres") val genres: List<Genre> = emptyList(), // Default to empty list
    @Json(name = "runtime") val runtime: Int? = null,
    @Json(name = "status") val status: String? = null,
    @Json(name = "cast") val cast: List<Cast> = emptyList(), // Default to empty list
    @Json(name = "keywords") val keywords: List<Keyword> = emptyList(), // Add keywords with default
    var score: Int = 0, // Local property for scoring
    var isLiked: Boolean = false // Local property for UI state
)