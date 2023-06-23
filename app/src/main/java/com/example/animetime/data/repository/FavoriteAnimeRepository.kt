package com.example.animetime.data.repository

import androidx.lifecycle.LiveData
import com.example.animetime.data.local_db.FavoriteAnimeDao
import com.example.animetime.data.models.favorite_anime.FavoriteAnime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteAnimeRepository @Inject constructor(private val favoriteAnimeDao: FavoriteAnimeDao) {

    val favoriteAnimeList: LiveData<List<FavoriteAnime>> = favoriteAnimeDao.getAllFavoriteAnime()

    fun getAllFavoriteAnimes(): LiveData<List<FavoriteAnime>> {
        return favoriteAnimeDao.getAllFavoriteAnime()
    }

    suspend fun addFavoriteAnime(favoriteAnime: FavoriteAnime) {
        withContext(Dispatchers.IO) {
            favoriteAnimeDao.insertFavoriteAnime(favoriteAnime)
        }
    }

    suspend fun removeFavoriteAnime(favoriteAnime: FavoriteAnime) {
        withContext(Dispatchers.IO) {
            favoriteAnimeDao.deleteFavoriteAnime(favoriteAnime)
        }
    }

    suspend fun isFavoriteAnime(id: Int): Boolean {
        return favoriteAnimeDao.isFavoriteAnime(id)
    }


}
