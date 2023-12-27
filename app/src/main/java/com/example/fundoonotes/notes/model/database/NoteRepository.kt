package com.example.fundoonotes.notes.model.database

import androidx.lifecycle.LiveData
import com.example.fundoonotes.notes.model.Note
import kotlinx.coroutines.Deferred

class NoteRepository(private val noteDao: NoteDao){
    private val firebaseManager = FirebaseManager()
    val allNotes:LiveData<List<Note>> = noteDao.getAllNotes()
    suspend fun getAllSoftSyncedNotes():List<Note> {
        return noteDao.getAllSoftSyncedNotes()
    }
    suspend fun getAllSoftDeletedNotes(): LiveData<List<Note>>{
        return noteDao.getAllSoftDeletedNotes()
    }
    suspend fun getNote(date:String):Note{
        return noteDao.getNote(date)
    }
    fun insertFirebaseNote(note:Note){
        firebaseManager.writeToFirebase(note)
    }
    suspend fun insert(note: Note){
        noteDao.insert(note)
    }

    suspend fun delete(note: Note){
        noteDao.delete(note)
        firebaseManager.deleteFromFirebase(note)
    }
    suspend fun softDelete(note: Note){
        noteDao.softDelete(note.id)
    }
    suspend fun undo(note: Note){
        noteDao.undo(note.id)
    }
    suspend fun softSync(note: Note){
        noteDao.softSynced(note.id,note.title,note.note)
    }

    suspend fun update(note: Note){
        note.isSynced = true
        noteDao.update(note.id,note.title,note.note)
        firebaseManager.writeToFirebase(note)
    }
}