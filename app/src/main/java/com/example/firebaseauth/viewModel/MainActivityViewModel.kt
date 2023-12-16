package com.example.firebaseauth.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseauth.model.AuthListener
import com.example.firebaseauth.model.User
import com.example.firebaseauth.model.UserAuthService
import com.example.noteapp.Database.NoteRepository
import com.example.noteapp.models.Note
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivityViewModel(private var userAuthService: UserAuthService,private var notesRepository: NoteRepository):ViewModel() {
    private var userDataStatus_ :MutableLiveData<AuthListener> = MutableLiveData()
    val userDataStatus:LiveData<AuthListener> = userDataStatus_
    fun logOut() {
        UserAuthService().userLogout()
    }
    fun getUserInfo(): FirebaseUser? {
        return userAuthService.getUserInfo()
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            notesRepository.insert(note)
        }
    }

    fun getAllNotes() = notesRepository.allNotes

    fun updateNote(note: Note){
        viewModelScope.launch {
            notesRepository.update(note)
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch {
            notesRepository.delete(note)
        }
    }
}