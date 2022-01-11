package dominando.android.moviesdb.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dominando.android.moviesdb.R
import dominando.android.moviesdb.adapters.HomeAdapter
import dominando.android.moviesdb.adapters.ProviderAdapter
import dominando.android.moviesdb.databinding.FragmentMovieDetailBinding
import dominando.android.moviesdb.model.FlatrateItem
import dominando.android.moviesdb.model.MovieDetailResponse
import dominando.android.moviesdb.model.MovieItem
import dominando.android.moviesdb.utils.Constanst.IMAGE_URL
import dominando.android.moviesdb.utils.extensions.formattedAsHour
import dominando.android.moviesdb.utils.extensions.formtattedAsDate
import dominando.android.moviesdb.utils.extensions.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModel()
    private val args by navArgs<MovieDetailFragmentArgs>()
    private val firstMovieId get() = args.movieId
    private var movieId = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMovieDetail(firstMovieId)
        setObservers()
        setViews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(viewModel.itemPosition == 0 ) findNavController().popBackStack()
                else renderBackPressed()
            }
        })
    }

    private fun setViews() = with(binding){
        icBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.movieDetailState.collect {
                when (it) {
                    is MovieDetails.Success -> renderSucces(it.movie)
                    is MovieDetails.Error -> renderError()
                    is MovieDetails.Loading -> renderLoading(it.isLoading)
                }
            }
        }
    }

    private fun renderLoading(isLoading: Boolean) = with(binding){
        progress.isVisible = isLoading
        scroll.isVisible = isLoading.not()
    }
    private fun renderSucces(movieList: List<MovieDetail>) = with(binding) {
        scroll.isVisible = true
        movieId = movieList[viewModel.itemPosition].detail?.id.toString()
        movieList[viewModel.itemPosition].detail?.let { renderDetail(it) }
        renderProvider(movieList[viewModel.itemPosition].providers?.results?.bR?.flatrate ?: listOf())
        movieList[viewModel.itemPosition].similar?.results?.let { renderSimilar(it) }
        if(movieId != firstMovieId) animationIn()
    }

    private fun renderDetail(movie: MovieDetailResponse)=with(binding){
        Glide.with(requireActivity()).load(IMAGE_URL+movie.backdropPath).into(poster)
        textTitle.text = movie.title
        textDate.text = movie.releaseDate.toString().formtattedAsDate()
        textDuration.text = movie.runtime.toString().formattedAsHour()
        textGenero.text = movie.genres[0].name
        rating.rating = (movie.voteAverage/2).toFloat()
        textResume.text = movie.overview
    }

    private fun renderBackPressed(){
        viewModel.itemPosition--
        viewModel.listMovies.removeLast()
        renderSucces(viewModel.listMovies)
        animationIn()
    }

    private fun renderProvider(providerList: List<FlatrateItem>) = with(binding){
        textWhereWatch.isVisible = providerList.isNotEmpty()
        view.isVisible = providerList.isNotEmpty()
        rvProvider.adapter = ProviderAdapter(providerList)
        rvProvider.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun renderSimilar(movieList: List<MovieItem>) = with(binding){
        rvSimilarMovies.adapter = HomeAdapter(::onClick, movieList, requireContext())
        rvSimilarMovies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun renderError(){
        viewModel.itemPosition--
        showToast(requireActivity(), "Error")
    }
    private fun animationIn() = with(binding){
        val animation= AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        layout.startAnimation(animation)
    }

    private fun onClick(id: Int, isShow: Boolean) {
        movieId = id.toString()
        viewModel.itemPosition++
        viewModel.getMovieDetail(id.toString())
    }
}