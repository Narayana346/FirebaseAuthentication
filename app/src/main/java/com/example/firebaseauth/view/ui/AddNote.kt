package com.example.firebaseauth.view.ui

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.firebaseauth.R
import com.example.firebaseauth.databinding.FragmentAddNoteBinding
import com.example.firebaseauth.view.MainActivity
import com.example.firebaseauth.viewModel.MainActivityViewModel
import com.example.noteapp.models.Note
import java.util.Date


@Suppress("DEPRECATION")
class AddNote : Fragment(R.layout.fragment_add_note) {
    private lateinit var binding: FragmentAddNoteBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var oldNote: Note
    private lateinit var newNote: Note
    private var isUpdate: Boolean = false
    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddNoteBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        // set data into ui of selected note
        try {
            oldNote = (arguments?.getSerializable("currentNote") as Note?)!!
            binding.etTitle.setText(oldNote.title)
            binding.etNote.setText(oldNote.note)
            isUpdate = true
        }catch (e: Exception) {
            e.message?.let {
                Log.d("Exception", it)
            }
        }

        // update and save onclick methods
        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val noteDesc = binding.etNote.text.toString()
            if (title.isNotEmpty() || noteDesc.isNotEmpty()) {
                val formatter = SimpleDateFormat("EE,d MM yyyy HH: mm a")
                if (isUpdate) {
                    newNote = Note(oldNote.id, title, noteDesc, formatter.format(Date()))
                    viewModel.updateNote(newNote)
                    findNavController().navigate(R.id.action_addNote_to_home2)
                } else {
                    newNote = Note(null, title, noteDesc, formatter.format(Date()))
                    viewModel.addNote(newNote)
                    findNavController().navigate(R.id.action_addNote_to_home2)
                }
            }else{
                Toast.makeText(context,"", Toast.LENGTH_SHORT).show()
            }
        }

        KeyEvent.KEYCODE_BACK
    }



}