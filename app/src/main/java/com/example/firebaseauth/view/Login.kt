package com.example.firebaseauth.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.firebaseauth.databinding.ActivityLoginBinding
import com.example.firebaseauth.model.User
import com.example.firebaseauth.model.UserAuthService
import com.example.firebaseauth.viewModel.LoginViewModel
import com.example.firebaseauth.viewModel.LoginViewModelFactory

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
        viewModel = ViewModelProvider(this,LoginViewModelFactory(UserAuthService()))[LoginViewModel::class.java]

        binding.logBtn.setOnClickListener {
            val email = binding.logEmail.text.toString()
            val password = binding.logPass.text.toString()
            binding.logProgressBar.visibility = View.GONE
            if(!isEmpty(email,password)){
                viewModel.login(User(email,password))
                viewModel.userLoginStatus.observe(this){
                    if(it.status) {
                        Toast.makeText(this, it.message, LENGTH_SHORT).show()
                        startActivity(Intent(this,MainActivity::class.java))
                    }else{
                        Toast.makeText(this, it.message, LENGTH_SHORT).show()
                    }

                }

            }

        }
        binding.resBtn.setOnClickListener {
            startActivity(Intent(this, Resister::class.java))
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