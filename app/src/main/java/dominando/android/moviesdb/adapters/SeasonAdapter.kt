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
            val lista = mutableListOf<String>()
            for(i in 0..seasons.episodeCount){
                lista.add((i + 1).toString())
            }
            spinner.adapter = ArrayAdapter(context, R.layout.layout_spinner,R.id.textSeason,lista.toTypedArray() )
        }
    }
}