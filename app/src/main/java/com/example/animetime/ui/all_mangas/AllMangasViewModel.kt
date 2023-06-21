package com.example.animetime.ui.all_mangas

import androidx.lifecycle.ViewModel
import com.example.animetime.data.repository.AnimeRepository
import com.example.animetime.data.repository.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllMangasViewModel @Inject constructor(
    mangaRepository: MangaRepository
) : ViewModel() {

    val mangas = mangaRepository.getMangas()
}