package com.example.firebaseauth.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.firebaseauth.databinding.ActivityForgetPasswordBinding
import com.example.firebaseauth.model.UserAuthService
import com.example.firebaseauth.viewModel.ForgetPasswordViewModel
import com.example.firebaseauth.viewModel.ForgetPasswordViewModelFactory

class ForgetPassword : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding
    private lateinit var viewModel:ForgetPasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this,ForgetPasswordViewModelFactory(UserAuthService()))[ForgetPasswordViewModel::class.java]
        binding.btnForgotPasswordSubmit.setOnClickListener {
            val email = binding.etForgotPasswordEmail.text.toString()
            if(email != null){
                viewModel.reset(email)
                viewModel.emailSendStatus.observe(this){
                    if(it.status){
                        Toast.makeText(this,it.message,LENGTH_SHORT).show()
                        startActivity(Intent(this,Login::class.java))
                    }else{
                        Toast.makeText(this,it.message, LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this,"Enter email", LENGTH_SHORT).show()
            }
        }
    }
}