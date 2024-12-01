package com.example.movieapp.ui.screen

data class Profile(
    val name: String,
    val watched: Int,
    val lists: Int,
    val likes: Int,
    val profilePictureResId: Int, // Drawable resource ID
    val isNew: Boolean = false // Defaults to false if not specified
)