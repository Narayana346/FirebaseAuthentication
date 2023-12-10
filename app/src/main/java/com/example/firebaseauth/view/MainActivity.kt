package com.example.firebaseauth.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.firebaseauth.databinding.ActivityMainBinding
import com.example.firebaseauth.model.UserAuthService
import com.example.firebaseauth.viewModel.MainActivityViewModel
import com.example.firebaseauth.viewModel.MainActivityViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this,MainActivityViewModelFactory(UserAuthService()))[MainActivityViewModel::class.java]

        //check user login or not
        val user = viewModel.userAuthService.getUserInfo()
        if (user != null){ binding.message.text = user.email
        }else{ startActivity(Intent(this, Login::class.java))}

        //onclick logout
        binding.logoutBtn.setOnClickListener {
            viewModel.logOut()
            Toast.makeText(this,"Logout Successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, Login::class.java))
        }
    }
}