package com.example.animetime.data.remote_db

import com.example.animetime.data.models.anime.AllAnimes
import com.example.animetime.data.models.anime.Anime
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeService {
    @GET("top/anime")
    suspend fun getTopAnimes(): Response<AllAnimes>

    @GET("anime/{id}")
    suspend fun getAnimeById(@Path("id") id: Int): Response<Anime>
}