package com.example.animetime.ui.all_favorite_animes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animetime.data.models.favorite_anime.FavoriteAnime
import com.example.animetime.databinding.ItemAnimeBinding

class FavoriteAnimeAdapter(private val listener: FavoriteAnimeItemListener) :
    ListAdapter<FavoriteAnime, FavoriteAnimeAdapter.FavoriteAnimeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAnimeViewHolder {
        val binding =
            ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteAnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteAnimeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FavoriteAnimeViewHolder(private val binding: ItemAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoriteAnime: FavoriteAnime) {
            val synopsis = favoriteAnime.synopsis
            val maxLength = 50

            binding.animecardTitle.text = favoriteAnime.title_english
            if (synopsis != null) {
                if (synopsis.length > maxLength) {
                    val trimmedSynopsis = synopsis.substring(0, maxLength) + "..."
                    binding.animecardDesc.text = trimmedSynopsis
                } else {
                    binding.animecardDesc.text = synopsis
                }
            } else {
                binding.animecardDesc.text = ""
            }

            Glide.with(binding.root)
                .load(favoriteAnime.images?.jpg?.image_url)
                .into(binding.animecardImage)

            binding.root.setOnClickListener {
                listener.onFavoriteAnimeClick(favoriteAnime.mal_id)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<FavoriteAnime>() {
        override fun areItemsTheSame(oldItem: FavoriteAnime, newItem: FavoriteAnime): Boolean {
            return oldItem.mal_id == newItem.mal_id
        }

        override fun areContentsTheSame(oldItem: FavoriteAnime, newItem: FavoriteAnime): Boolean {
            return oldItem == newItem
        }
    }

    interface FavoriteAnimeItemListener {
        fun onFavoriteAnimeClick(animeId: Int)
    }
}
