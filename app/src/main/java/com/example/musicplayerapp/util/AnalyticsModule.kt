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
    }

    private lateinit var proMediaPlayer: MediaPlayer
    @Singleton
    @Provides
    fun ProvidesMediaPlayer(@ApplicationContext appContext: Context):MediaPlayer{
        proMediaPlayer=MediaPlayer()
        return proMediaPlayer
    }*/



}