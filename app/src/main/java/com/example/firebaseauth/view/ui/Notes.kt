package com.example.firebaseauth.view.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.firebaseauth.R
import com.example.firebaseauth.databinding.FragmentNotesBinding

class Notes : Fragment(R.layout.fragment_notes) {
    private lateinit var binding: FragmentNotesBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotesBinding.bind(view)
    }

}