package com.example.fundoonotes.notes.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fundoonotes.authentication.model.UserAuthService
import com.example.fundoonotes.notes.model.database.NoteRepository

@Suppress("UNCHECKED_CAST")
class MainActivityViewModelFactory(private var userAuthService: UserAuthService, private var noteRepository: NoteRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(userAuthService,noteRepository) as T
    }
}