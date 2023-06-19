package com.example.animetime.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.animetime.data.models.Anime

@Database(entities = [Anime::class], version = 2, exportSchema = false)
@TypeConverters(value = [ImagesTypeConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "animes_db"
                )
                    .fallbackToDestructiveMigration().build().also {
                        instance = it
                    }
            }
        }
    }
}