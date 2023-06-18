package com.example.animetime.ui.single_anime_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.animetime.R
import com.example.animetime.databinding.FragmentSingleAnimeBinding
import com.example.animetime.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleAnimeFragment : Fragment() {

    private var binding: FragmentSingleAnimeBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }
}


/*
class SingleCharacterFragment : Fragment() {


    private var binding: CharacterDetailFragmentBinding by autoCleared()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharacterDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //TODO: Observe your character Livedata in your ViewModel

        arguments?.getInt("id")?.let {

            //TODO: update the view model with the id data to trigger the character observer
        }
    }

    private fun updateCharacter(character: Character) {

        //TODO:Uncomment these lines to update the ui with the character
        */
/*
         binding.name.text = character.name
        binding.gender.text = character.gender
        binding.species.text = character.species
        binding.status.text = character.status
        Glide.with(requireContext()).load(character.picture).circleCrop().into(binding.image)
         *//*

    }*/
