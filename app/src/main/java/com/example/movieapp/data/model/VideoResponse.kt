package com.example.movieapp.data.model;

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("results")
    val results: List<Video>
)

data class Video(
    @SerializedName("id")
    val id: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("site")
    val site: String,
    @SerializedName("type")
    val type: String
)
