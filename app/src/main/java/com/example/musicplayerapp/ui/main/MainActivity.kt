package com.example.musicplayerapp.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayerapp.MyService
import com.example.musicplayerapp.data.constant.AllSongsModel
import com.example.musicplayerapp.databinding.ActivityMainBinding
import com.example.musicplayerapp.ui.Player
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //on below line we are creating a variable for our recycler view, exit text, button and viewmodal.
    private val viewModal: SongViewModal by viewModels()
    private lateinit var noteRVAdapter: AllSongsAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        viewModal.getAudioList(this)
        viewModal.audioList.observe(this) { list ->
            list?.let {
                if (list.isNotEmpty()) {
                    setAdapter(it)
                }
            }
        }

    }

    private fun setAdapter(list: List<AllSongsModel>) {
        noteRVAdapter = AllSongsAdapter(list) {
            Toast.makeText(this, "clicked" + it.songName, Toast.LENGTH_SHORT).show()


            //Start our own service
            //Start our own service
            val intent = Intent(
                this,
                MyService::class.java
            )

            //Start Service
            intent.putExtra("path", it.path)
            intent.putExtra("name", it.songName)
            intent.putExtra("duration", it.duration)
            //   intent.putExtra("LIST", list as Serializable?)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }

            Log.d("setAdapter", "setAdapter: ${it}")
            //Start Activity
            val intent2 = Intent(this, Player::class.java)
            intent2.putExtra("path", it.path)
            intent2.putExtra("name", it.songName)
            intent2.putExtra("duration", it.duration)
            intent2.putExtra("LIST", list as Serializable?)
            startActivity(intent2)

        }
        binding.recyclerview.adapter = noteRVAdapter
    }
}