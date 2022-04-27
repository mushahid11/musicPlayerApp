package com.example.musicplayerapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.musicplayerapp.R
import com.example.musicplayerapp.data.constant.AllSongsModel
import com.example.musicplayerapp.databinding.ActivityPlayerBinding
import com.example.musicplayerapp.ui.main.MainActivity
import com.example.musicplayerapp.util.getTimeInMilles
import com.example.musicplayerapp.util.media.mediaPlayer
import java.io.IOException

class Player : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding


    private var currentSongIndex = 0


    private var songsList: List<AllSongsModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityPlayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val path = intent.getStringExtra("path")
        val name = intent.getStringExtra("name")
        val Duration = intent.getStringExtra("duration")
        songsList = intent.getSerializableExtra("LIST") as List<AllSongsModel>?

        binding.tvSongName.text = name

        binding.imgPlay.setOnClickListener {

            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()

                binding.imgPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24)

            } else {
                mediaPlayer?.start()
                binding.imgPlay.setImageResource(R.drawable.ic_baseline_pause_24)
            }


            /*  //Start our own service
              //Start our own service
              val intent = Intent(
                  this@Player,
                  MyService::class.java
              )

              intent.putExtra("path", path)
              intent.putExtra("name", name)
              intent.putExtra("duration", Duration)
              intent.putExtra("LIST", songsList as Serializable?)


              mediaPlayer = MediaPlayer()
              *//*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }*//*


            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
                binding.imgPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            } else {
                binding.imgPlay.setImageResource(R.drawable.ic_baseline_pause_24)
                mediaPlayer?.start()
            }
*/
        }

        binding.imgNext.setOnClickListener {
            next()
        }

        binding.imgPrev.setOnClickListener {
            prev()
        }


        //    playMusic(path, Duration)
        binding.tvTotalDuration.text = Duration
        maxintializeSeekBar()
        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) mediaPlayer?.seekTo(p1)
                binding.tvDuration.text = getTimeInMilles(p1.toLong())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                // TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                //  TODO("Not yet implemented")
            }

        })


    }

    /*  private fun playMusic(path: String?, Duration: String?) {


          mediaPlayer = MediaPlayer()
          try {
              mediaPlayer?.setDataSource(path) //Write your location here
              mediaPlayer?.prepare()
              mediaPlayer?.start()
          } catch (e: Exception) {
              e.printStackTrace()
          }
          binding.tvTotalDuration.text = Duration
      }*/

    private fun prev() {
        if (currentSongIndex > 0) {
            playSong(currentSongIndex - 1);
            currentSongIndex -= 1;
        } else {
            // play last song
            playSong(songsList!!.size - 1);
            currentSongIndex = songsList!!.size - 1;
        }
    }

    private fun next() {
        if (currentSongIndex < (songsList!!.size - 1)) {
            playSong(currentSongIndex + 1);
            currentSongIndex += 1;
        } else {
            // play first song
            playSong(0);
            currentSongIndex = 0;
        }
    }

    private fun maxintializeSeekBar() {

        binding.seekbar.max = mediaPlayer!!.duration

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {

                try {
                    binding.seekbar.progress = mediaPlayer!!.currentPosition
                    handler.postDelayed(this, 1000)
                } catch (e: Exception) {
                    binding.seekbar.progress = 0
                }

            }
        }, 0)
    }

    private fun playSong(songIndex: Int) {

        binding.tvSongName.text = songsList?.get(songIndex)?.songName

        // Play song
        try {
            mediaPlayer?.reset()
            mediaPlayer?.setDataSource(songsList?.get(songIndex)?.path)
            mediaPlayer?.prepare()
            mediaPlayer?.start()

            //  maxintializeSeekBar()

            binding.tvTotalDuration.text = songsList?.get(songIndex)?.duration

            // Displaying Song title
            //     val songTitle: String = songsList.get(songIndex).get("songTitle")
            // songTitleLabel.setText(songTitle)

            // Changing Button Image to pause image
            binding.imgPlay.setImageResource(R.drawable.ic_baseline_pause_24)

            /* // set Progress bar values
             songProgressBar.setProgress(0)
             songProgressBar.setMax(100)

             // Updating progress bar
             updateProgressBar()*/
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@Player, MainActivity::class.java))
        finish()
    }


    override fun onPause() {
        super.onPause()

        mediaPlayer?.pause()
    }

}