package com.example.animetime.ui.single_manga_fragment

import android.annotation.SuppressLint
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
import com.example.animetime.data.models.manga.Manga
import com.example.animetime.databinding.FragmentSingleMangaBinding
import com.example.animetime.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlin.Error

@AndroidEntryPoint
class SingleMangaFragment : Fragment() {

    private var binding: FragmentSingleMangaBinding by autoCleared()
    private val viewModel: SingleMangaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingleMangaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mangaId = arguments?.getInt("id")
        mangaId?.let { id ->
            viewModel.setId(id)
        }

        viewModel.manga.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                is Success -> {
                    binding.progressBarID.isVisible = false
                    val manga = resource.status.data
                    manga?.let { updateMangaDetails(it) }
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

        viewModel.isFavoriteManga(mangaId as Int).observe(viewLifecycleOwner) { favoriteManga ->

            if (favoriteManga) {
                binding.FavouriteMangaBtn.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                binding.FavouriteMangaBtn.setImageResource(R.drawable.baseline_favorite_border_24)
            }
        }

        binding.FavouriteMangaBtn.setOnClickListener {
            val currentManga = viewModel.manga.value?.status?.data
            viewModel.isFavoriteManga(mangaId).observe(viewLifecycleOwner) {
                if (it) {
                    viewModel.removeMangaFromFavorites(currentManga as Manga)
                    binding.FavouriteMangaBtn.setImageResource(R.drawable.baseline_favorite_border_24)

                } else {
                    viewModel.addMangaToFavorites(currentManga as Manga)
                    binding.FavouriteMangaBtn.setImageResource(R.drawable.baseline_favorite_24)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateMangaDetails(manga: Manga) {
        val releaseYear = manga.published?.from
        val maxLength = 4

        if (releaseYear != null) {
            if (releaseYear.length > maxLength) {
                val trimmedReleaseYear = releaseYear.substring(0, maxLength)
                binding.relYearMangaPage.text = trimmedReleaseYear
            } else {
                binding.relYearMangaPage.text = releaseYear
            }
        } else {
            binding.relYearMangaPage.text = ""
        }
        if (manga.status.toString().equals("publishing", ignoreCase = true)) {
            binding.epVolumesMangaPage.text = getString(R.string.ongoing)
        } else {
            binding.epVolumesMangaPage.text = manga.volumes.toString()
        }

        binding.title.text = manga.title_english
        binding.ratingManga.text = manga.score.toString()
        binding.statusMangaPage.text = manga.status.toString()
        binding.description.text = manga.synopsis
        Glide.with(requireContext()).load(manga.images?.jpg?.image_url).into(binding.photo)
    }
}
