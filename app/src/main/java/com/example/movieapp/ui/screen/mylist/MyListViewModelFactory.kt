package com.example.movieapp.ui.screen.mylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.repositories.MovieRepository

class MyListViewModelFactory(
    private val repository: MovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyListViewModel::class.java)) {
            return MyListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}