package com.example.animetime.ui.all_favorite_animes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animetime.data.local_db.FavoriteAnimeDao
import com.example.animetime.data.models.favorite_anime.FavoriteAnime
import com.example.animetime.data.repository.FavoriteAnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllFavoriteAnimesViewModel @Inject constructor(
    private val favoriteAnimeRepository: FavoriteAnimeRepository,
) : ViewModel() {
    val favoriteAnimeList: LiveData<List<FavoriteAnime>> =
        favoriteAnimeRepository.getAllFavoriteAnimes()

    fun removeFavoriteAnimeFromFavorites(favoriteAnime: FavoriteAnime) {
        viewModelScope.launch() {
            favoriteAnimeRepository.removeFavoriteAnime(favoriteAnime)
        }
    }


}