package com.example.musicplayerapp.util

import android.content.Context
import android.media.MediaPlayer
import android.provider.MediaStore
import com.example.musicplayerapp.data.repo.SongRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {



   /* @Singleton
    @Provides
    fun Repositorypository(mediaPlayer: MediaPlayer): SongRepository {
        appRepository = SongRepository()
        return appRepository
    }*/

   /* @Singleton
    @Provides
    fun ProvidesMediaPlayer(@ApplicationContext appContext: Context): MediaPlayer {
        return MediaPlayer()
    }*/


    /*@Singleton
    @Provides
    fun provideWifiManager(@ApplicationContext appContext: Context) : WifiManager {
        return appContext.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }*/

}