package com.example.animetime.ui.all_animes

import androidx.lifecycle.ViewModel
import com.example.animetime.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllAnimesViewModel @Inject constructor(
    animeRepository: AnimeRepository
) : ViewModel() {

    val animes = animeRepository.getAnimes()


}