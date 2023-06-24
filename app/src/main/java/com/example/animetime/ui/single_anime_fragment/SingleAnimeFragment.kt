package com.example.animetime.ui.single_anime_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.animetime.R
import com.example.animetime.data.models.anime.Anime
import com.example.animetime.data.models.favorite_anime.FavoriteAnime
import com.example.animetime.databinding.FragmentSingleAnimeBinding
import com.example.animetime.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlin.Error

@AndroidEntryPoint
class SingleAnimeFragment : Fragment() {

    private var binding: FragmentSingleAnimeBinding by autoCleared()
    private val viewModel: SingleAnimeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingleAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animeId = arguments?.getInt("id")
        animeId?.let { id ->
            viewModel.setId(id)
        }

        viewModel.anime.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                is Success -> {
                    binding.progressBarID.isVisible = false

                    val anime = resource.status.data
                    anime?.let { updateAnimeDetails(it) }
                }
                is Error -> {
                    binding.progressBarID.isVisible = false

                    Toast.makeText(requireContext(), resource.status.message, Toast.LENGTH_SHORT)
                        .show()
                }
                is Loading -> {
                    binding.progressBarID.isVisible = true
                }
                else -> {}
            }
        }

        viewModel.isFavoriteAnime(animeId as Int).observe(viewLifecycleOwner) { favoriteAnime ->

            if (favoriteAnime) {
                binding.FavouriteAnimeBtn.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                binding.FavouriteAnimeBtn.setImageResource(R.drawable.baseline_favorite_border_24)
            }
        }

        binding.FavouriteAnimeBtn.setOnClickListener {
            val currentAnime = viewModel.anime.value?.status?.data
            viewModel.isFavoriteAnime(animeId).observe(viewLifecycleOwner) {
                if (it) {
                    viewModel.removeAnimeFromFavorites(currentAnime as Anime)
                    binding.FavouriteAnimeBtn.setImageResource(R.drawable.baseline_favorite_border_24)
                    Toast.makeText(requireContext(), getString(R.string.removeFavoriteAnimesToast), Toast.LENGTH_SHORT).show()


                } else {
                    viewModel.addAnimeToFavorites(currentAnime as Anime)
                    binding.FavouriteAnimeBtn.setImageResource(R.drawable.baseline_favorite_24)
                    Toast.makeText(requireContext(), getString(R.string.addFavoriteMangaToast), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateAnimeDetails(anime: Anime) {
        binding.title.text = anime.title_english
        binding.epNumberAnimePage.text = anime.episodes.toString()
        binding.ratingAnime.text = anime.score.toString()
        binding.relYearAnimePage.text = anime.year.toString()
        binding.description.text = anime.synopsis
        Glide.with(requireContext()).load(anime.images?.jpg?.image_url).into(binding.photo)
    }
}

