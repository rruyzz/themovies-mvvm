package dominando.android.moviesdb.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dominando.android.moviesdb.databinding.ItemMovieBinding
import dominando.android.moviesdb.model.DiscoveryMovieItem
import dominando.android.moviesdb.utils.Constanst

class HomeAdapter(
    var listener: onClick,
    private val list: List<MovieSerieItem>,
    private val context: Context
) : RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {

    lateinit var binding: ItemMovieBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding.root)
    }

    interface onClick{
        fun onClick()
    }

    override fun getItemCount() = list.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        fun bind(movie: MovieSerieItem) = with(binding){
            textTitle.text = movie.title
            textGrade.text = movie.grade
            Glide.with(context).load(movie.poster).into(imagePoster)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(RecyclerView.NO_POSITION != position)
                listener.onClick()
        }
    }
}

interface MovieSerieItem {
    val title: String
    val grade: String
    val poster: String
}