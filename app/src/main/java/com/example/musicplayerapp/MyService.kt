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

    override fun onStart(intent: Intent?, startid: Int) {
        Toast.makeText(this, "Service Started and Playing Music", Toast.LENGTH_LONG).show()
        // myPlayer.start()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        path = intent?.getStringExtra("path")
        val name = intent?.getStringExtra("name")
        Duration = intent?.getStringExtra("duration")
        songsList = intent?.getSerializableExtra("LIST") as List<AllSongsModel>?



        val prevIntent = Intent(baseContext, Receiver::class.java).setAction(PLAY)
        val prevPendingIntentt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(
                baseContext,
                0,
                prevIntent,
                FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getBroadcast(baseContext, 0, prevIntent, FLAG_UPDATE_CURRENT)
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



        val nextIntent = Intent(baseContext, Receiver::class.java).setAction(PLAY)
        val nextPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(
                baseContext,
                0,
                nextIntent,
                FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getBroadcast(baseContext, 0, nextIntent, FLAG_UPDATE_CURRENT)
        }

        val CHANNEL_ID = "my_app"
        val channel = NotificationChannel(
            CHANNEL_ID,
            "MyApp", NotificationManager.IMPORTANCE_DEFAULT
        )
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(name)
            .setSmallIcon(R.drawable.music_notes)
            .setContentText("Hello! This is a notification.")
            .addAction(R.drawable.backword, "previous", prevPendingIntentt)
            .addAction(R.drawable.ic_baseline_play_arrow_24, "play", playPendingIntent)
            .addAction(R.drawable.ic_baseline_fast_forward_24, "Next", nextPendingIntent)
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



    class Receiver : BroadcastReceiver() {
        private var currentSongIndex = 0
        override fun onReceive(context: Context, intent: Intent) {

            when (intent.action) {
                PAUSE -> {
                    Log.d("TAG4", "onReceivePAUSE: ")
                  mediaPlayer?.pause()

                }

                PLAY -> {

                    if(mediaPlayer?.isPlaying == true){
                        mediaPlayer?.pause()
                    }else{
                        mediaPlayer?.start()
                    }

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



            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }


    override fun onDestroy() {
        Toast.makeText(this, "Service Stopped and Music Stopped", Toast.LENGTH_LONG).show()
        // myPlayer.stop()
    }

}