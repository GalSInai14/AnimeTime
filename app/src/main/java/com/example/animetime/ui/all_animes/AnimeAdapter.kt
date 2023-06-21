package com.example.animetime.ui.all_animes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animetime.data.models.anime.Anime
import com.example.animetime.databinding.ItemAnimeBinding

class AnimeAdapter(private val listener: AnimeItemListener) :
    RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    private val animes = ArrayList<Anime>()

    class AnimeViewHolder(
        private val itemBinding: ItemAnimeBinding,
        private val listener: AnimeItemListener
    ) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var anime: Anime

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: Anime) {
            this.anime = item
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
            listener.onAnimeClick(anime.mal_id)
        }
    }

    fun setAnimes(animes: Collection<Anime>) {
        this.animes.clear()
        this.animes.addAll(animes.filter { item ->
            item.title_english != null &&
                    item.synopsis != null &&
                    item.images?.jpg?.image_url != null
        })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding = ItemAnimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AnimeViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val item = animes[position]
        holder.bind(item)
    }

    override fun getItemCount() = animes.size

    interface AnimeItemListener {
        fun onAnimeClick(animeId: Int)
    }
}





