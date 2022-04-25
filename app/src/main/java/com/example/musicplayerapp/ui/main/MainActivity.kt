package com.example.musicplayerapp.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayerapp.data.constant.AllSongsModel
import com.example.musicplayerapp.databinding.ActivityMainBinding

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
        noteRVAdapter = AllSongsAdapter(list,this@MainActivity) {
            Toast.makeText(this, "clicked"+it.songName, Toast.LENGTH_SHORT).show()
             /* val intent = Intent(this,Player::class.java)
              intent.putExtra("path",dataSet[position].path)
              intent.putExtra("name",dataSet[position].songName)
              intent.putExtra("duration",dataSet[position].duration)
              intent.putExtra("LIST", dataSet as Serializable?)
              context.startActivity(intent)*/
        }
        binding.recyclerview.adapter = noteRVAdapter
    }
}