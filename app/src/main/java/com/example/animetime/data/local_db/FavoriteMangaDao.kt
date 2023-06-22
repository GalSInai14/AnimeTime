package com.example.animetime.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.animetime.data.models.favorite_anime.FavoriteAnime
import com.example.animetime.data.models.favorite_manga.FavoriteManga

@Dao
interface FavoriteMangaDao {
    @Insert
    suspend fun insertFavoriteManga(manga: FavoriteManga)

    @Delete
    suspend fun deleteFavoriteManga(manga: FavoriteManga)

    @Query("SELECT * FROM favorite_mangas")
    fun getAllFavoriteManga(): LiveData<List<FavoriteManga>>
}