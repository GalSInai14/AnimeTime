package com.example.animetime.ui.all_animes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.animetime.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllAnimesViewModel @Inject constructor(
    animeRepository: AnimeRepository
) : ViewModel() {

    private val currentPage = MutableLiveData<Int>()

    init {
        currentPage.value = 1
    }

    val animes = currentPage.switchMap { animeRepository.getAnimes(it) }

    fun setCurrentPage(page: Int) {
        currentPage.value = page
    }

    fun getCurrentPage(): Int {
        return currentPage.value ?: 1
    }
}