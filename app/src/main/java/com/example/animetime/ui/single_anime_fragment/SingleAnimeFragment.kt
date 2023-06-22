package com.example.animetime.ui.single_anime_fragment

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
import com.example.animetime.data.models.anime.Anime
import com.example.animetime.data.models.favorite_anime.FavoriteAnime
import com.example.animetime.databinding.FragmentSingleAnimeBinding
import com.example.animetime.utils.Loading
import com.example.animetime.utils.Success
import com.example.animetime.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SingleAnimeFragment : Fragment() {

    private var binding: FragmentSingleAnimeBinding by autoCleared()
    private val viewModel: SingleAnimeViewModel by viewModels()

    @Inject
    lateinit var animeDatabase: AppDatabase

    private val favoriteAnimeList: MutableLiveData<List<FavoriteAnime>> = MutableLiveData<List<FavoriteAnime>>().apply { value = emptyList() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingleAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.anime.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT)
                    .show()
                is Success -> {
                    updateAnime(it.status.data!!)
                }
                is Error -> {
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        arguments?.getInt("id")?.let { animeId ->
            viewModel.setId(animeId)

            val arrivedFromFavorites = arguments?.getBoolean("arrivedFromFavorites") ?: false
            if (arrivedFromFavorites) {
                binding.FavouriteAnimeBtn.visibility = View.GONE
            }
        }

        binding.FavouriteAnimeBtn.setOnClickListener {
            val currentAnime = viewModel.anime.value?.status?.data

            if (currentAnime != null) {
                val favoriteAnimeDao = animeDatabase.favoriteAnimeDao()

                if (isAnimeInFavorites(currentAnime)) {
                    removeAnimeFromFavorites(currentAnime, favoriteAnimeDao)
                } else {
                    addAnimeToFavorites(currentAnime, favoriteAnimeDao)
                }
            }
        }

        favoriteAnimeList.observe(viewLifecycleOwner) { animeList ->
            val currentAnime = viewModel.anime.value?.status?.data
            if (currentAnime != null) {
                if (isAnimeInFavorites(currentAnime)) {
                    binding.FavouriteAnimeBtn.setImageResource(R.drawable.baseline_favorite_24)
                } else {
                    binding.FavouriteAnimeBtn.setImageResource(R.drawable.baseline_favorite_border_24)
                }
            }
        }
    }

    private fun updateAnime(anime: Anime) {
        binding.title.text = anime.title_english
        binding.epNumberAnimePage.text = anime.episodes.toString()
        binding.ratingAnime.text = anime.score.toString()
        binding.relYearAnimePage.text = anime.year.toString()
        binding.description.text = anime.synopsis
        Glide.with(requireContext()).load(anime.images?.jpg?.image_url).into(binding.photo)
    }

    private fun isAnimeInFavorites(anime: Anime): Boolean {
        val favoriteAnimeList = favoriteAnimeList.value
        return favoriteAnimeList?.any { it.mal_id == anime.mal_id } ?: false
    }

    private fun addAnimeToFavorites(anime: Anime, favoriteAnimeDao: FavoriteAnimeDao) {
        val favoriteAnime = FavoriteAnime(
            anime.mal_id,
            anime.title_english,
            anime.images,
            anime.episodes,
            anime.score,
            anime.synopsis,
            anime.year
        )

        GlobalScope.launch(Dispatchers.IO) {
            favoriteAnimeDao.insertFavoriteAnime(favoriteAnime)
        }

        val currentList = favoriteAnimeList.value?.toMutableList() ?: mutableListOf()
        currentList.add(favoriteAnime)
        favoriteAnimeList.value = currentList

        binding.FavouriteAnimeBtn.setImageResource(R.drawable.baseline_favorite_24)
        Toast.makeText(requireContext(), "Added to favorites", Toast.LENGTH_SHORT).show()
    }

    private fun removeAnimeFromFavorites(anime: Anime, favoriteAnimeDao: FavoriteAnimeDao) {
        val favoriteAnime = FavoriteAnime(
            anime.mal_id,
            anime.title_english,
            anime.images,
            anime.episodes,
            anime.score,
            anime.synopsis,
            anime.year
        )

        GlobalScope.launch(Dispatchers.IO) {
            favoriteAnimeDao.deleteFavoriteAnime(favoriteAnime)
        }

        val currentList = favoriteAnimeList.value?.toMutableList() ?: mutableListOf()
        currentList.removeAll { it.mal_id == anime.mal_id }
        favoriteAnimeList.value = currentList

        binding.FavouriteAnimeBtn.setImageResource(R.drawable.baseline_favorite_border_24)
        Toast.makeText(requireContext(), "Removed from favorites", Toast.LENGTH_SHORT).show()
    }
}
