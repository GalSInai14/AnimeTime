package com.example.animetime.ui.single_anime_fragment


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.animetime.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingleAnimeViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()
    val anime = _id.switchMap { animeRepository.getAnime(it) }


    fun setId(id:Int){
        _id.value = id
    }
}