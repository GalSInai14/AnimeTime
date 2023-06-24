package com.example.animetime.ui.all_mangas

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
import com.example.animetime.databinding.FragmentAllMangasBinding
import com.example.animetime.ui.all_mangas.AllMangasViewModel
import com.example.animetime.ui.all_mangas.MangaAdapter
import com.example.animetime.utils.Loading
import com.example.animetime.utils.Success
import com.example.animetime.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllMangasFragment : Fragment(), MangaAdapter.MangaItemListener {

    private var binding: FragmentAllMangasBinding by autoCleared()

    private val viewModel: AllMangasViewModel by viewModels()

    private lateinit var adapter: MangaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentAllMangasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var isLoading = false


        binding.MangaFavButton.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_allMangasFragment_to_allFavoriteMangasFragment)
        })

        adapter = MangaAdapter(this)
        binding.AllMangasRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.AllMangasRecycler.adapter = adapter

        viewModel.mangas.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> {
                    isLoading = true
                    binding.progressBarID.isVisible = true
                }
                is Success -> {
                    isLoading = false
                    binding.progressBarID.isVisible = false
                    adapter.setMangas(ArrayList(it.status.data))
                }
                is Error -> {
                    isLoading = false
                    binding.progressBarID.isVisible = false
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
        binding.AllMangasRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                //val isSearchEmpty = binding.search.query.toString().isEmpty()
                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    val currentPage = viewModel.getCurrentPage()
                    viewModel.setCurrentPage(currentPage + 1)
                }
            }
        })
    }

    override fun onMangaClick(animeId: Int) {
        findNavController().navigate(
            R.id.action_allMangasFragment_to_singleMangaFragment,
            bundleOf("id" to animeId)
        )
    }

}
