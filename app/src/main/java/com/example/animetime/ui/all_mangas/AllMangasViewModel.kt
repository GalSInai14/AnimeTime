package com.example.animetime.ui.all_mangas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.animetime.data.repository.AnimeRepository
import com.example.animetime.data.repository.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllMangasViewModel @Inject constructor(
    mangaRepository: MangaRepository
) : ViewModel() {
    private val currentPage = MutableLiveData<Int>()

    init {
        currentPage.value = 1
    }

    val mangas = currentPage.switchMap { mangaRepository.getMangas(it) }

    fun setCurrentPage(page: Int) {
        currentPage.value = page
    }

    fun getCurrentPage(): Int {
        return currentPage.value ?: 1
    }


}