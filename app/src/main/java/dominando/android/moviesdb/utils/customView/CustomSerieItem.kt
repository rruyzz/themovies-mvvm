package dominando.android.moviesdb.utils.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import dominando.android.moviesdb.R
import dominando.android.moviesdb.adapters.SeasonAdapter
import dominando.android.moviesdb.adapters.SeasonSerieAdapter
import dominando.android.moviesdb.databinding.NewItemSerieEpisodeBinding
import dominando.android.moviesdb.model.EpisodesItem
import dominando.android.moviesdb.serieDetail.SerieDetail

class CustomSerieItem  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAtr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAtr) {

    private var binding: NewItemSerieEpisodeBinding = NewItemSerieEpisodeBinding
        .inflate(LayoutInflater.from(context), this, true)
    private var episode = 0
    private var season = 0
    private var title = ""
    private var image = ""
    private var episodeItem : SerieDetail? = null
    private var listSerie : List<SeasonSerieAdapter.ClassSerie?> = listOf()
    private val adapter = SeasonSerieAdapter(::onClick, listSerie, context)

    init {
        getAtrributes(attrs)
        binding.recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recycler.adapter= SeasonSerieAdapter(::onClick, listSerie, context)
//        binding.recycler.adapter= adapter
//        setTitle(title)
//        setEpisode(season, episode)
//        setImage(image)
    }

    fun setCustomView(listSerieDetail: SerieDetail?) = with(binding) {
        listSerie = parse(listSerieDetail)
        binding.recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recycler.adapter= SeasonSerieAdapter(::onClick, listSerie, context)
    }

    
    private fun parse(serieDetail: SerieDetail?) : List<SeasonSerieAdapter.ClassSerie?> {
        var listaClass = mutableListOf<SeasonSerieAdapter.ClassSerie?>()
        serieDetail?.detail?.seasonList?.filterNotNull()?.forEach { season ->
            listaClass.add(SeasonSerieAdapter.ClassSerie.SeasonClass(season))
            season.episodes.forEach{ episode ->
                listaClass.add(SeasonSerieAdapter.ClassSerie.Episode(episode))
            }
        }
        return listaClass
    }

    private fun onClick(episode: EpisodesItem) {
//        val action = SerieDetailFragmentDirections.actionSerieDetailFragmentToEpisodeDetailFragment(episode, serieDetail.providers)
//        navigation.navigate(action)
    }

    private fun getAtrributes(attrs: AttributeSet?)= context.theme.obtainStyledAttributes(
        attrs, R.styleable.CustomSerieItem, 0, 0
    )//.apply {


//    fun getShowId() = episodeItem
//
//
//    fun setEpisode(episode: EpisodesItem){
//        episodeItem = episode
//    }
//    fun setTitle(title: String){
//        binding.textTitle.text = title
//    }
//    fun setEpisode(season: Int, episode: Int){
//        binding.textEpisode.text = "S$season | E$episode"
//    }
//    fun setImage(image: String?){
//        Glide.with(context).load(Constanst.IMAGE_URL +image).into(binding.episodeImage)
//    }

    override fun setOnClickListener(l: OnClickListener?) {
//        binding.card.setOnClickListener(l)
    }
}

//class CustomSerieItem  @JvmOverloads constructor(
//    context: Context,
//    defStyle: Int = 0,
//    attrs: AttributeSet? = null,
//) : ConstraintLayout(context, attrs, defStyle) {
//
//    private lateinit var binding: ItemSerieEpisodeBinding
//    private var episode = 0
//    private var season = 0
//    private var title = ""
//    private var image = ""
//    private lateinit var episodeItem : EpisodesItem
//
//    init {
//        binding = ItemSerieEpisodeBinding.inflate(LayoutInflater.from(context), this, true)
//        setTitle(title)
//        setEpisode(season, episode)
//        setImage(image)
//    }
//    fun getShowId() = episodeItem
//
//    fun setEpisode(episode: EpisodesItem){
//        episodeItem = episode
//    }
//    fun setTitle(title: String){
//        binding.textTitle.text = title
//    }
//    fun setEpisode(season: Int, episode: Int){
//        binding.textEpisode.text = "S$season | E$episode"
//    }
//    fun setImage(image: String?){
//        Glide.with(context).load(Constanst.IMAGE_URL +image).into(binding.episodeImage)
//    }
//
//    override fun setOnClickListener(l: OnClickListener?) {
//        binding.card.setOnClickListener(l)
//    }
//}