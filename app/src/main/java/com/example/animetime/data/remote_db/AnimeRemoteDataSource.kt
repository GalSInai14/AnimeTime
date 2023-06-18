package com.example.animetime.data.remote_db

import javax.inject.Inject

class AnimeRemoteDataSource @Inject constructor(private val animeService: AnimeService) :
    BaseDataSource() {

    suspend fun getAnimes() = getResult { animeService.getTopAnimes() }
    suspend fun getAnime(id: Int) = getResult { animeService.getAnimeById(id) }

}
