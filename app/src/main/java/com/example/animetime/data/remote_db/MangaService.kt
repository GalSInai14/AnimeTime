package com.example.animetime.data.remote_db

import com.example.animetime.data.models.manga.AllMangas
import com.example.animetime.data.models.manga.Manga
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MangaService {
    @GET("top/manga")
    suspend fun getTopMangas(): Response<AllMangas>

    @GET("manga/{id}")
    suspend fun getMangaById(@Path("id") id: Int): Response<Manga>
}