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

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            val profile = userRepository.getUserProfile()
            _userProfile.value = profile
        }
    }

    fun saveUserProfile(updatedProfile: UserProfile) {
        viewModelScope.launch {
            userRepository.saveUserProfile(updatedProfile)
            _userProfile.value = updatedProfile
        }
    }
}
