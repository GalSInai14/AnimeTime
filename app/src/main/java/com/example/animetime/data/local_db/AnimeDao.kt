package com.example.animetime.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.animetime.data.models.anime.Anime

@Dao
interface AnimeDao {

    @Query("SELECT * FROM animes")
    fun getAllAnimes(): LiveData<List<Anime>>

    @Query("SELECT * FROM animes WHERE mal_id = :id")
    fun getAnime(id: Int): LiveData<Anime>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimes(animes: List<Anime>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: Anime)
}