package com.example.animetime.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.animetime.data.models.manga.Manga

@Dao
interface MangaDao {
    @Query("SELECT * FROM mangas")
    fun getAllMangas(): LiveData<List<Manga>>

    @Query("SELECT * FROM mangas WHERE mal_id = :id")
    fun getManga(id: Int): LiveData<Manga>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMangas(animes: List<Manga>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManga(anime: Manga)
}