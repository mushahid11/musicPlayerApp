package com.example.musicplayerapp.ui.player

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.musicplayerapp.R
import com.example.musicplayerapp.data.constant.AllSongsModel
import com.example.musicplayerapp.data.constant.AppConstant.currentSongIndex
import com.example.musicplayerapp.databinding.ActivityPlayerBinding
import com.example.musicplayerapp.ui.main.MainActivity
import com.example.musicplayerapp.util.getTimeInMilles
import com.example.musicplayerapp.util.media.mediaPlayer
import java.io.IOException


class Player : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding


  //  private var currentSongIndex = 0


    private var songsList: List<AllSongsModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityPlayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.apply {

            val path = intent.getStringExtra("path")
            val name = intent.getStringExtra("name")
            val duration = intent.getStringExtra("duration")
            songsList = intent.getSerializableExtra("LIST") as List<AllSongsModel>?



            //Icon Change to pause when song finished
            mediaPlayer?.setOnCompletionListener {
                next()
            }

            tvSongName.text = name

            imgPlay.setOnClickListener {

                if (mediaPlayer?.isPlaying == true) {
                    mediaPlayer?.pause()

                    imgPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24)

                } else {
                    mediaPlayer?.start()
                    imgPlay.setImageResource(R.drawable.ic_baseline_pause_24)
                }


            }

            imgNext.setOnClickListener {
                next()
            }

            imgPrev.setOnClickListener {
                prev()
            }


            //    playMusic(path, Duration)
            tvTotalDuration.text = duration
            maxintializeSeekBar()
            seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if (p2) mediaPlayer?.seekTo(p1)
                    tvDuration.text = getTimeInMilles(p1.toLong())
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                    // TODO("Not yet implemented")
                    Log.d("onStartTrackingTouch", "onStartTrackingTouch: ")
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                    //  TODO("Not yet implemented")
                }

            })


        }


    }


    private fun next() {
        if (currentSongIndex < (songsList!!.size - 1)) {
            playSong(currentSongIndex + 1)
            currentSongIndex += 1
        } else {
            // play first song
            playSong(0)
            currentSongIndex = 0
        }
    }

    private fun prev() {
        if (currentSongIndex > 0) {
            playSong(currentSongIndex - 1)
            currentSongIndex -= 1
        } else {
            // play last song
            playSong(songsList!!.size - 1)
            currentSongIndex = songsList!!.size - 1
        }
    }


    private fun maxintializeSeekBar() {
        
        binding.apply {
            seekbar.max = mediaPlayer!!.duration



            val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {

                    try {
                        seekbar.progress = mediaPlayer!!.currentPosition
                        handler.postDelayed(this, 1000)
                    } catch (e: Exception) {
                        seekbar.progress = 0
                    }

                }
            }, 0)
        }

    }

    private fun playSong(songIndex: Int) {


        binding.apply {
            tvSongName.text = songsList?.get(songIndex)?.songName

            // Play song
            try {

                mediaPlayer?.apply {
                    reset()
                    setDataSource(songsList?.get(songIndex)?.path)
                    prepare()
                    start()
                }

                //  maxintializeSeekBar()
                tvTotalDuration.text = songsList?.get(songIndex)?.duration

                // Changing Button Image to pause image
                imgPlay.setImageResource(R.drawable.ic_baseline_pause_24)


            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }

    override fun onBackPressed() {
        startActivity(Intent(this@Player, MainActivity::class.java))
        finish()
    }


}