package dominando.android.moviesdb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dominando.android.moviesdb.databinding.ItemSeasonBinding
import dominando.android.moviesdb.databinding.ItemSerieEpisodeBinding
import dominando.android.moviesdb.model.EpisodesItem
import dominando.android.moviesdb.model.Season
import dominando.android.moviesdb.utils.Constanst

class SeasonSerieAdapter(
    var onClick: (Season?) -> Unit,
    var onClickEpisode: (EpisodesItem?) -> Unit,
    private var list: List<ClassSerie?>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun updateList(updateList : List<ClassSerie?>){
        list = updateList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = when (viewType) {
        0 -> ItemSeasonHolder(ItemSeasonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        1 -> SerieSeasonHolder(ItemSerieEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false), parent.context)
        else -> throw IllegalArgumentException("Unsupported type")

    }

    inner class ItemSeasonHolder(val binding: ItemSeasonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(season: ClassSerie?) {
            val seasonItem = (season as ClassSerie.SeasonClass).season
            binding.seasonTitle.text = seasonItem?.name
            binding.seasonTitle.setOnClickListener {
                val position = adapterPosition
                if (RecyclerView.NO_POSITION != position) {
                    onClick(seasonItem)
                }
            }
        }
    }

    inner class SerieSeasonHolder(val binding: ItemSerieEpisodeBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: ClassSerie?) {
            val season = (episode as ClassSerie.Episode).episode
            if(season?.isVisible == true) {
                itemView.visibility = View.VISIBLE
                itemView.setLayoutParams(RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            } else {
                itemView.visibility = View.GONE
                itemView.setLayoutParams(RecyclerView.LayoutParams(0, 0))
            }
            binding.textEpisode.text = "S${season?.seasonNumber.toString()} | E${season?.episodeNumber.toString()}"
            binding.textTitle.text = season?.name
            Glide.with(context).load(Constanst.IMAGE_URL + season?.stillPath)
                .into(binding.episodeImage)
            itemView.setOnClickListener {
                val position = adapterPosition
                if (RecyclerView.NO_POSITION != position) {
                    onClickEpisode(season)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemSeasonHolder -> { holder.bind(list[position]) }
            is SerieSeasonHolder -> { holder.bind(list[position]) }
        }
    }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int) = when {
        list[position] is ClassSerie.SeasonClass -> 0
        list[position] is ClassSerie.Episode -> 1
        else -> 2
    }

    sealed class ClassSerie {
        class SeasonClass(val season: Season?) : ClassSerie()
        class Episode(val episode: EpisodesItem?) : ClassSerie()
    }
}

