package com.example.firebaseauth.viewModel

import androidx.lifecycle.ViewModel
import com.example.firebaseauth.model.UserAuthService

class MainActivityViewModel(var userAuthService: UserAuthService):ViewModel() {
    fun logOut() {
        UserAuthService().userLogout()
    }

}