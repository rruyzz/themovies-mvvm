package dominando.android.moviesdb.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dominando.android.moviesdb.adapters.HomeAdapter
import dominando.android.moviesdb.adapters.ProviderAdapter
import dominando.android.moviesdb.databinding.FragmentMovieDetailBinding
import dominando.android.moviesdb.model.FlatrateItem
import dominando.android.moviesdb.model.MovieDetailResponse
import dominando.android.moviesdb.model.MovieItem
import dominando.android.moviesdb.utils.Constanst.IMAGE_URL
import dominando.android.moviesdb.utils.extensions.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(viewModel.itemPosition == 0 ) findNavController().popBackStack()
                else {
                    viewModel.itemPosition--
                    viewModel.listMovies.removeLast()
                    renderSucces(viewModel.listMovies)
                }
            }
        })
    }

    private fun setObservers() {
        viewModel.movieDetailViewState.observe(requireActivity(), Observer {
            when (it) {
                is MovieDetails.Success -> renderSucces(it.movie)
                is MovieDetails.Error -> renderError()
                is MovieDetails.Loading -> renderLoading(it.isLoading)
            }
        })
    }

    private fun renderSucces(movieList: List<MovieDetail>) = with(binding) {
            movieId = movieList[viewModel.itemPosition].detail.id.toString()
            renderDetail(movieList[viewModel.itemPosition].detail)
            renderProvider(movieList[viewModel.itemPosition].providers.results.bR?.flatrate ?: listOf())
            renderSimilar(movieList[viewModel.itemPosition].similar.results)
    }

    private fun renderDetail(movie: MovieDetailResponse)=with(binding){
        Glide.with(requireActivity()).load(IMAGE_URL+movie.backdropPath).into(poster)
        textTitle.text = movie.title
        textDuration.text = movie.releaseDate
        rating.rating = (movie.voteAverage/2).toFloat()
        textResume.text = movie.overview
    }

    private fun renderSimilar(movieList: List<MovieItem>) = with(binding){
        rvSimilarMovies.adapter = HomeAdapter(::onClick, movieList, requireContext())
        rvSimilarMovies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun renderProvider(providerList: List<FlatrateItem>) = with(binding){
        rvProvider.adapter = ProviderAdapter(providerList)
        rvProvider.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }
    private fun renderError() {
        showToast(requireActivity(), "Error")
    }

    private fun renderLoading(isLoading: Boolean) = with(binding) {
        progress.isVisible = isLoading
    }
    private fun onClick(id: Int) {
        movieId = id.toString()
        viewModel.itemPosition++
        viewModel.getMovieDetail(id.toString())
    }
}