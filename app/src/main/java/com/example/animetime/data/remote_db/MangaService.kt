package com.example.animetime.data.remote_db

import com.example.animetime.data.models.manga.AllMangas
import com.example.animetime.data.models.manga.Manga
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MangaService {
    @GET("top/manga")
    suspend fun getTopMangas(
        @Query("page") page: Int
    ): Response<AllMangas>

    @GET("manga/{id}")
    suspend fun getMangaById(@Path("id") id: Int): Response<Manga>
}