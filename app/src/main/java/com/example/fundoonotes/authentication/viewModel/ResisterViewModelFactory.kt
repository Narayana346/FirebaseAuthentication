package com.example.fundoonotes.authentication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fundoonotes.authentication.model.UserAuthService

@Suppress("UNCHECKED_CAST")
class ResisterViewModelFactory(private var userAuthService: UserAuthService):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ResisterViewModel(userAuthService) as T
    }
}