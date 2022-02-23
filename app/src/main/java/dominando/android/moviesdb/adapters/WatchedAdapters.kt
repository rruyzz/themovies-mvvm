package dominando.android.moviesdb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dominando.android.moviesdb.databinding.ItemCastBinding
import dominando.android.moviesdb.model.MovieFirebase
import dominando.android.moviesdb.utils.Constanst

class WatchedAdapter {
}

class WatchedAdapters(
    private val list: List<MovieFirebase>,
    private val context: Context
) : RecyclerView.Adapter<WatchedAdapters.WatchedViewHolder>() {

    lateinit var binding: ItemCastBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WatchedAdapters.WatchedViewHolder {
        binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WatchedViewHolder(binding.root)
    }
    override fun getItemCount() = list.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: WatchedAdapters.WatchedViewHolder, position: Int) {
        holder.bind(list[position])
    }


    inner class WatchedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(actor: MovieFirebase) = with(binding) {
            Glide.with(context).load(Constanst.IMAGE_URL +actor.poster).into(imagePoster)
            textCastName.text = actor.name
            textPersonageName.text = ""
        }
    }
}