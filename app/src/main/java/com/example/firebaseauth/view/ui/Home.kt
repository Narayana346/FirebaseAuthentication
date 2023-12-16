package com.example.firebaseauth.view.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.firebaseauth.R
import com.example.firebaseauth.databinding.FragmentHomeBinding
import com.example.firebaseauth.model.NotesItemClickListener
import com.example.firebaseauth.view.MainActivity
import com.example.firebaseauth.viewModel.MainActivityViewModel
import com.example.noteapp.Adapter.NoteAdapter
import com.example.noteapp.models.Note

class Home : Fragment(R.layout.fragment_home),NotesItemClickListener , PopupMenu.OnMenuItemClickListener {
    private lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: MainActivityViewModel
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var selectNote:Note
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel

        initUI()
        // if any changes note List the update the note List
        viewModel.getAllNotes().observe(viewLifecycleOwner){
            it?.let{
                noteAdapter.updateList(it)
            }
        }

        binding.fbBtn.setOnClickListener {
            findNavController().navigate(R.id.action_home2_to_addNote)
        }
    }

    // initialize and attached adapter on recycler view
    private fun initUI(){
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
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
        val bundle = Bundle()
        bundle.putSerializable("currentNote",note)
        AddNote().arguments = bundle
        findNavController().navigate(R.id.action_home2_to_addNote,bundle)
    }

    override fun onLongItemClicked(note: Note, cardView: CardView) {
        selectNote = note
        popUpDisplay(cardView)
    }
    private fun popUpDisplay(cardView: CardView){
        val popup = PopupMenu(context,cardView)
        popup.setOnMenuItemClickListener(this)
        popup.inflate(R.menu.pop_up_menu)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.delete_note){
            viewModel.deleteNote(selectNote)
            return true
        }
        return false
    }


}