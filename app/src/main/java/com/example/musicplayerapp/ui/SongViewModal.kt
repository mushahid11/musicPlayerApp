package com.example.musicplayerapp.ui

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicplayerapp.data.constant.AllSongsModel
import com.example.musicplayerapp.data.repo.SongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModal @Inject constructor(private val songRepository: SongRepository, application: Application) :AndroidViewModel(application) {


    var audioList = MutableLiveData<List<AllSongsModel>>()

   /* fun getAudioList(activity: Activity) {
        viewModelScope.launch {
            audioList.value = songRepository.loadAllSongs(activity)
        }

    }*/

    fun getAudioList(activity: Activity) {
        viewModelScope.launch(Dispatchers.IO) {
            audioList.postValue(songRepository.loadAllSongs(activity))
        }


    }

}