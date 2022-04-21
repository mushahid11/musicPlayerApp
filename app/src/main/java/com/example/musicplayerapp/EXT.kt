package com.example.musicplayerapp

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import com.example.musicplayerapp.data.constant.AllSongsModel



fun getTimeInMilles(time: Long): String {
    // Take Input in Long otherwise
    // overflow occur for some inputs.
    // Take Input in Long otherwise
    // overflow occur for some inputs.
    val milliseconds: Long = time

    // formula for conversion for
    // milliseconds to minutes.

    // formula for conversion for
    // milliseconds to minutes.
    val minutes = milliseconds / 1000 / 60

    // formula for conversion for
    // milliseconds to seconds

    // formula for conversion for
    // milliseconds to seconds
    val seconds = milliseconds / 1000 % 60

    // Print the output

    // Print the output
    Log.d(
        "getTimeInMilles", "getTimeInMilles: "
                + minutes + ":"
                + seconds
    )

    return "$minutes:$seconds"
}