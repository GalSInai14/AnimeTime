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

        binding.AnimeFavButton.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_allAnimeFragment_to_allFavoriteAnimesFragment)
        })

        adapter = AnimeAdapter(this)
        binding.AllAnimeRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.AllAnimeRecycler.adapter = adapter

        viewModel.animes.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> binding.progressBarID.isVisible = true
                is Success -> {
                    binding.progressBarID.isVisible = false
                    adapter.setAnimes(ArrayList(it.status.data))
                }
                is Error -> {
                    binding.progressBarID.isVisible = false
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    override fun onAnimeClick(animeId: Int) {
        findNavController().navigate(
            R.id.action_allAnimeFragment_to_singleAnimeFragment,
            bundleOf("id" to animeId)
        )
    }

}
