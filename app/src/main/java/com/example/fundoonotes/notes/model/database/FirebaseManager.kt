package com.example.fundoonotes.notes.model.database

import com.example.fundoonotes.notes.model.Note
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class FirebaseManager {
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("users")
        .child(Firebase.auth.uid.toString()).child("notes")
    fun writeToFirebase(note: Note) {
        databaseReference.child(note.id.toString()).setValue(note)
    }

    fun deleteFromFirebase(note: Note) {
        databaseReference.child(note.id.toString()).removeValue()
    }

}
