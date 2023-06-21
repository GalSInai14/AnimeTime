package com.example.animetime.ui.all_mangas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animetime.R
import com.example.animetime.databinding.FragmentAllAnimeBinding
import com.example.animetime.databinding.FragmentAllMangasBinding
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

        adapter = MangaAdapter(this)
        binding.AllMangasRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.AllMangasRecycler.adapter = adapter

        viewModel.mangas.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                is Success -> {
                    adapter.setMangas(ArrayList(it.status.data))
                }
                is Error -> {
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

   override fun onMangaClick(animeId: Int) {
       findNavController().navigate(
           R.id.action_allMangasFragment_to_singleMangaFragment,
           bundleOf("id" to animeId)
      )
   }
}
