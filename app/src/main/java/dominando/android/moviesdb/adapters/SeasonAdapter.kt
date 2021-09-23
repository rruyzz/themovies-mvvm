package dominando.android.moviesdb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import dominando.android.moviesdb.R
import dominando.android.moviesdb.databinding.ItemSeasonBinding
import dominando.android.moviesdb.model.SeasonsItem
import dominando.android.moviesdb.utils.customView.CustomSerieItem

class SeasonAdapter(
    private val list: List<SeasonsItem>,
    private val context: Context
)  : RecyclerView.Adapter<SeasonAdapter.SeasonsViewHolder>() {

    lateinit var binding: ItemSeasonBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeasonAdapter.SeasonsViewHolder {
        binding = ItemSeasonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeasonsViewHolder(binding.root)
    }

    override fun getItemCount() = list.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: SeasonsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class SeasonsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(seasons: SeasonsItem) = with(binding) {
            seasonTitle.text = "Temporada ${seasons.seasonNumber}"
            for(i in 0..seasons.episodeCount){
                linear.addView(parseToSerieItem(seasons, seasons.seasonNumber))
            }
        }
        private fun parseToSerieItem(seaason: SeasonsItem, episode: Int): CustomSerieItem{
            val item = CustomSerieItem(context, null, 0)
            item.setTitle(seaason.name)
            item.setEpisode(seaason.seasonNumber, episode )
            item.setImage(seaason.posterPath)
            return item
        }
    }
}