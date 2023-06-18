package com.example.animetime.repository

import com.example.animetime.data.remote_db.AnimeRemoteDataSource
import com.example.animetime.local_db.AnimeDao
import com.example.animetime.utils.performFetchingAndSaving
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeRepository @Inject constructor(
    private val remoteDataSource: AnimeRemoteDataSource,
    private val localDataSource: AnimeDao
) {

    fun getAnimes() = performFetchingAndSaving(
        { localDataSource.getAllAnimes() },
        { remoteDataSource.getAnimes() },
        { localDataSource.insertAnimes(it.data) }
    )

    fun getAnime(id: Int) = performFetchingAndSaving(
        { localDataSource.getAnime(id) },
        { remoteDataSource.getAnime(id) },
        { localDataSource.insertAnime(it) }
    )
}