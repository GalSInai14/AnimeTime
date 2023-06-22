package com.example.animetime.ui.all_favorite_mangas

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
import com.example.animetime.data.models.favorite_manga.FavoriteManga
import com.example.animetime.databinding.FragmentAllFavoriteAnimesBinding
import com.example.animetime.databinding.FragmentAllFavoriteMangasBinding
import com.example.animetime.databinding.FragmentAllMangasBinding
import com.example.animetime.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllFavoriteMangasFragment : Fragment(),
    FavoriteMangaAdapter.FavoriteMangaItemListener {

    private var binding: FragmentAllMangasBinding by autoCleared()
    private val viewModel: AllFavoriteMangasViewModel by viewModels()
    private lateinit var adapter: FavoriteMangaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllMangasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoriteMangaAdapter(this)
        binding.AllMangasRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.AllMangasRecycler.adapter = adapter

        viewModel.favoriteMangaList.observe(viewLifecycleOwner) { favoriteMangaList ->
            adapter.submitList(favoriteMangaList)
        }
    }

    override fun onFavoriteMangaClick(mangaId: Int) {
        val bundle = bundleOf(
            "id" to mangaId,
            "arrivedFromFavorites" to true // Add this parameter
        )
        findNavController().navigate(
            R.id.action_allFavoriteMangasFragment_to_singleMangaFragment,
            bundle
        )
    }
}




