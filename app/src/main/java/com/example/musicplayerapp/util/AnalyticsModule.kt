package com.example.musicplayerapp.util

import com.example.musicplayerapp.data.repo.SongRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {

    private lateinit var appRepository: SongRepository

  /*  @Singleton
    @Provides
    fun Repositorypository(mediaPlayer: MediaPlayer): SongRepository {
        appRepository = SongRepository()
        return appRepository
    }*/

   /* @Singleton
    @Provides
    fun ProvidesMediaPlayer(@ApplicationContext appContext: Context):MediaPlayer{
        return MediaPlayer()
    }

    @Singleton
    @Provides
    fun provideWifiManager(@ApplicationContext appContext: Context) : WifiManager {
        return appContext.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }*/

}