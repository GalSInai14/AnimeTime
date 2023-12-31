package com.example.animetime.ui.all_animes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animetime.R
import com.example.animetime.databinding.FragmentAllAnimeBinding
import com.example.animetime.utils.Loading
import com.example.animetime.utils.Success
import com.example.animetime.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllAnimeFragment : Fragment(), AnimeAdapter.AnimeItemListener {

    private var binding: FragmentAllAnimeBinding by autoCleared()

    private val viewModel: AllAnimesViewModel by viewModels()

    private lateinit var adapter: AnimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentAllAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var isLoading = false

        binding.AnimeFavButton.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_allAnimeFragment_to_allFavoriteAnimesFragment)
        })

        adapter = AnimeAdapter(this)
        binding.searchAnime.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })

        binding.AllAnimeRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.AllAnimeRecycler.adapter = adapter


        viewModel.animes.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> {
                    isLoading = true
                    binding.progressBarID.isVisible = true
                }
                is Success -> {
                    isLoading = false
                    binding.progressBarID.isVisible = false
                    adapter.setAnimes(ArrayList(it.status.data))
                }
                is Error -> {
                    isLoading = false
                    binding.progressBarID.isVisible = false
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        binding.AllAnimeRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val isSearchEmpty = binding.searchAnime.query.toString().isEmpty()
                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    val currentPage = viewModel.getCurrentPage()
                    if (isSearchEmpty) {
                        viewModel.setCurrentPage(currentPage + 1)
                    }
                }
            }
        })
    }

    override fun onAnimeClick(animeId: Int) {
        findNavController().navigate(
            R.id.action_allAnimeFragment_to_singleAnimeFragment,
            bundleOf("id" to animeId)
        )
    }

}
