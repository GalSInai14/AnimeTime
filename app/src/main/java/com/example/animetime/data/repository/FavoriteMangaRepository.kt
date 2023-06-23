package com.example.animetime.data.repository

import androidx.lifecycle.LiveData
import com.example.animetime.data.local_db.FavoriteAnimeDao
import com.example.animetime.data.local_db.FavoriteMangaDao
import com.example.animetime.data.models.favorite_anime.FavoriteAnime
import com.example.animetime.data.models.favorite_manga.FavoriteManga
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteMangaRepository @Inject constructor(private val favoriteMangaDao: FavoriteMangaDao) {

    val favoriteMangaList: LiveData<List<FavoriteManga>> = favoriteMangaDao.getAllFavoriteManga()

    fun getAllFavoriteManga(): LiveData<List<FavoriteManga>> {
        return favoriteMangaDao.getAllFavoriteManga()
    }

    suspend fun addFavoriteManga(favoriteManga: FavoriteManga) {
        withContext(Dispatchers.IO) {
            favoriteMangaDao.insertFavoriteManga(favoriteManga)
        }
    }

    suspend fun removeFavoriteManga(favoriteManga: FavoriteManga) {
        withContext(Dispatchers.IO) {
            favoriteMangaDao.deleteFavoriteManga(favoriteManga)
        }
    }

    suspend fun isFavoriteManga(id: Int): Boolean {
        return favoriteMangaDao.isFavoriteManga(id)
    }
}