package com.example.firebaseauth.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firebaseauth.model.UserAuthService
import com.example.noteapp.Database.NoteRepository

@Suppress("UNCHECKED_CAST")
class MainActivityViewModelFactory(private var userAuthService: UserAuthService,private var noteRepository: NoteRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(userAuthService,noteRepository) as T
    }
}