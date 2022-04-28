package com.example.musicplayerapp


import android.app.*
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.musicplayerapp.data.constant.AllSongsModel
import com.example.musicplayerapp.data.constant.AppConstant.NEXT
import com.example.musicplayerapp.data.constant.AppConstant.PAUSE
import com.example.musicplayerapp.data.constant.AppConstant.PLAY
import com.example.musicplayerapp.data.constant.AppConstant.PREVIOUS
import com.example.musicplayerapp.util.media.mediaPlayer
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException


@AndroidEntryPoint
class MyService : Service() {

     companion object {
         var songsList: List<AllSongsModel>? = null
     }

    var Duration: String? = null
    var path: String? = null


    override fun onBind(p0: Intent?): IBinder? {

        return null
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        path = intent?.getStringExtra("path")
        val name = intent?.getStringExtra("name")
        Duration = intent?.getStringExtra("duration")
        songsList = intent?.getSerializableExtra("LIST") as List<AllSongsModel>?

        Log.d("onBind:", "onBind: $path")
        Log.d("onBind:", "onBind: $name")
        Log.d("onBind:", "onBind: $Duration")
        Log.d("onBind:", "onBind: ${songsList?.size}")


        val pauseIntent = Intent(baseContext, Receiver::class.java).setAction(PAUSE)
        val pausePendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(
                baseContext, 0, pauseIntent,
                FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getBroadcast(baseContext, 0, pauseIntent, FLAG_UPDATE_CURRENT)
        }

        val playIntent = Intent(baseContext, Receiver::class.java).setAction(PLAY)
        val playPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(
                baseContext,
                0,
                playIntent,
                FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getBroadcast(baseContext, 0, playIntent, FLAG_UPDATE_CURRENT)
        }

        val PrevIntent = Intent(baseContext, Receiver::class.java).setAction(PLAY)
        val PrevPendingIntentt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(
                baseContext,
                0,
                PrevIntent,
                FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getBroadcast(baseContext, 0, playIntent, FLAG_UPDATE_CURRENT)
        }

        val NextIntent = Intent(baseContext, Receiver::class.java).setAction(PLAY)
        val NextPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(
                baseContext,
                0,
                NextIntent,
                FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getBroadcast(baseContext, 0, playIntent, FLAG_UPDATE_CURRENT)
        }

        val CHANNEL_ID = "my_app"
        val channel = NotificationChannel(
            CHANNEL_ID,
            "MyApp", NotificationManager.IMPORTANCE_DEFAULT
        )
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Notification")
            .setSmallIcon(R.drawable.backword)
            .setContentText("Hello! This is a notification.")
            .addAction(R.drawable.ic_baseline_play_arrow_24, "pause", pausePendingIntent)
            .addAction(R.drawable.ic_baseline_pause_24, "play", playPendingIntent)
            .addAction(R.drawable.ic_baseline_fast_forward_24, "play", NextPendingIntent)
            .addAction(R.drawable.backword, "play", PrevPendingIntentt)
            .setAutoCancel(true)
            .setContentText("").build()
        startForeground(1, notification)

        //  showNotification(playIntent,pauseIntent)

        playMusic(path, Duration)

        return START_NOT_STICKY
    }


    override fun onCreate() {
        //   playMusic(path, Duration)
        Toast.makeText(this, "Service Successfully Created", Toast.LENGTH_LONG).show()
        //myPlayer = MediaPlayer.create(this, R.raw.sisira)
        //setting loop play to true
        //this will make the ringtone continuously playing        myPlayer.setLooping(false); // Set looping
    }


    private fun playMusic(path: String?, Duration: String?) {

        try {
            mediaPlayer?.apply {
                setDataSource(path) //Write your location here
                prepare()
                start()
            }

            Log.d("playMusic", "playMusic: 2")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("playMusic", "playMusic: $e")
        }

    }

    override fun onStart(intent: Intent?, startid: Int) {
        Toast.makeText(this, "Service Started and Playing Music", Toast.LENGTH_LONG).show()
        // myPlayer.start()
    }

    override fun onDestroy() {
        Toast.makeText(this, "Service Stopped and Music Stopped", Toast.LENGTH_LONG).show()
        // myPlayer.stop()
    }


    /*@RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(pausePendingIntent: PendingIntent?,playPendingIntent: PendingIntent?) {
        val CHANNEL_ID = "my_app"
        val channel = NotificationChannel(
            CHANNEL_ID,
            "MyApp", NotificationManager.IMPORTANCE_DEFAULT
        )
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
            channel
        )
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Notification")
            .setSmallIcon(R.drawable.backword)
            .setContentText("Hello! This is a notification.")
            .addAction(R.drawable.ic_baseline_play_arrow_24, "pause", pausePendingIntent)
            .addAction(R.drawable.ic_baseline_pause_24, "play", playPendingIntent)
            .setAutoCancel(true)
            .setContentText("").build()
        startForeground(1, notification)
    }*/


    class Receiver : BroadcastReceiver() {
        private var currentSongIndex = 0
        override fun onReceive(context: Context, intent: Intent) {

            when (intent.action) {
                PAUSE -> {
                    Log.d("TAG4", "onReceivePAUSE: ")
                  mediaPlayer?.pause()

                }

                PLAY -> {
                 mediaPlayer?.start()

                }

                PREVIOUS -> {
                    prev()

                }

                NEXT -> {
                    next()
                }
            }
        }


        fun next() {

             if (currentSongIndex < (songsList!!.size - 1)) {
                 playSong(currentSongIndex + 1);
                 currentSongIndex += 1;
             } else {
                 // play first song
                 playSong(0);
                 currentSongIndex = 0;
             }

        }

        fun prev() {

             if (currentSongIndex > 0) {
                 playSong(currentSongIndex - 1);
                 currentSongIndex -= 1;
             } else {
                 // play last song
                 playSong(songsList!!.size - 1);
                 currentSongIndex =songsList!!.size - 1;
             }

        }

        private fun playSong(songIndex: Int) {

            ///binding.tvSongName.text = songsList?.get(songIndex)?.songName

            //Play song
            try {
                mediaPlayer?.reset()
                mediaPlayer?.setDataSource(songsList?.get(songIndex)?.path)
                mediaPlayer?.prepare()
                mediaPlayer?.start()

                Log.d("playSong", "playSong: ")

                //  maxintializeSeekBar()

                //           binding.tvTotalDuration.text = songsList?.get(songIndex)?.duration

                // Displaying Song title
                //     val songTitle: String = songsList.get(songIndex).get("songTitle")
                // songTitleLabel.setText(songTitle)

                // Changing Button Image to pause image
                //           binding.imgPlay.setImageResource(R.drawable.ic_baseline_pause_24)

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

    }

}