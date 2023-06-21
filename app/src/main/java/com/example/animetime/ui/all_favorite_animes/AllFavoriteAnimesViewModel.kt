package com.example.animetime.ui.all_favorite_animes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.animetime.data.local_db.FavoriteAnimeDao
import com.example.animetime.data.models.favorite_anime.FavoriteAnime
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllFavoriteAnimesViewModel @Inject constructor(
    private val favoriteAnimeDao: FavoriteAnimeDao
) : ViewModel() {

    val favoriteAnimeList: LiveData<List<FavoriteAnime>> = favoriteAnimeDao.getAllFavoriteAnime()
}