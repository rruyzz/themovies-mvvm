package dominando.android.moviesdb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dominando.android.moviesdb.databinding.ItemSeasonBinding
import dominando.android.moviesdb.databinding.ItemSerieEpisodeBinding
import dominando.android.moviesdb.model.EpisodesItem
import dominando.android.moviesdb.model.Season
import dominando.android.moviesdb.serieDetail.serieSeasons.SerieSeasonsFragment
import dominando.android.moviesdb.serieDetail.serieSeasons.SerieSeasonsFragment.ClassSerie
import dominando.android.moviesdb.utils.Constanst
import dominando.android.moviesdb.utils.customView.CustomSerieItem

class SeasonAdapter(
    var onClick: (EpisodesItem) -> Unit,
    private var list: List<ClassSerie?>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): SeasonAdapter.SeasonsViewHolder {
//        val binding = ItemSeasonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return SeasonsViewHolder(binding)
//    }

    fun updateList(updateList : List<ClassSerie?>){
        list = updateList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = when (viewType) {
        0 -> ItemSeasonHolder(
            ItemSeasonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        1 -> SerieSeasonHolder(
            ItemSerieEpisodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), context
        )
        else -> throw IllegalArgumentException("Unsupported type")

    }

    class ItemSeasonHolder(val binding: ItemSeasonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(season: ClassSerie?) {
            val seasonItem = (season as ClassSerie.SeasonClass).season
            binding.seasonTitle.text = seasonItem?.name
            binding.seasonTitle.setOnClickListener {
                val position = adapterPosition
                if (RecyclerView.NO_POSITION != position) {
                    seasonItem?.showEpisodes = !seasonItem?.showEpisodes!!
                    binding.linear.isVisible = seasonItem.showEpisodes ?: false
                }
            }
        }
    }

    class SerieSeasonHolder(val binding: ItemSerieEpisodeBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: ClassSerie?) {
            val season = (episode as ClassSerie.Episode).episode
            binding.textEpisode.text = "S${season?.seasonNumber.toString()} | E${season?.episodeNumber.toString()}"
            binding.textTitle.text = season?.name
            Glide.with(context).load(Constanst.IMAGE_URL + season?.stillPath)
                .into(binding.episodeImage)
            binding.card.visibility = View.VISIBLE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemSeasonHolder -> {
                holder.bind(list[position])
            }
            is SerieSeasonHolder -> {
                holder.bind(list[position])
            }
        }
    }

    override fun getItemCount() = list.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = when {
        list[position] is ClassSerie.SeasonClass -> 0
        list[position] is ClassSerie.Episode -> 1
        else -> 2
    }

    inner class SeasonsViewHolder(private val binding: ItemSeasonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(seasons: Season?) = with(binding) {
//            seasonTitle.text = "${seasons?.name}"
//            seasons?.episodes?.forEach {
//                linear.addView(parseToSerieItem(it))
//            }
//            seasonTitle.setOnClickListener {
//                val position = adapterPosition
//                if (RecyclerView.NO_POSITION != position) {
//                    list[position]?.showEpisodes = !list[position]?.showEpisodes!!
//                    binding.linear.isVisible = list[position]?.showEpisodes ?: false
//                    linear.removeAllViews()
//                }
//            }
        }

        private fun parseToSerieItem(episode: EpisodesItem): CustomSerieItem {
            val item = CustomSerieItem(context)
//            item.setEpisode(episode)
//            item.setTitle(episode.name)
//            item.setEpisode(episode.seasonNumber, episode.episodeNumber)
//            item.setImage(episode.stillPath)
//            item.setOnClickListener { onClick(item.getShowId()) }
            return item
        }

//        init {
//            binding.seasonTitle.setOnClickListener {
//                val position = adapterPosition
//                if (RecyclerView.NO_POSITION != position) {
//                    list[position]?.showEpisodes = !list[position]?.showEpisodes!!
//                    binding.linear.isVisible = list[position]?.showEpisodes ?: false
//                }
//            }
//        }
    }


//    sealed class ClassSerie {
//        class SeasonClass(val season: Season?) : ClassSerie()
//        class Episode(val episode: EpisodesItem?) : ClassSerie()
//    }
}

