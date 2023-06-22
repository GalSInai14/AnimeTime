package com.example.animetime.ui.single_manga_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.animetime.R
import com.example.animetime.data.local_db.AppDatabase
import com.example.animetime.data.local_db.FavoriteAnimeDao
import com.example.animetime.data.local_db.FavoriteMangaDao
import com.example.animetime.data.models.anime.Anime
import com.example.animetime.data.models.favorite_anime.FavoriteAnime
import com.example.animetime.data.models.favorite_manga.FavoriteManga
import com.example.animetime.data.models.manga.Manga
import com.example.animetime.databinding.FragmentSingleAnimeBinding
import com.example.animetime.databinding.FragmentSingleMangaBinding
import com.example.animetime.utils.Loading
import com.example.animetime.utils.Success
import com.example.animetime.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SingleMangaFragment : Fragment() {

    private var binding: FragmentSingleMangaBinding by autoCleared()
    private val viewModel: SingleMangaViewModel by viewModels()

    @Inject
    lateinit var mangaDatabase: AppDatabase

    private val favoriteMangaList: MutableLiveData<List<FavoriteManga>> = MutableLiveData<List<FavoriteManga>>().apply { value = emptyList() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingleMangaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.manga.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT)
                    .show()
                is Success -> {
                    updateManga(it.status.data!!)
                }
                is Error -> {
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        arguments?.getInt("id")?.let { mangaId ->
            viewModel.setId(mangaId)

            val arrivedFromFavorites = arguments?.getBoolean("arrivedFromFavorites") ?: false
            if (arrivedFromFavorites) {
                binding.FavouriteMangaBtn.visibility = View.GONE
            }
        }

        binding.FavouriteMangaBtn.setOnClickListener {
            val currentManga = viewModel.manga.value?.status?.data

            if (currentManga != null) {
                val favoriteMangaDao = mangaDatabase.favoriteMangaDao()

                if (isMangaInFavorites(currentManga)) {
                    removeMangaFromFavorites(currentManga, favoriteMangaDao)
                } else {
                    addMangaToFavorites(currentManga, favoriteMangaDao)
                }
            }
        }

        favoriteMangaList.observe(viewLifecycleOwner) { mangaList ->
            val currentManga = viewModel.manga.value?.status?.data
            if (currentManga != null) {
                if (isMangaInFavorites(currentManga)) {
                    binding.FavouriteMangaBtn.setImageResource(R.drawable.baseline_favorite_24)
                } else {
                    binding.FavouriteMangaBtn.setImageResource(R.drawable.baseline_favorite_border_24)
                }
            }
        }
    }

    private fun updateManga(manga: Manga) {

        val year = manga.published?.from
        val maxLength = 5

        binding.title.text = manga.title_english
        if (manga.volumes == null)
        {binding.epVolumesMangaPage.text = "Ongoing....."}
        else{binding.epVolumesMangaPage.text = manga.volumes.toString()}
        binding.ratingManga.text= manga.score.toString()

        if (year != null) {
            if (year.length > maxLength) {
                val trimmedYear = year.substring(0, maxLength)
                binding.relYearMangaPage.text = trimmedYear
            } else {
                binding.relYearMangaPage.text = year
            }
        } else {
            binding.relYearMangaPage.text = ""
        }


        binding.description.text = manga.synopsis
        binding.statusMangaPage.text = manga.status
        Glide.with(requireContext()).load(manga.images?.jpg?.image_url).into(binding.photo)

    }

    private fun isMangaInFavorites(manga: Manga): Boolean {
        val favoriteMangaList = favoriteMangaList.value
        return favoriteMangaList?.any { it.mal_id == manga.mal_id } ?: false
    }

    private fun addMangaToFavorites(manga: Manga, favoriteMangaDao: FavoriteMangaDao) {
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

        GlobalScope.launch(Dispatchers.IO) {
            favoriteMangaDao.insertFavoriteManga(favoriteManga)
        }

        val currentList = favoriteMangaList.value?.toMutableList() ?: mutableListOf()
        currentList.add(favoriteManga)
        favoriteMangaList.value = currentList

        binding.FavouriteMangaBtn.setImageResource(R.drawable.baseline_favorite_24)
        Toast.makeText(requireContext(), "Added to favorites", Toast.LENGTH_SHORT).show()
    }

    private fun removeMangaFromFavorites(manga: Manga, favoriteMangaDao: FavoriteMangaDao) {
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

        GlobalScope.launch(Dispatchers.IO) {
            favoriteMangaDao.deleteFavoriteManga(favoriteManga)
        }

        val currentList = favoriteMangaList.value?.toMutableList() ?: mutableListOf()
        currentList.removeAll { it.mal_id == manga.mal_id }
        favoriteMangaList.value = currentList

        binding.FavouriteMangaBtn.setImageResource(R.drawable.baseline_favorite_border_24)
        Toast.makeText(requireContext(), "Removed from favorites", Toast.LENGTH_SHORT).show()
    }
}
