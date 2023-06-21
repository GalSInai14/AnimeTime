package com.example.animetime.ui.single_anime_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        arguments?.getInt("id")?.let {
            viewModel.setId(it)
        }

        binding.FavouriteAnimeBtn.setOnClickListener(View.OnClickListener {
            val currentAnime = viewModel.anime.value?.status?.data

            if (currentAnime != null) {
                val favoriteAnimeDao = animeDatabase.favoriteAnimeDao()

                if (isAnimeInFavorites(currentAnime)) {
                    // Anime is already in the favorite list, so remove it
                    removeAnimeFromFavorites(currentAnime, favoriteAnimeDao)
                } else {
                    // Anime is not in the favorite list, so add it
                    addAnimeToFavorites(currentAnime, favoriteAnimeDao)
                }
            }
        })
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
        val favoriteAnimeList = viewModel.favoriteAnimeList.value
        return favoriteAnimeList?.contains(anime) ?: false
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

        val currentList = viewModel.favoriteAnimeList.value?.toMutableList() ?: mutableListOf()
        currentList.add(anime)
        viewModel.favoriteAnimeList.value = currentList

        GlobalScope.launch(Dispatchers.IO) {
            favoriteAnimeDao.insertFavoriteAnime(favoriteAnime)
        }

        // Update the icon to indicate it's a favorite
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

        val currentList = viewModel.favoriteAnimeList.value?.toMutableList() ?: mutableListOf()
        currentList.remove(anime)
        viewModel.favoriteAnimeList.notifyObserver()

        GlobalScope.launch(Dispatchers.IO) {
            favoriteAnimeDao.deleteFavoriteAnime(favoriteAnime)
        }

        // Update the icon to indicate it's not a favorite
        binding.FavouriteAnimeBtn.setImageResource(R.drawable.baseline_favorite_border_24)
        Toast.makeText(requireContext(), "Removed from favorites", Toast.LENGTH_SHORT).show()
    }
}

// Extension function to notify LiveData observers of a mutable list change
fun <T> MutableLiveData<List<T>>.notifyObserver() {
    this.value = this.value
}





