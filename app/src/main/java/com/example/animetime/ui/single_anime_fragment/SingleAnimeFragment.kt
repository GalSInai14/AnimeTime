package com.example.animetime.ui.single_anime_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.animetime.data.models.anime.Anime
import com.example.animetime.databinding.FragmentSingleAnimeBinding
import com.example.animetime.utils.Loading
import com.example.animetime.utils.Success
import com.example.animetime.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleAnimeFragment : Fragment() {

    private var binding: FragmentSingleAnimeBinding by autoCleared()
    private val viewModel : SingleAnimeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.anime.observe(viewLifecycleOwner){
            when (it.status) {
                is Loading -> Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
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
    }

    private fun updateAnime(anime: Anime) {


        binding.title.text = anime.title_english
        binding.epNumberAnimePage.text = anime.episodes.toString()
        binding.ratingAnime.text = anime.score.toString()
        binding.relYearAnimePage.text = anime.year.toString()
        binding.description.text = anime.synopsis
        Glide.with(requireContext()).load(anime.images?.jpg?.image_url).into(binding.photo)

    }

}





