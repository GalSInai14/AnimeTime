package com.example.animetime.ui.all_mangas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.widget.Filter
import android.widget.Filterable
import com.example.animetime.data.models.anime.Anime
import com.example.animetime.data.models.manga.Manga
import com.example.animetime.databinding.ItemMangaBinding

class MangaAdapter(private val listener: MangaItemListener) :
    RecyclerView.Adapter<MangaAdapter.MangaViewHolder>(), Filterable {

    private val mangas = ArrayList<Manga>()
    private var filteredMangas = ArrayList<Manga>()

    class MangaViewHolder(
        private val itemBinding: ItemMangaBinding,
        private val listener: MangaItemListener
    ) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var manga: Manga

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: Manga) {
            this.manga = item
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
            listener.onMangaClick(manga.mal_id)
        }
    }

    fun setMangas(mangas: Collection<Manga>) {
        this.mangas.clear()
        this.mangas.addAll(mangas.filter { item ->
            item.title_english != null &&
                    item.synopsis != null &&
                    item.images?.jpg?.image_url != null
        }.filter { item ->
            !item.title_english.isNullOrEmpty() &&
                    !item.synopsis.isNullOrEmpty() &&
                    item.images?.jpg?.image_url != null
        })
        this.filteredMangas.clear()
        this.filteredMangas.addAll(mangas.filter { item ->
            !item.title_english.isNullOrEmpty() &&
                    !item.synopsis.isNullOrEmpty() &&
                    item.images?.jpg?.image_url != null
        })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val binding = ItemMangaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MangaViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MangaAdapter.MangaViewHolder, position: Int) {
        if (position < filteredMangas.size) {
            holder.bind(filteredMangas[position])
        } else {
            holder.bind(mangas[position])
        }
    }

    override fun getItemCount() = mangas.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterPattern = constraint.toString()
                if (filterPattern.isEmpty()) {
                    filteredMangas = ArrayList(mangas)
                } else {
                    val filterResultList = ArrayList<Manga>()
                    for (manga in mangas) {
                        if (manga.title_english?.lowercase()?.contains(filterPattern) == true
                        ) {
                            filterResultList.add(manga)
                        }
                    }
                    filteredMangas = filterResultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredMangas
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredMangas = results?.values as ArrayList<Manga>
                notifyDataSetChanged()
            }
        }
    }

    interface MangaItemListener {
        fun onMangaClick(mangaId: Int)
    }
}





