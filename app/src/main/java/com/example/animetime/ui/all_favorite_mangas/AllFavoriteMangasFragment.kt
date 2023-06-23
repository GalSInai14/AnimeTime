package com.example.animetime.ui.all_favorite_mangas

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
import com.example.animetime.databinding.FragmentAllFavoriteMangasBinding
import com.example.animetime.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllFavoriteMangasFragment : Fragment(),
    FavoriteMangaAdapter.FavoriteMangaItemListener {

    private var binding: FragmentAllFavoriteMangasBinding by autoCleared()
    private val viewModel: AllFavoriteMangasViewModel by viewModels()
    private lateinit var adapter: FavoriteMangaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllFavoriteMangasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoriteMangaAdapter(this)
        binding.AllFavoriteMangaRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.AllFavoriteMangaRecycler.adapter = adapter

        viewModel.favoriteMangaList.observe(viewLifecycleOwner) { favoriteMangaList ->
            adapter.setFavoriteMangas(favoriteMangaList)
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
                    setTitle("Remove From Favorite Mangas")
                    setMessage("Do you want to remove this Manga from your favorite mangas list?")
                    setCancelable(false)
                    setPositiveButton("Yes") { _, _ ->
                        viewModel.removeFavoriteAnimeFromFavorites(item)
                        binding.AllFavoriteMangaRecycler.adapter!!.notifyItemRemoved(viewHolder.adapterPosition)
                    }
                    setNegativeButton("No") { _, _ ->
                        binding.AllFavoriteMangaRecycler.adapter!!.notifyItemChanged(viewHolder.adapterPosition)
                    }
                }.show()
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.AllFavoriteMangaRecycler)

    }

    override fun onFavoriteMangaClick(mangaId: Int) {
        val bundle = bundleOf(
            "id" to mangaId,
            "arrivedFromFavorites" to true
        )
        findNavController().navigate(
            R.id.action_allFavoriteMangasFragment_to_singleMangaFragment,
            bundle
        )
    }
}




