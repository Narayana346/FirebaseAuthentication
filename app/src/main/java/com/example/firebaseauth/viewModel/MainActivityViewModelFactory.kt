package com.example.firebaseauth.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firebaseauth.model.UserAuthService

@Suppress("UNCHECKED_CAST")
class MainActivityViewModelFactory(private var userAuthService: UserAuthService):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(userAuthService) as T
    }
}