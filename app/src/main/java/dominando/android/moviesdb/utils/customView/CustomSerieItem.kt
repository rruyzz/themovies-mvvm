package dominando.android.moviesdb.utils.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import dominando.android.moviesdb.databinding.ItemSerieEpisodeBinding
import dominando.android.moviesdb.model.EpisodesItem
import dominando.android.moviesdb.utils.Constanst

class CustomSerieItem  @JvmOverloads constructor(
    context: Context,
    defStyle: Int = 0,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs, defStyle) {

    private lateinit var binding: ItemSerieEpisodeBinding
    private var episode = 0
    private var season = 0
    private var title = ""
    private var image = ""
    private lateinit var episodeItem : EpisodesItem

    init {
        binding = ItemSerieEpisodeBinding.inflate(LayoutInflater.from(context), this, true)
        setTitle(title)
        setEpisode(season, episode)
        setImage(image)
    }
    fun getShowId() = episodeItem

    fun setEpisode(episode: EpisodesItem){
        episodeItem = episode
    }
    fun setTitle(title: String){
        binding.textTitle.text = title
    }
    fun setEpisode(season: Int, episode: Int){
        binding.textEpisode.text = "S$season | E$episode"
    }
    fun setImage(image: String?){
        Glide.with(context).load(Constanst.IMAGE_URL +image).into(binding.episodeImage)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        binding.card.setOnClickListener(l)
    }
}