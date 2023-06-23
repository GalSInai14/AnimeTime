package com.example.animetime.ui.single_anime_fragment

import androidx.lifecycle.*
import com.example.animetime.data.models.anime.Anime
import com.example.animetime.data.models.favorite_anime.FavoriteAnime
import com.example.animetime.data.repository.AnimeRepository
import com.example.animetime.data.repository.FavoriteAnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SingleAnimeViewModel @Inject constructor(
    private val animeRepository: AnimeRepository,
    private val favoriteAnimeRepository: FavoriteAnimeRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()


    val anime = _id.switchMap { animeRepository.getAnime(it) }

    fun setId(id: Int) {
        _id.value = id
    }

    fun addAnimeToFavorites(anime: Anime) {
        val favoriteAnime = FavoriteAnime(
            anime.mal_id,
            anime.title_english,
            anime.images,
            anime.episodes,
            anime.score,
            anime.synopsis,
            anime.year
        )
        viewModelScope.launch() {
            favoriteAnimeRepository.addFavoriteAnime(favoriteAnime)
        }
    }

    fun removeAnimeFromFavorites(anime: Anime) {
        val favoriteAnime = FavoriteAnime(
            anime.mal_id,
            anime.title_english,
            anime.images,
            anime.episodes,
            anime.score,
            anime.synopsis,
            anime.year
        )
        viewModelScope.launch() {
            favoriteAnimeRepository.removeFavoriteAnime(favoriteAnime)
        }
    }

    fun isFavoriteAnime(id: Int): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        viewModelScope.launch {
            favoriteAnimeRepository.isFavoriteAnime(id)
            val isFavoriteAnime = withContext(Dispatchers.IO) {
                favoriteAnimeRepository.isFavoriteAnime(id)
            }
            liveData.postValue(isFavoriteAnime)
        }
        return liveData
    }
}
