package com.example.animetime.data.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.animetime.data.models.anime.Anime
import com.example.animetime.data.models.favorite_anime.FavoriteAnime
import com.example.animetime.data.models.favorite_manga.FavoriteManga
import com.example.animetime.data.models.manga.Manga

@Database(entities = [Anime::class, Manga::class, FavoriteAnime::class, FavoriteManga::class], version = 5, exportSchema = false)
@TypeConverters(value = [ImagesTypeConverter::class, PublishedTypeConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao
    abstract fun mangaDao(): MangaDao
    abstract fun favoriteAnimeDao(): FavoriteAnimeDao
    abstract fun favoriteMangaDao(): FavoriteMangaDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "animetime_db"
                )
                    .fallbackToDestructiveMigration().build().also {
                        instance = it
                    }
            }
        }
    }
}