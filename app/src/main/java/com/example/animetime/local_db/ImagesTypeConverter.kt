package com.example.animetime.local_db

import androidx.room.TypeConverter
import com.example.animetime.data.models.Images
import com.google.gson.Gson

class ImagesTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromImages(images: Images): String {
        return gson.toJson(images)
    }

    @TypeConverter
    fun toImages(imagesJson: String): Images {
        return gson.fromJson(imagesJson, Images::class.java)
    }
}