package com.example.animetime.ui.all_favorite_mangas

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animetime.data.models.favorite_manga.FavoriteManga
import com.example.animetime.data.repository.FavoriteMangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllFavoriteMangasViewModel @Inject constructor(
    private val favoriteMangaRepository: FavoriteMangaRepository
) : ViewModel() {

    val favoriteMangaList: LiveData<List<FavoriteManga>> =
        favoriteMangaRepository.getAllFavoriteManga()

    fun removeFavoriteAnimeFromFavorites(favoriteManga: FavoriteManga) {
        viewModelScope.launch() {
            favoriteMangaRepository.removeFavoriteManga(favoriteManga)
        }
    }
}