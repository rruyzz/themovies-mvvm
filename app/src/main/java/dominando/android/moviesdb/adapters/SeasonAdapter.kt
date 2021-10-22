package dominando.android.moviesdb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import dominando.android.moviesdb.databinding.ItemSeasonBinding
import dominando.android.moviesdb.model.SeasonsItem
import dominando.android.moviesdb.utils.customView.CustomSerieItem

class SeasonAdapter(
    private val list: List<SeasonsItem>,
    private val context: Context
) : RecyclerView.Adapter<SeasonAdapter.SeasonsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeasonAdapter.SeasonsViewHolder {
        val binding = ItemSeasonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeasonsViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: SeasonsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class SeasonsViewHolder(private val binding: ItemSeasonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(seasons: SeasonsItem) = with(binding) {
            seasonTitle.text = "${seasons.name}"
            for (i in 0..seasons.episodeCount) {
                linear.addView(parseToSerieItem(seasons, i+1))
            }
        }

        private fun parseToSerieItem(seaason: SeasonsItem, episode: Int): CustomSerieItem {
            val item = CustomSerieItem(context, null, 0)
            item.setTitle(seaason.name)
            item.setEpisode(seaason.seasonNumber, episode)
            item.setImage(seaason.posterPath)
            return item
        }

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (RecyclerView.NO_POSITION != position) {
                    list[position].showEpisodes = !list[position].showEpisodes
                    binding.linear.isVisible = list[position].showEpisodes
                    notifyDataSetChanged()
                }
            }
        }
    }
}