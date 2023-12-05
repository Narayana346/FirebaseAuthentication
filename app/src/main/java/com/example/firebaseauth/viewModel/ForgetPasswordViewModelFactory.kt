package com.example.firebaseauth.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firebaseauth.model.UserAuthService

class ForgetPasswordViewModelFactory(private val userAuthService: UserAuthService):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ForgetPasswordViewModel(userAuthService) as T
    }
}