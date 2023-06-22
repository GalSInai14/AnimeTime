package com.example.animetime.ui.all_favorite_mangas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animetime.data.models.favorite_anime.FavoriteAnime
import com.example.animetime.data.models.favorite_manga.FavoriteManga
import com.example.animetime.databinding.ItemAnimeBinding
import com.example.animetime.databinding.ItemMangaBinding

class FavoriteMangaAdapter(private val listener: FavoriteMangaItemListener) :
    ListAdapter<FavoriteManga, FavoriteMangaAdapter.FavoriteMangaViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMangaViewHolder {
        val binding = ItemMangaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteMangaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteMangaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FavoriteMangaViewHolder(private val binding: ItemMangaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoriteManga: FavoriteManga) {
            val synopsis = favoriteManga.synopsis
            val maxLength = 50

            binding.mangaCardTitle.text = favoriteManga.title_english
            if (synopsis != null) {
                if (synopsis.length > maxLength) {
                    val trimmedSynopsis = synopsis.substring(0, maxLength) + "..."
                    binding.mangaCardDesc.text = trimmedSynopsis
                } else {
                    binding.mangaCardDesc.text = synopsis
                }
            } else {
                binding.mangaCardDesc.text = ""
            }

            Glide.with(binding.root)
                .load(favoriteManga.images?.jpg?.image_url)
                .into(binding.mangaCardImage)

            binding.root.setOnClickListener {
                listener.onFavoriteMangaClick(favoriteManga.mal_id)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<FavoriteManga>() {
        override fun areItemsTheSame(oldItem: FavoriteManga, newItem: FavoriteManga): Boolean {
            return oldItem.mal_id == newItem.mal_id
        }

        override fun areContentsTheSame(oldItem: FavoriteManga, newItem: FavoriteManga): Boolean {
            return oldItem == newItem
        }
    }

    interface FavoriteMangaItemListener {
        fun onFavoriteMangaClick(animeId: Int)
    }
}
