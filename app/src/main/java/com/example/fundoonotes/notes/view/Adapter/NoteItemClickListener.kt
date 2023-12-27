package com.example.fundoonotes.notes.view.Adapter

import androidx.cardview.widget.CardView
import com.example.fundoonotes.notes.model.Note

interface NotesItemClickListener{

    //on click listener
    fun onItemClicked(note: Note)
    // on long click listener
    fun onLongItemClicked(note: Note, cardView: CardView)
}