package com.example.animetime.ui.single_manga_fragment


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.animetime.data.repository.AnimeRepository
import com.example.animetime.data.repository.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingleMangaViewModel @Inject constructor(
    private val mangaRepository: MangaRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()
    val manga = _id.switchMap { mangaRepository.getManga(it) }


    fun setId(id:Int){
        _id.value = id
    }
}