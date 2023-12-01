package com.example.firebaseauth.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebaseauth.model.AuthListener
import com.example.firebaseauth.model.User
import com.example.firebaseauth.model.UserAuthService

class ResisterViewModel(private var userAuthService: UserAuthService) :ViewModel() {
    private var resisterStatus_:MutableLiveData<AuthListener> = MutableLiveData<AuthListener>()
    var resisterStatus:LiveData<AuthListener> = resisterStatus_

    fun register(user: User){
        userAuthService.userSignUp(user){
            resisterStatus_.value = it
        }
    }
}