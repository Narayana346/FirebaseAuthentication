package com.example.fundoonotes.notes.view.fragment

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
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
import com.example.fundoonotes.common.util.NotificationReceiver
import com.example.fundoonotes.notes.model.Note
import com.example.fundoonotes.notes.view.Adapter.NoteAdapter
import com.example.fundoonotes.notes.view.Adapter.NotesItemClickListener
import com.example.fundoonotes.notes.view.MainActivity
import com.example.fundoonotes.notes.viewModel.MainActivityViewModel
import java.util.Calendar
import java.util.Locale

class Home : Fragment(R.layout.fragment_home), NotesItemClickListener, PopupMenu.OnMenuItemClickListener {
    private lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: MainActivityViewModel
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var selectNote: Note
    private lateinit var selectedCalendar: Calendar
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel

        initUI()
        // if any changes note List the update the note List

        viewModel.getAllNotes().observe(viewLifecycleOwner){
            noteAdapter.updateList(it)
        }


        binding.fbBtn.setOnClickListener {
            findNavController().navigate(R.id.action_home2_to_addNote)
        }

        selectedCalendar = Calendar.getInstance()
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
            viewModel.softDelete(selectNote)
            return true
        }

        if(item?.itemId == R.id.Reminder){
            showDateTimePickerDialog()
        }
        return false
    }

    private fun showDateTimePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                selectedCalendar.set(Calendar.YEAR, year)
                selectedCalendar.set(Calendar.MONTH, month)
                selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                showTimePickerDialog()
            },
            selectedCalendar.get(Calendar.YEAR),
            selectedCalendar.get(Calendar.MONTH),
            selectedCalendar.get(Calendar.DAY_OF_MONTH)
        )

        val dialog = datePickerDialog.datePicker
        dialog.minDate = System.currentTimeMillis() - 1000 // Set minimum date to today
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedCalendar.set(Calendar.MINUTE, minute)

                // Display the selected date and time
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                scheduleNotification(selectedCalendar.timeInMillis)

            },
            selectedCalendar.get(Calendar.HOUR_OF_DAY),
            selectedCalendar.get(Calendar.MINUTE),
            true
        )

        timePickerDialog.show()
    }
    private fun scheduleNotification(timeInMillis: Long) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), NotificationReceiver::class.java).apply {
            putExtra("note_title",selectNote.title)
            putExtra("note_desc",selectNote.note)
        }
        val pendingIntent =
            PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)

        // Schedule the alarm
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
    }

}