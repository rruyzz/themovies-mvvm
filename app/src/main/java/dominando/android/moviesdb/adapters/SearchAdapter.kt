package dominando.android.moviesdb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dominando.android.moviesdb.R
import dominando.android.moviesdb.databinding.ItemSearchBinding

class SearchAdapter(
    var onClick: (Int, Boolean) -> Unit,
    private val list: List<MovieSerieItem>,
    private val context: Context
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    lateinit var binding: ItemSearchBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.SearchViewHolder {
        binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(movie: MovieSerieItem) = with(binding) {
            textName.text = movie.title
            Glide.with(context).load(movie.backPoster)
                .placeholder(R.color.gray_brand_light).into(imagePoster)
        }

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (RecyclerView.NO_POSITION != position)
                    onClick(list[position].movie_id, list[position].isShow)
            }
        }
    }
}