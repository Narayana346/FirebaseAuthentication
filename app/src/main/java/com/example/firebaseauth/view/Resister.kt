package com.example.firebaseauth.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.firebaseauth.databinding.ActivityResisterBinding
import com.example.firebaseauth.model.User
import com.example.firebaseauth.model.UserAuthService
import com.example.firebaseauth.viewModel.ResisterViewModel
import com.example.firebaseauth.viewModel.ResisterViewModelFactory

class Resister : AppCompatActivity() {
    private lateinit var binding: ActivityResisterBinding
    private lateinit var viewModel: ResisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this,ResisterViewModelFactory(UserAuthService()))[ResisterViewModel::class.java]
        // onclick resister
        binding.btnSignUp.setOnClickListener {
            val name = binding.etSinUpName.text.toString()
            val email = binding.etSinUpEmail.text.toString()
            val password = binding.etSinUpPassword.text.toString()
            if (!isEmpty(email,password)){
                viewModel.register(User(name,email,password))
                viewModel.resisterStatus.observe(this){ authListener ->
                    if(authListener.status) {

                        // store user data
                        viewModel.storeData(User(name,email,password))
                        viewModel.storeDataStatus.observe(this){
                            if (it.status){
                                Toast.makeText(this,it.message, LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this,it.message, LENGTH_SHORT).show()
                            }
                        }
                        Toast.makeText(this, authListener.message, LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    }else{
                        Toast.makeText(this,authListener.message, LENGTH_SHORT).show()
                    }
                }
            }
        }

        // move to login Activity
        binding.tvLoginPage.setOnClickListener{ startActivity(Intent(this, Login::class.java)) }
    }

    // check email and password is empty or not
    private fun isEmpty(email: String,password:String):Boolean {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Enter email", LENGTH_SHORT).show()
            return true
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Enter password", LENGTH_SHORT).show()
            return true
        }
        return false
    }
}