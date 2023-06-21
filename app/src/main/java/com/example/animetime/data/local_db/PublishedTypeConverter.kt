package com.example.animetime.data.local_db

import androidx.room.TypeConverter
import com.example.animetime.data.models.Images
import com.example.animetime.data.models.Published
import com.google.gson.Gson

class PublishedTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromPublished(published: Published?): String {
        return gson.toJson(published)
    }

    @TypeConverter
    fun toPublished(publishedJson: String): Published? {
        return if (publishedJson.isEmpty()) {
            null
        } else {
            gson.fromJson(publishedJson, Published::class.java)
        }
    }
}