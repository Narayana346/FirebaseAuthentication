package com.example.firebaseauth.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebaseauth.model.AuthListener
import com.example.firebaseauth.model.User
import com.example.firebaseauth.model.UserAuthService
import com.google.firebase.auth.FirebaseUser

class LoginViewModel(private var  userAuthService: UserAuthService) :ViewModel() {
    private var userLoginStatus_:MutableLiveData<AuthListener> = MutableLiveData<AuthListener>()
    val userLoginStatus: LiveData<AuthListener> = userLoginStatus_

    fun login(user: User){
        userAuthService.userLogin(user){
            userLoginStatus_.value = it
        }
    }
    fun getUserInfo(): FirebaseUser? {
        return UserAuthService().getUserInfo()
    }
}