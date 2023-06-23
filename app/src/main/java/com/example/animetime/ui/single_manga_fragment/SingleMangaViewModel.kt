package com.example.animetime.ui.single_manga_fragment


import androidx.lifecycle.*
import com.example.animetime.data.models.favorite_manga.FavoriteManga
import com.example.animetime.data.models.manga.Manga
import com.example.animetime.data.repository.FavoriteMangaRepository
import com.example.animetime.data.repository.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SingleMangaViewModel @Inject constructor(
    private val mangaRepository: MangaRepository,
    private val favoriteMangaRepository: FavoriteMangaRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()
    val manga = _id.switchMap { mangaRepository.getManga(it) }


    fun setId(id: Int) {
        _id.value = id
    }

    fun addMangaToFavorites(manga: Manga) {
        val favoriteManga = FavoriteManga(
            manga.mal_id,
            manga.title_english,
            manga.images,
            manga.score,
            manga.synopsis,
            manga.status,
            manga.volumes,
            manga.published
        )
        viewModelScope.launch() {
            favoriteMangaRepository.addFavoriteManga(favoriteManga)
        }
    }

    fun removeMangaFromFavorites(manga: Manga) {
        val favoriteManga = FavoriteManga(
            manga.mal_id,
            manga.title_english,
            manga.images,
            manga.score,
            manga.synopsis,
            manga.status,
            manga.volumes,
            manga.published
        )
        viewModelScope.launch() {
            favoriteMangaRepository.removeFavoriteManga(favoriteManga)
        }
    }

    fun isFavoriteManga(id: Int): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        viewModelScope.launch {
            favoriteMangaRepository.isFavoriteManga(id)
            val isFavoriteManga = withContext(Dispatchers.IO) {
                favoriteMangaRepository.isFavoriteManga(id)
            }
            liveData.postValue(isFavoriteManga)
        }
        return liveData
    }
}