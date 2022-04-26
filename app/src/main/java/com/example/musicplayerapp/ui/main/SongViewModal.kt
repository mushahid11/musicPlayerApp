package com.example.musicplayerapp.ui.main

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicplayerapp.data.constant.AllSongsModel
import com.example.musicplayerapp.data.repo.SongRepository
import kotlinx.coroutines.launch

class SongViewModal(application: Application) : AndroidViewModel(application) {

    var audioRepo: SongRepository = SongRepository(application)
    var audioList = MutableLiveData<List<AllSongsModel>>()

    fun getAudioList(activity: Activity) {
        viewModelScope.launch {
            audioList.value = audioRepo.loadAllSongs(activity)
        }

    }

}