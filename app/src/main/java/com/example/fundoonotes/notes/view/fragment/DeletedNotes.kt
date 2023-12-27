package com.example.fundoonotes.notes.view.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.firebaseauth.R
import com.example.firebaseauth.databinding.FragmentDeletedNotesBinding
import com.example.fundoonotes.notes.model.Note
import com.example.fundoonotes.notes.view.Adapter.NoteAdapter
import com.example.fundoonotes.notes.view.Adapter.NotesItemClickListener
import com.example.fundoonotes.notes.view.MainActivity
import com.example.fundoonotes.notes.viewModel.MainActivityViewModel
import kotlinx.coroutines.launch

class DeletedNotes : Fragment(R.layout.fragment_deleted_notes), NotesItemClickListener, PopupMenu.OnMenuItemClickListener{
    lateinit var noteAdapter:NoteAdapter
    lateinit var viewModel:MainActivityViewModel
    private lateinit var selectNote:Note
    private lateinit var binding: FragmentDeletedNotesBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDeletedNotesBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel

        initUI()
        // if any changes note List the update the note List
        lifecycleScope.launch{
            viewModel.getAllDeletedNotes().observe(viewLifecycleOwner){
                noteAdapter.updateList(it)
            }
        }
    }
    private fun initUI(){
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        noteAdapter = NoteAdapter(this)
        binding.recyclerView.adapter = noteAdapter

        activity?.findViewById<SearchView>(R.id.searchView)?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null){
                    noteAdapter.filterList(newText)
                }
                return true
            }
        })

    }

    override fun onItemClicked(note: Note) {

    }

    override fun onLongItemClicked(note: Note, cardView: CardView) {
        selectNote = note
        popUpDisplay(cardView)
    }
    private fun popUpDisplay(cardView: CardView){
        val popup = PopupMenu(context,cardView)
        popup.setOnMenuItemClickListener(this)
        popup.inflate(R.menu.delete_menu)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.delete_note){
                viewModel.deleteNote(selectNote)
            return true
        }
        if(item?.itemId == R.id.undo){
            viewModel.undo(selectNote)
            return true
        }
        return false
    }

}