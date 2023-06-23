package com.example.animetime.ui.all_favorite_animes

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
            adapter.setFavoriteAnimes(favoriteAnimeList)
        }

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) = makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                val item =
                    adapter.favoriteAt(viewHolder.adapterPosition)

                builder.apply {
                    setTitle("Remove From Favorite Animes")
                    setMessage("Do you want to remove this Manga from your favorite mangas list?")
                    setCancelable(false)
                    setPositiveButton("Yes") { _, _ ->
                        viewModel.removeFavoriteAnimeFromFavorites(item)
                        binding.AllFavoriteAnimeRecycler.adapter!!.notifyItemRemoved(viewHolder.adapterPosition)
                    }
                    setNegativeButton("No") { _, _ ->
                        binding.AllFavoriteAnimeRecycler.adapter!!.notifyItemChanged(viewHolder.adapterPosition)
                    }
                }.show()
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.AllFavoriteAnimeRecycler)

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




