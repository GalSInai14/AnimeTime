package com.example.animetime.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.animetime.data.models.favorite_anime.FavoriteAnime
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAnimeDao {
    @Insert
    suspend fun insertFavoriteAnime(anime: FavoriteAnime)

    @Delete
    suspend fun deleteFavoriteAnime(anime: FavoriteAnime)

    @Query("SELECT * FROM favorite_animes")
    fun getAllFavoriteAnime(): LiveData<List<FavoriteAnime>>
}