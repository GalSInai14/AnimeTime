package com.example.animetime.ui.single_manga_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.animetime.data.models.anime.Anime
import com.example.animetime.data.models.manga.Manga
import com.example.animetime.databinding.FragmentSingleAnimeBinding
import com.example.animetime.databinding.FragmentSingleMangaBinding
import com.example.animetime.utils.Loading
import com.example.animetime.utils.Success
import com.example.animetime.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleMangaFragment : Fragment() {

    private var binding: FragmentSingleMangaBinding by autoCleared()
    private val viewModel : SingleMangaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleMangaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.manga.observe(viewLifecycleOwner){
            when (it.status) {
                is Loading -> Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                is Success -> {
                    updateManga(it.status.data!!)
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

}





