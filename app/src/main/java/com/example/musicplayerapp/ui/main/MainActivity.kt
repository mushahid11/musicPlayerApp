package com.example.musicplayerapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayerapp.R
import com.example.musicplayerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    //on below line we are creating a variable for our recycler view, exit text, button and viewmodal.
    private lateinit var viewModal: SongViewModal

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val  view = binding.root
        setContentView(view)

        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        //on below line we are initializing our adapter class.
        val noteRVAdapter = AllSongsAdapter(this@MainActivity){

            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
        }

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[SongViewModal::class.java]

        viewModal.audioList.observe(this) { list ->
            list?.let {
                //on below line we are updating our list.
                Log.d("listSize", "onCreate: " + list.size)
                noteRVAdapter.updateList(it)
            }
        }

    }
}