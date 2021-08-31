package dominando.android.moviesdb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dominando.android.moviesdb.databinding.ItemMovieBinding

class HomeAdapter(
    var onClick: (Int, Boolean) -> Unit,
    private val list: List<MovieSerieItem>,
    private val context: Context
) : RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {

    lateinit var binding: ItemMovieBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding.root)
    }

    override fun getItemCount() = list.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(movie: MovieSerieItem) = with(binding) {
            textTitle.text = movie.title
            textGrade.text = movie.grade
            Glide.with(context).load(movie.poster).into(imagePoster)
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

interface MovieSerieItem {
    val title: String
    val grade: String
    val poster: String
    val movie_id: Int
    val isShow: Boolean
}