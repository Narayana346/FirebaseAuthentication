package com.example.firebaseauth.model

import androidx.cardview.widget.CardView
import com.example.noteapp.models.Note
interface NotesItemClickListener{
    //on click listener
    fun onItemClicked(note: Note)
    // on long click listener
    fun onLongItemClicked(note: Note, cardView: CardView)
}