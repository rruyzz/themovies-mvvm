package dominando.android.moviesdb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dominando.android.moviesdb.databinding.ItemMovieBinding
import dominando.android.moviesdb.databinding.ItemProviderBinding
import dominando.android.moviesdb.model.FlatrateItem

class ProviderAdapter(
    private val list: List<FlatrateItem>
) : RecyclerView.Adapter<ProviderAdapter.ProvidersViewHolder>() {

    lateinit var binding: ItemProviderBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProviderAdapter.ProvidersViewHolder {
        binding = ItemProviderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProvidersViewHolder(binding.root)
    }

    override fun getItemCount() = list.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: ProvidersViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ProvidersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(movie: FlatrateItem) = with(binding) {
            title.text = movie.providerName
        }
    }
}