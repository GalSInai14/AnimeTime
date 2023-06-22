package com.example.animetime.ui.all_favorite_mangas

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.animetime.data.local_db.FavoriteMangaDao
import com.example.animetime.data.models.favorite_manga.FavoriteManga
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllFavoriteMangasViewModel @Inject constructor(
    private val favoriteMangaDao: FavoriteMangaDao
) : ViewModel() {

    val favoriteMangaList: LiveData<List<FavoriteManga>> = favoriteMangaDao.getAllFavoriteManga()
}