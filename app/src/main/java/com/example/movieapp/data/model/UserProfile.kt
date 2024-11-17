package com.example.movieapp.data.model

import com.squareup.moshi.Json

data class UserProfile(
    @Json(name = "name") val name: String,
    @Json(name = "age") val age: Int,
    @Json(name = "location") val location: String,
    @Json(name = "favorite_genre") val favoriteGenre: String
)
