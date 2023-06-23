package com.example.animetime.ui.all_favorite_mangas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animetime.data.models.favorite_manga.FavoriteManga
import com.example.animetime.databinding.ItemMangaBinding

class FavoriteMangaAdapter(private val listener: FavoriteMangaItemListener) :
    RecyclerView.Adapter<FavoriteMangaAdapter.FavoriteMangaViewHolder>() {

    private val favoriteMangas = ArrayList<FavoriteManga>()

    class FavoriteMangaViewHolder(
        private val itemBinding: ItemMangaBinding,
        private val listener: FavoriteMangaItemListener
    ) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var favoriteManga: FavoriteManga

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(
            item: FavoriteManga
        ) {
            this.favoriteManga = item
            val synopsis = item.synopsis
            val maxLength = 50

            itemBinding.mangaCardTitle.text = item.title_english
            if (synopsis != null) {
                if (synopsis.length > maxLength) {
                    val trimmedSynopsis = synopsis.substring(0, maxLength) + "..."
                    itemBinding.mangaCardDesc.text = trimmedSynopsis
                } else {
                    itemBinding.mangaCardDesc.text = synopsis
                }
            } else {
                itemBinding.mangaCardDesc.text = ""
            }

            Glide.with(itemBinding.root)
                .load(item.images?.jpg?.image_url)
                .into(itemBinding.mangaCardImage)
        }

        override fun onClick(v: View?) {
            listener.onFavoriteMangaClick(favoriteManga.mal_id)
        }
    }

    fun favoriteAt(position: Int) = favoriteMangas[position]


    fun setFavoriteMangas(favoriteMangas: Collection<FavoriteManga>) {
        this.favoriteMangas.clear()
        this.favoriteMangas.addAll(favoriteMangas.filter { item ->
            item.title_english != null &&
                    item.synopsis != null &&
                    item.images?.jpg?.image_url != null
        })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMangaViewHolder {
        val binding = ItemMangaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteMangaViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: FavoriteMangaViewHolder, position: Int) {
        val item = favoriteMangas[position]
        holder.bind(item)
    }

    override fun getItemCount() = favoriteMangas.size

    interface FavoriteMangaItemListener {
        fun onFavoriteMangaClick(favoriteAnimeId: Int)
    }
}

