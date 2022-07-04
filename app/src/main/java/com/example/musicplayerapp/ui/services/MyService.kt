package com.example.musicplayerapp.ui.services


import android.app.*
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.musicplayerapp.R
import com.example.musicplayerapp.data.constant.AllSongsModel
import com.example.musicplayerapp.data.constant.AppConstant.CHANNEL_ID
import com.example.musicplayerapp.data.constant.AppConstant.NEXT
import com.example.musicplayerapp.data.constant.AppConstant.PAUSE
import com.example.musicplayerapp.data.constant.AppConstant.PLAY
import com.example.musicplayerapp.data.constant.AppConstant.PREVIOUS
import com.example.musicplayerapp.data.constant.AppConstant.currentSongIndex
import com.example.musicplayerapp.ui.player.Player
import com.example.musicplayerapp.util.media.mediaPlayer
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException


@AndroidEntryPoint
class MyService : Service() {

    companion object {
        var songsList: List<AllSongsModel>? = null
    }

    private var durartion: String? = null
    var path: String? = null


    override fun onBind(p0: Intent?): IBinder? {

        return null
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Service Started and Playing Music", Toast.LENGTH_LONG).show()

        path = intent?.getStringExtra("path")
        val name = intent?.getStringExtra("name")
        durartion = intent?.getStringExtra("duration")
        songsList = intent?.getSerializableExtra("LIST") as List<AllSongsModel>?

        showNotification(name)






        playMusic(path)

        return START_NOT_STICKY
    }

    private fun showNotification(name: String?) {

        val prevIntent = Intent(baseContext, Receiver::class.java).setAction(PREVIOUS)
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


        val nextIntent = Intent(baseContext, Receiver::class.java).setAction(NEXT)
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


        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                CHANNEL_ID,
                "MyApp", NotificationManager.IMPORTANCE_DEFAULT
            )
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
            channel
        )
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(name)
            .setSmallIcon(R.drawable.music_notes)
            .setContentText("Hello! This is a notification.")
            .addAction(R.drawable.ic_baseline_fast_rewind_24, "previous", prevPendingIntentt)
            .addAction(R.drawable.ic_baseline_play_arrow_24, "play", playPendingIntent)
            .addAction(R.drawable.ic_baseline_fast_forward_24, "Next", nextPendingIntent)
            .setAutoCancel(true)
            .setContentText("").build()
        startForeground(1, notification)
    }


    override fun onCreate() {
        //   playMusic(path, Duration)
        Toast.makeText(this, "Service Successfully Created", Toast.LENGTH_LONG).show()

    }


    private fun playMusic(path: String?) {

        try {
            mediaPlayer?.apply {
                setDataSource(path) //Write your location here
                prepare()
                start()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    class Receiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            when (intent.action) {
                 PAUSE -> {
                   mediaPlayer?.pause()


                     val intent =
                         Intent(context, Player.ReceiverMain::class.java).setAction(
                             PAUSE
                         )

                     val nextPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                         PendingIntent.getBroadcast(
                             context,
                             0,
                             intent,
                             FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
                         )
                     } else {
                         PendingIntent.getBroadcast(context, 0, intent, FLAG_UPDATE_CURRENT)
                     }
                     context.sendBroadcast(intent)

                 }

                PLAY -> {
                    if (mediaPlayer?.isPlaying == true) {
                        mediaPlayer?.pause()

                        val intent =
                            Intent(context, Player.ReceiverMain::class.java).setAction(
                                PAUSE
                            )

                        val nextPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            PendingIntent.getBroadcast(
                                context,
                                0,
                                intent,
                                FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
                            )
                        } else {
                            PendingIntent.getBroadcast(context, 0, intent, FLAG_UPDATE_CURRENT)
                        }
                       context.sendBroadcast(intent)

                    } else {
                        mediaPlayer?.start()

                        val intent =
                            Intent(context, Player.ReceiverMain::class.java).setAction(
                                PLAY
                            )
                        val nextPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            PendingIntent.getBroadcast(
                                context,
                                0,
                                intent,
                                FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
                            )
                        } else {
                            PendingIntent.getBroadcast(context, 0, intent, FLAG_UPDATE_CURRENT)
                        }
                        context.sendBroadcast(intent)
                    }

                }

                PREVIOUS -> {
                    prev()
                    val intent = Intent(context, Player.ReceiverMain::class.java).setAction(
                        PREVIOUS
                    )
                    val nextPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        PendingIntent.getBroadcast(
                            context,
                            0,
                            intent,
                            FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
                        )
                    } else {
                        PendingIntent.getBroadcast(context, 0, intent, FLAG_UPDATE_CURRENT)
                    }
                    context.sendBroadcast(intent)
                }

                NEXT -> {
                    next()
                    val intent =
                        Intent(context, Player.ReceiverMain::class.java).setAction(NEXT)
                    val nextPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        PendingIntent.getBroadcast(
                            context,
                            0,
                            intent,
                            FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
                        )
                    } else {
                        PendingIntent.getBroadcast(context, 0, intent, FLAG_UPDATE_CURRENT)
                    }
                    context.sendBroadcast(intent)
                }
            }


        }


        fun next() {

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

        private fun playSong(songIndex: Int) {
            //Play song
            try {
                mediaPlayer?.apply {
                    reset()
                    setDataSource(songsList?.get(songIndex)?.path)
                    prepare()
                    start()
                }

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
    }

}