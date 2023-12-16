package com.example.firebaseauth.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.firebaseauth.R
import com.example.firebaseauth.databinding.ActivityMainBinding
import com.example.firebaseauth.model.UserAuthService
import com.example.firebaseauth.view.ui.Home
import com.example.firebaseauth.view.ui.Notes
import com.example.firebaseauth.viewModel.MainActivityViewModel
import com.example.firebaseauth.viewModel.MainActivityViewModelFactory
import com.example.noteapp.Database.NoteDatabase
import com.example.noteapp.Database.NoteRepository
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainActivityViewModel
    private lateinit var toggle: ActionBarDrawerToggle
    private val database = Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // instance of view model
        viewModel = ViewModelProvider(this,MainActivityViewModelFactory(
            UserAuthService(), NoteRepository(NoteDatabase(this).getNoteDao()))
        )[MainActivityViewModel::class.java]

        // action bar
        setSupportActionBar(binding.toolbar)
        binding.navView.setNavigationItemSelectedListener(this)
        toggle = ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar , R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        //firebase realtime database fetch
        database.child("users").child(viewModel.getUserInfo()!!.uid).get().addOnSuccessListener {
            if(it.exists()){
                val email = it.child("email").value.toString()
                val name = it.child("name").value.toString()
                findViewById<TextView>(R.id.user_name).text = name
                findViewById<TextView>(R.id.user_email).text = email
                Toast.makeText(this,"data load successfully", LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,"data load Failed", LENGTH_SHORT).show()
        }
        // search icon operate
        binding.searchIcon.setOnClickListener {
            if(binding.searchView.isVisible){
                binding.searchView.visibility = View.GONE
                binding.searchIcon.setImageResource(R.drawable.baseline_search_24)
            }else{
                binding.searchView.visibility = View.VISIBLE
                binding.searchIcon.setImageResource(R.drawable.baseline_chevron_right_24)
            }
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        if(savedInstanceState == null){
            navController.navigate(R.id.home2)
            binding.navView.setCheckedItem(R.id.home)
        }

    }
    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container,fragment)
        transaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home -> replaceFragment(Home())
            R.id.notes -> replaceFragment(Notes())
            R.id.delete -> Toast.makeText(applicationContext,"Delete",LENGTH_SHORT).show()
            R.id.settings -> Toast.makeText(applicationContext,"Settings", LENGTH_SHORT).show()
            R.id.help -> Toast.makeText(applicationContext,"Clicked Help", LENGTH_SHORT).show()
            R.id.logout -> {
                viewModel.logOut()
                startActivity(Intent(this,Login::class.java))
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }


}