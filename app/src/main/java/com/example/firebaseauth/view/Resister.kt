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
import com.example.firebaseauth.databinding.ActivityResisterBinding
import com.example.firebaseauth.model.User
import com.example.firebaseauth.model.UserAuthService
import com.example.firebaseauth.viewModel.ResisterViewModel
import com.example.firebaseauth.viewModel.ResisterViewModelFactory

class Resister : AppCompatActivity() {
    private lateinit var binding: ActivityResisterBinding
    private lateinit var viewModel: ResisterViewModel
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this,ResisterViewModelFactory(UserAuthService()))[ResisterViewModel::class.java]
        // onclick resister
        binding.resBtn.setOnClickListener {
            binding.resProgressBar.visibility = View.GONE
            val email = binding.resEmail.text.toString()
            val password = binding.resPass.text.toString()
            if (!isEmpty(email,password)){
                viewModel.register(User(email,password))
                viewModel.resisterStatus.observe(this){
                    if(it.status) {
                        Toast.makeText(this, it.message, LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    }else{
                        Toast.makeText(this,it.message, LENGTH_SHORT).show()
                    }
                }
            }
        }

        // move to login Activity
        binding.logBtn.setOnClickListener{ startActivity(Intent(this, Login::class.java)) }
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