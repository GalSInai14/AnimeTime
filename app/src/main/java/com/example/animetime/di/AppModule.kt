package com.example.animetime.di

import android.content.Context
import com.example.animetime.data.remote_db.AnimeService
import com.example.animetime.local_db.AppDatabase
import com.example.animetime.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideAnimeService(retrofit: Retrofit): AnimeService =
        retrofit.create(AnimeService::class.java)

    @Provides
    @Singleton
    fun provideLocalDataBase(@ApplicationContext appContext: Context): AppDatabase =
        AppDatabase.getDatabase(appContext)

    @Provides
    @Singleton
    fun provideAnimeDao(database: AppDatabase) = database.animeDao()
}