package com.example.animetime.ui.all_favorite_animes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animetime.data.models.favorite_anime.FavoriteAnime
import com.example.animetime.databinding.ItemAnimeBinding

class FavoriteAnimeAdapter(private val listener: FavoriteAnimeItemListener) :
    RecyclerView.Adapter<FavoriteAnimeAdapter.FavoriteAnimeViewHolder>() {

    private val favoriteAnimes = ArrayList<FavoriteAnime>()

    class FavoriteAnimeViewHolder(
        private val itemBinding: ItemAnimeBinding,
        private val listener: FavoriteAnimeItemListener
    ) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var favoriteAnime: FavoriteAnime

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(
            item: FavoriteAnime
        ) {
            this.favoriteAnime = item
            val synopsis = item.synopsis
            val maxLength = 50

            itemBinding.animecardTitle.text = item.title_english
            if (synopsis != null) {
                if (synopsis.length > maxLength) {
                    val trimmedSynopsis = synopsis.substring(0, maxLength) + "..."
                    itemBinding.animecardDesc.text = trimmedSynopsis
                } else {
                    itemBinding.animecardDesc.text = synopsis
                }
            } else {
                itemBinding.animecardDesc.text = ""
            }

            Glide.with(itemBinding.root)
                .load(item.images?.jpg?.image_url)
                .into(itemBinding.animecardImage)
        }

        override fun onClick(v: View?) {
            listener.onFavoriteAnimeClick(favoriteAnime.mal_id)
        }
    }

    fun favoriteAt(position: Int) = favoriteAnimes[position]


    fun setFavoriteAnimes(favoriteAnimes: Collection<FavoriteAnime>) {
        this.favoriteAnimes.clear()
        this.favoriteAnimes.addAll(favoriteAnimes.filter { item ->
            item.title_english != null &&
                    item.synopsis != null &&
                    item.images?.jpg?.image_url != null
        })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAnimeViewHolder {
        val binding = ItemAnimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteAnimeViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: FavoriteAnimeViewHolder, position: Int) {
        val item = favoriteAnimes[position]
        holder.bind(item)
    }

    override fun getItemCount() = favoriteAnimes.size

    interface FavoriteAnimeItemListener {
        fun onFavoriteAnimeClick(favoriteAnimeId: Int)
    }
}

