package com.example.randogpic.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.randogpic.data.repository.RandomDogPicRepository

class RandomDogPicViewModelFactory (private val repository: RandomDogPicRepository)

    : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RandomDogPicViewModel(repository) as T
        }

}






