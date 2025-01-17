package com.example.movieapp.data.model

import com.squareup.moshi.Json

data class Movie(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "overview") val overview: String?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "vote_average") val voteAverage: Double?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "genres") val genres: List<Genre>?,
    @Json(name = "runtime") val runtime: Int?,
    @Json(name = "status") val status: String?,
    @Json(name = "cast") val cast: List<Cast>?,
    var isLiked: Boolean = false // Local property
)