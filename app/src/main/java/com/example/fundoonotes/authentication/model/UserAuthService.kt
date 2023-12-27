package com.example.fundoonotes.authentication.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class UserAuthService {
    private val auth:FirebaseAuth = Firebase.auth
    private val database:DatabaseReference = Firebase.database.reference
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
    fun userSignUp(user: User, listener: (AuthListener) -> Unit){
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
    fun writeNewUser(userId: String, user: User, listener: (AuthListener) -> Unit){
        database.child("users").child(userId).setValue(user)
            .addOnSuccessListener{
                listener(AuthListener(true,"store information successfully"))
            }.addOnFailureListener{
                listener(AuthListener(false,"Failed"))
            }
        }
    fun getUserData(userId:String,listener: (AuthListener) -> Unit) {
    database.child("users").child(userId).get().addOnSuccessListener {
            listener(AuthListener(true,"successfully"))
        }.addOnFailureListener{
            listener(AuthListener(false,"Failed"))
        }
    }
}


