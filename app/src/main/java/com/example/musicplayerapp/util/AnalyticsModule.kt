package com.example.musicplayerapp.util

import android.media.MediaPlayer
import com.example.musicplayerapp.data.repo.SongRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object AnalyticsModule {


    @Singleton
    @Provides
    fun SongRepository(mediaPlayer: MediaPlayer): SongRepository = SongRepository(mediaPlayer)


}