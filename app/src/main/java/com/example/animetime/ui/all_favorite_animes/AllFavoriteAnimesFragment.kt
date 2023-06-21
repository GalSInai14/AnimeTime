package com.example.animetime.ui.all_favorite_animes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animetime.R
import com.example.animetime.databinding.FragmentAllFavoriteAnimesBinding
import com.example.animetime.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllFavoriteAnimesFragment : Fragment(),
    FavoriteAnimeAdapter.FavoriteAnimeItemListener {

    private var binding: FragmentAllFavoriteAnimesBinding by autoCleared()
    private val viewModel: AllFavoriteAnimesViewModel by viewModels()
    private lateinit var adapter: FavoriteAnimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllFavoriteAnimesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoriteAnimeAdapter(this)
        binding.AllFavoriteAnimeRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.AllFavoriteAnimeRecycler.adapter = adapter

        viewModel.favoriteAnimeList.observe(viewLifecycleOwner) { favoriteAnimeList ->
            adapter.submitList(favoriteAnimeList)
        }
    }

    override fun onFavoriteAnimeClick(animeId: Int) {
        val bundle = bundleOf(
            "id" to animeId,
            "arrivedFromFavorites" to true // Add this parameter
        )
        findNavController().navigate(
            R.id.action_allFavoriteAnimesFragment_to_singleAnimeFragment,
            bundle
        )
    }
}




