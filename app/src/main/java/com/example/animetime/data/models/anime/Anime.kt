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
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Anime) return false

        return mal_id == other.mal_id &&
                title_english == other.title_english &&
                images == other.images &&
                episodes == other.episodes &&
                score == other.score &&
                synopsis == other.synopsis &&
                year == other.year
    }
}