package com.example.animetime.data.remote_db

import javax.inject.Inject

class MangaRemoteDataSource @Inject constructor(private val mangaService: MangaService) :
    BaseDataSource() {
    suspend fun getMangas(page: Int) = getResult { mangaService.getTopMangas(page) }
    suspend fun getManga(id: Int) = getResult { mangaService.getMangaById(id) }
}