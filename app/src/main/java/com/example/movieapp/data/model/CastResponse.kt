package com.example.movieapp.data.model

import com.squareup.moshi.Json

data class CastResponse(
    @Json(name = "cast") val cast: List<Cast>
)