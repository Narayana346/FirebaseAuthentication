package com.example.firebaseauth.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class UserAuthService {
    private val auth:FirebaseAuth = Firebase.auth
    // user login method
    fun userLogin(user: User, listener: (AuthListener)-> Unit) {
        auth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // the listener message if you login user
                    listener(AuthListener(true,"Login Successfully"))
                } else {
                    // the listener message If sign in fails
                    listener(AuthListener(false,"Login Failure"))
                }
            }
    }
    //logout method
    fun userLogout(){
        auth.signOut()
    }
    //signup method
    fun userSignUp(user: User,listener: (AuthListener) -> Unit){
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // listener message if Sign in success
                    listener(AuthListener(true,"SignUp Successfully"))
                } else {
                    //listener message  If sign in fails
                    listener(AuthListener(false,"SignUp Failure"))
                }
            }
    }
//    userInfo method
    fun getUserInfo(): FirebaseUser? {
        return auth.currentUser
    }
    fun forgetPassword(email:String,listener: (AuthListener) -> Unit){
        auth.sendPasswordResetEmail(email).addOnCompleteListener{
            if(it.isSuccessful){
                listener(AuthListener(true,"Please check your Email"))
            }else{
                listener(AuthListener(false,"try Again"))
            }
        }
    }
}