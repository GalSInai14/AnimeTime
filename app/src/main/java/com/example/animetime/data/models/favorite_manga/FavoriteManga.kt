package com.example.animetime.data.models.favorite_manga

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.animetime.data.local_db.ImagesTypeConverter
import com.example.animetime.data.local_db.PublishedTypeConverter
import com.example.animetime.data.models.Images
import com.example.animetime.data.models.Published

@Entity(tableName = "favorite_mangas")

data class FavoriteManga(
    @PrimaryKey
    val mal_id: Int,
    val title_english: String?,
    @TypeConverters(ImagesTypeConverter::class)
    val images: Images?,
    val score: Double?,
    val synopsis: String?,
    val status: String?,
    val volumes: Int?,
    @TypeConverters(PublishedTypeConverter::class)
    val published: Published?
) {
}