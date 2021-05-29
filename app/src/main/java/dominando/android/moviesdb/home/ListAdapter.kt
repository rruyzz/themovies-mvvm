package dominando.android.moviesdb.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import dominando.android.moviesdb.R
import dominando.android.moviesdb.home.ListAdapter.ListViewHolder
import dominando.android.moviesdb.model.DiscoveryListMovieItem
import kotlinx.android.synthetic.main.item_movie.view.*

class ListAdapter(
    var listener: onClick,
    private val list: List<DiscoveryListMovieItem>,
    private val context: Context
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        )
    }

    interface onClick{
        fun onClick()
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.title.text = list[position].title
        holder.grade.text = list[position].voteAverage.toString()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val title = itemView.text_title
        val grade = itemView.text_grade

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(RecyclerView.NO_POSITION != position)
                listener.onClick()
        }
    }

}