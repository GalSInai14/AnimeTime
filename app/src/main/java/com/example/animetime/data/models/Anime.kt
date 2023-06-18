package com.example.animetime.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animes")
data class Anime(
    @PrimaryKey
    val mal_id: Int,
    val title_english: String,
//    val images: Images,
    val episodes: Int,
    val score: Double,
    val synopsis: String,
    val year: Int

) {
}