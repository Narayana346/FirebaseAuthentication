package com.example.firebaseauth.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebaseauth.model.AuthListener
import com.example.firebaseauth.model.UserAuthService

class ForgetPasswordViewModel(private var userAuthService : UserAuthService):ViewModel() {
    private var emailSendStatus_: MutableLiveData<AuthListener> = MutableLiveData<AuthListener>()
    val emailSendStatus: LiveData<AuthListener> = emailSendStatus_
    fun reset(email:String){
        userAuthService.forgetPassword(email){
            emailSendStatus_.value = it
        }
    }
}