package com.example.movieapp.data.model
import com.squareup.moshi.Json

data class Cast(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "character") val character: String, // Role played by the cast member
    @Json(name = "profile_path") val profilePath: String? // Profile picture of the cast member
)