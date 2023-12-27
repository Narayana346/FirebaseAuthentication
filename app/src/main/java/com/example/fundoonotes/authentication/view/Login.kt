package com.example.fundoonotes.authentication.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.firebaseauth.databinding.ActivityLoginBinding
import com.example.fundoonotes.authentication.model.User
import com.example.fundoonotes.authentication.model.UserAuthService
import com.example.fundoonotes.notes.view.MainActivity
import com.example.fundoonotes.authentication.viewModel.LoginViewModel
import com.example.fundoonotes.authentication.viewModel.LoginViewModelFactory

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = viewModel.getUserInfo()
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, LoginViewModelFactory(UserAuthService()))[LoginViewModel::class.java]

        binding.btnSignIn.setOnClickListener {
            val email = binding.etSinInEmail.text.toString()
            val password = binding.etSinInPassword.text.toString()
            if(!isEmpty(email,password)){
                viewModel.login(User(email = email, password = password))
                viewModel.userLoginStatus.observe(this){
                    Toast.makeText(this, it.message, LENGTH_SHORT).show()
                    if(it.status) {
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                }

            }

        }
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, Resister::class.java))
        }
        binding.tvForgotPassword.setOnClickListener{
            startActivity(Intent(this, ForgetPassword::class.java))
        }
    }

    private fun isEmpty(email: String,password:String):Boolean {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Enter email",LENGTH_SHORT).show()
            return true
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Enter password",LENGTH_SHORT).show()
            return true
        }
        return false
    }

}