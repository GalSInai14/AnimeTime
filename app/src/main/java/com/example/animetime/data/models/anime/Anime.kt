package com.example.animetime.data.models.anime

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.animetime.data.local_db.ImagesTypeConverter
import com.example.animetime.data.models.Images

@Entity(tableName = "animes")
data class Anime(
    @PrimaryKey
    val mal_id: Int,
    val title_english: String?,
    @TypeConverters(ImagesTypeConverter::class)
    val images: Images?,
    val episodes: Int?,
    val score: Double?,
    val synopsis: String?,
    val year: Int?

) {
}