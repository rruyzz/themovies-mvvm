package dominando.android.moviesdb.utils.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import bolts.AppLinkNavigation.navigate
import dominando.android.moviesdb.R
import dominando.android.moviesdb.adapters.SeasonAdapter
import dominando.android.moviesdb.adapters.SeasonSerieAdapter
import dominando.android.moviesdb.databinding.NewItemSerieEpisodeBinding
import dominando.android.moviesdb.model.EpisodesItem
import dominando.android.moviesdb.model.MovieProviderResponse
import dominando.android.moviesdb.model.Season
import dominando.android.moviesdb.serieDetail.SerieDetail
import dominando.android.moviesdb.serieDetail.SerieDetailFragmentDirections

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
    private val adapter = SeasonSerieAdapter(::onClick, ::onClickEpisode, listSerie, context)
    private lateinit var action: SerieDetailFragmentDirections.Companion
    private var providerResponse: MovieProviderResponse? = null
    private val navigate get() = findNavController()

    init {
        binding.recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recycler.adapter= adapter
    }

    fun setCustomView(
        listSerieDetail: SerieDetail?,
        actionNav: SerieDetailFragmentDirections.Companion,
        providers: MovieProviderResponse?
    ) = with(binding) {
        listSerie = parse(listSerieDetail)
        action = actionNav
        providerResponse = providers
        adapter.updateList(listSerie)
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

    private fun onClick(episode: Season?) {
        listSerie.map {
            if(it is SeasonSerieAdapter.ClassSerie.Episode){
                if(it.episode?.seasonNumber == episode?.seasonNumber) it.episode?.isVisible = it.episode?.isVisible?.not() ?: false
            }
        }
        adapter.updateList(listSerie)

    }

    private fun onClickEpisode(episode: EpisodesItem?){
        episode?.let{
            navigate.navigate(action.actionSerieDetailFragmentToEpisodeDetailFragment(it, providerResponse))
        }
    }

    private fun getAtrributes(attrs: AttributeSet?)= context.theme.obtainStyledAttributes(
        attrs, R.styleable.CustomSerieItem, 0, 0
    )//.apply {

    override fun setOnClickListener(l: OnClickListener?) {
//        binding.card.setOnClickListener(l)
    }
}
