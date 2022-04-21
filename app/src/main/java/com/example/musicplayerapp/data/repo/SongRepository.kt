package com.example.musicplayerapp.data.repo

import android.app.Activity
import android.app.Application
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import com.example.musicplayerapp.R
import com.example.musicplayerapp.data.constant.AllSongsModel
import com.example.musicplayerapp.getTimeInMilles

class SongRepository(private val application: Application) {


    private val list: MutableList<AllSongsModel> = mutableListOf()
    fun loadAllSongs(activity: Activity) :List<AllSongsModel>{

        //Retrieve a list of Music files currently listed in the Media store DB via URI.

        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION
        )

        val cursor = activity.managedQuery(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            null
        )

        val songs: MutableList<String> = ArrayList()
        while (cursor.moveToNext()) {
            songs.add(
                cursor.getString(0)
                    .toString() + "||" + cursor.getString(1) + "||" + cursor.getString(2) + "||" + cursor.getString(
                    3
                ) + "||" + cursor.getString(4) + "||" + cursor.getString(5)
            )

            Log.d(
                "loadAllSongs", "loadAllSongs: " +
                        "||" + cursor.getString(
                    3
                ) + "||" + cursor.getString(4) + "||" + cursor.getString(5)
            )

            list.add(
                AllSongsModel(
                    R.drawable.ic_baseline_library_music_24,
                    cursor.getString(2).toString(),
                    getTimeInMilles(cursor.getLong(5)),
                    cursor.getString(3)
                )
            )

        }
        return list
    }


}