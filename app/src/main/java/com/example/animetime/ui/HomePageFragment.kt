package com.example.animetime.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.animetime.R
import com.example.animetime.databinding.FragmentAllAnimeBinding
import com.example.animetime.databinding.FragmentHomePageBinding
import com.example.animetime.ui.all_animes.AllAnimesViewModel
import com.example.animetime.ui.all_animes.AnimeAdapter
import com.example.animetime.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageFragment : Fragment() {

    private var binding: FragmentHomePageBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.AnimeButton.setOnClickListener(View.OnClickListener {
            findNavController().navigate(
                R.id.action_homePageFragment_to_allAnimeFragment
            )
        })

        binding.MangaButton.setOnClickListener(View.OnClickListener {
            findNavController().navigate(
                R.id.action_homePageFragment_to_allMangasFragment
            )
        })
    }

}