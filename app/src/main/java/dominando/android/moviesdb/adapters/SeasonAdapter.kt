package dominando.android.moviesdb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import dominando.android.moviesdb.databinding.ItemSeasonBinding
import dominando.android.moviesdb.model.EpisodesItem
import dominando.android.moviesdb.model.Season
import dominando.android.moviesdb.utils.customView.CustomSerieItem

class SeasonAdapter(
    var onClick: (EpisodesItem) -> Unit,
    private val list: List<Season?>,
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

    inner class SeasonsViewHolder(private val binding: ItemSeasonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(seasons: Season?) = with(binding) {
            seasonTitle.text = "${seasons?.name}"
            seasons?.episodes?.forEach {
                linear.addView(parseToSerieItem(it))
            }
        }

        private fun parseToSerieItem(episode: EpisodesItem): CustomSerieItem {
            val item = CustomSerieItem(context)
            item.setEpisode(episode)
            item.setTitle(episode.name)
            item.setEpisode(episode.seasonNumber, episode.episodeNumber)
            item.setImage(episode.stillPath)
            item.setOnClickListener { onClick(item.getShowId()) }
            return item
        }

        init {
            binding.seasonTitle.setOnClickListener {
                val position = adapterPosition
                if (RecyclerView.NO_POSITION != position) {
                    list[position]?.showEpisodes = !list[position]?.showEpisodes!!
                    binding.linear.isVisible = list[position]?.showEpisodes ?: false
                }
            }
        }
    }
}