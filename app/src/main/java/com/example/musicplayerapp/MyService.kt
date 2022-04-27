package com.example.musicplayerapp


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.musicplayerapp.data.constant.AllSongsModel
import com.example.musicplayerapp.util.media.mediaPlayer
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyService : Service() {

    private var songsList: List<AllSongsModel>? = null

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
        //songsList = intent?.getSerializableExtra("LIST") as List<AllSongsModel>?

        Log.d("onBind:", "onBind: $path")
        Log.d("onBind:", "onBind: $name")
        Log.d("onBind:", "onBind: $Duration")

        showNotification()

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


    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification() {
      /*  val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, "channelID")
                .setSmallIcon(R.drawable.stat_notify_call_mute)
                .setContentTitle("Notification")
                .setContentText("Hello! This is a notification.")
                .setAutoCancel(true)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1
        createChannel(notificationManager)
        notificationManager.notify(notificationId, notificationBuilder.build())*/

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
            .setAutoCancel(true)
            .setContentText("").build()
        startForeground(1, notification)

    }

    private fun createChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT < 26) {
            return
        }
        val channel =
            NotificationChannel("channelID", "name", NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = "Hello! This is a notification."
        notificationManager.createNotificationChannel(channel)
    }


}