package com.example.animetime.ui.all_animes


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.Filter
import android.widget.Filterable
import com.bumptech.glide.Glide
import com.example.animetime.data.models.anime.Anime
import com.example.animetime.databinding.ItemAnimeBinding

class AnimeAdapter(private val listener: AnimeItemListener) :
    RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>(), Filterable {

    private val animes = ArrayList<Anime>()
    private var filteredAnimes = ArrayList<Anime>()

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
        }.filter { item ->
            // Exclude anime with null attributes
            !item.title_english.isNullOrEmpty() &&
                    !item.synopsis.isNullOrEmpty() &&
                    item.images?.jpg?.image_url != null
        })
        this.filteredAnimes.clear()
        this.filteredAnimes.addAll(animes.filter { item ->
            // Exclude anime with null attributes
            !item.title_english.isNullOrEmpty() &&
                    !item.synopsis.isNullOrEmpty() &&
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
        if (position < filteredAnimes.size) {
            holder.bind(filteredAnimes[position])
        } else {
            holder.bind(animes[position])
        }
    }

    override fun getItemCount() = filteredAnimes.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterPattern = constraint.toString()
                if (filterPattern.isEmpty()) {
                    filteredAnimes = ArrayList(animes)
                } else {
                    val filterResultList = ArrayList<Anime>()
                    for (anime in animes) {
                        if (anime.title_english?.lowercase()?.contains(filterPattern) == true
                        ) {
                            filterResultList.add(anime)
                        }
                    }
                    filteredAnimes = filterResultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredAnimes
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredAnimes = results?.values as ArrayList<Anime>
                notifyDataSetChanged()
            }
        }
    }

    interface AnimeItemListener {
        fun onAnimeClick(animeId: Int)
    }
}
