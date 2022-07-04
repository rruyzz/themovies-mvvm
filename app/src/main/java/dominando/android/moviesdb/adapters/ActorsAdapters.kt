package dominando.android.moviesdb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dominando.android.moviesdb.databinding.ItemCastBinding
import dominando.android.moviesdb.databinding.ItemProviderBinding
import dominando.android.moviesdb.model.ActorsItem
import dominando.android.moviesdb.model.FlatrateItem
import dominando.android.moviesdb.utils.Constanst.IMAGE_URL

class ActorsAdapters(
    private val list: List<ActorsItem>,
    private val context: Context
) : RecyclerView.Adapter<ActorsAdapters.ActorsViewHolder>() {

    lateinit var binding: ItemCastBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActorsAdapters.ActorsViewHolder {
        binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorsViewHolder(binding.root)
    }
    override fun getItemCount() = list.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: ActorsAdapters.ActorsViewHolder, position: Int) {
        holder.bind(list[position])
    }


    inner class ActorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(actor: ActorsItem) = with(binding) {
            Glide.with(context).load(IMAGE_URL+actor.profilePath).into(imagePoster)
            textCastName.text = actor.originalName
            textPersonageName.text = actor.character
        }
    }
}