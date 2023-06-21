package com.example.animetime.data.remote_db

import javax.inject.Inject

class MangaRemoteDataSource @Inject constructor(private val mangaService: MangaService) :
    BaseDataSource() {
    suspend fun getMangas() = getResult { mangaService.getTopMangas() }
    suspend fun getManga(id: Int) = getResult { mangaService.getMangaById(id) }
}