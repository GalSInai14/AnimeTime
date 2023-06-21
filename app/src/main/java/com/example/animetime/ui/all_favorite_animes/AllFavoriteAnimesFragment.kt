package com.example.animetime.ui.all_favorite_animes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
//            when (it.status) {
//                is Loading -> Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
//                is Success -> {
//                    adapter.setAnimes(ArrayList(it.status.data))
//                }
//                is Error -> {
//                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
//                }
//                else -> {}
//            }
    }

    override fun onFavoriteAnimeClick(animeId: Int) {

    }
}




