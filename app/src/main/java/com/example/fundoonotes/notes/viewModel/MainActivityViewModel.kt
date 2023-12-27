package com.example.fundoonotes.notes.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fundoonotes.authentication.model.AuthListener
import com.example.fundoonotes.authentication.model.UserAuthService
import com.example.fundoonotes.notes.model.Note
import com.example.fundoonotes.notes.model.database.NoteRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivityViewModel(private var userAuthService: UserAuthService, private var notesRepository: NoteRepository):ViewModel() {
    private var userDataStatus_ :MutableLiveData<AuthListener> = MutableLiveData()
    val userDataStatus:LiveData<AuthListener> = userDataStatus_
    fun logOut() {
        UserAuthService().userLogout()
    }
    fun getUserInfo(): FirebaseUser? {
        return userAuthService.getUserInfo()
    }
    fun addNote(note:Note){
        viewModelScope.launch {
            notesRepository.insert(note)
        }
    }

    fun addNoteSynced(note: Note) {
        note.isSynced = true
        viewModelScope.launch {
            val insertDeferredResult: Deferred<Unit> = async {
                notesRepository.insert(note)
            }
            // wait function until insert is not completed
            insertDeferredResult.await()

            val note:Note = async {
                notesRepository.getNote(note.date)
            }.await()

            notesRepository.insertFirebaseNote(note)
        }
    }

    fun getAllNotes() = notesRepository.allNotes
    suspend fun getAllDeletedNotes():LiveData<List<Note>>{
        return notesRepository.getAllSoftDeletedNotes()
    }
    fun autoSynced(){
        viewModelScope.launch {
            val notes = async {
                notesRepository.getAllSoftSyncedNotes()
            }.await()

            if (notes.isNotEmpty()){
                for (note in notes ){
                    notesRepository.update(note)
                }
            }

        }
    }
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
    fun softDelete(note: Note){
        viewModelScope.launch {
            notesRepository.softDelete(note)
        }
    }
    fun undo(note: Note){
        viewModelScope.launch {
            notesRepository.undo(note)
        }
    }
    fun softSync(note: Note){
        viewModelScope.launch {
            notesRepository.softSync(note)
        }
    }
}