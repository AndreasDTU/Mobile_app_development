package com.example.movieapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.UserProfile
import com.example.movieapp.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    // StateFlow to hold the user's profile data
    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile

    // StateFlow to track loading status
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // StateFlow to track errors
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchUserProfile()
    }

    // Fetch the user profile from the repository
    private fun fetchUserProfile() {
        _isLoading.value = true
        _error.value = null // Reset error state before fetching

        viewModelScope.launch {
            try {
                val profile = userRepository.getUserProfile()
                _userProfile.value = profile
                println("Fetched Profile: $profile") // Debug log
            } catch (e: Exception) {
                _error.value = "Error fetching profile: ${e.message}" // Handle error
                _userProfile.value = null
                println("Error fetching profile: ${e.message}")
            } finally {
                _isLoading.value = false // Set loading to false after fetch attempt
            }
        }
    }

    // Save the updated user profile
    fun updateUserProfile(name: String, age: Int, location: String, favoriteGenre: String) {
        _isLoading.value = true
        _error.value = null // Reset error state before saving

        val updatedProfile = UserProfile(
            name = name,
            age = age,
            location = location,
            favoriteGenre = favoriteGenre, // Updated field
            ratings = userProfile.value?.ratings ?: mutableListOf() // Preserve current ratings
        )

        println("Updating Profile: $updatedProfile") // Debug log

        viewModelScope.launch {
            try {
                userRepository.saveUserProfile(updatedProfile)
                _userProfile.value = updatedProfile
            } catch (e: Exception) {
                _error.value = "Error saving profile: ${e.message}" // Handle error
                println("Error saving profile: ${e.message}")
            } finally {
                _isLoading.value = false // Set loading to false after saving attempt
            }
        }
    }
}