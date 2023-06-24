package com.example.animetime.data.repository

import com.example.animetime.data.local_db.MangaDao
import com.example.animetime.data.remote_db.MangaRemoteDataSource
import com.example.animetime.utils.performFetchingAndSaving
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MangaRepository @Inject constructor(
    private val remoteDataSource: MangaRemoteDataSource,
    private val localDataSource: MangaDao
) {

    fun getMangas(page: Int) = performFetchingAndSaving(
        { localDataSource.getAllMangas() },
        { remoteDataSource.getMangas(page) },
        { localDataSource.insertMangas(it.data) }
    )

    fun getManga(id: Int) = performFetchingAndSaving(
        { localDataSource.getManga(id) },
        { remoteDataSource.getManga(id) },
        { localDataSource.insertManga(it) }
    )
}