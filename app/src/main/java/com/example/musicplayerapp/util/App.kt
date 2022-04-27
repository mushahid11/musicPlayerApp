package com.example.musicplayerapp.util

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App: Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()

    }
}