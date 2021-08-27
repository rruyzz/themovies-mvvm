package dominando.android.moviesdb.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import dominando.android.moviesdb.model.MovieDetailResponse
import dominando.android.moviesdb.model.MovieProviderResponse
import dominando.android.moviesdb.model.MovieResultResponse
import dominando.android.moviesdb.utils.Constanst.IMAGE_URL
import dominando.android.moviesdb.utils.extensions.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModel()
    private val args by navArgs<MovieDetailFragmentArgs>()
    private val movieId get() = args.movieId

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMovieDetail(movieId)
        setObservers()
    }

    private fun setObservers() {
        viewModel.movieDetailViewState.observe(requireActivity(), Observer {
            when (it) {
                is MovieDetails.Success -> renderSucces(it.movieDetail, it.providerResponse, it.similarMovies)
                is MovieDetails.Error -> renderError()
                is MovieDetails.Loading -> renderLoading(it.isLoading)
            }
        })
    }

    private fun renderSucces(
        movieDetail: MovieDetailResponse,
        providerResponse: MovieProviderResponse?,
        similarMovies: MovieResultResponse) = with(binding) {
            Glide.with(requireActivity()).load(IMAGE_URL+movieDetail.backdropPath).into(poster)
            textTitle.text = movieDetail.title
            textDuration.text = movieDetail.releaseDate
            rvProvider.adapter = ProviderAdapter(providerResponse?.results?.bR?.flatrate ?: listOf())
            rvProvider.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rating.rating = (movieDetail.voteAverage/2).toFloat()
            textResume.text = movieDetail.overview
            rvSimilarMovies.adapter = HomeAdapter(::onClick, similarMovies.results, requireContext())
            rvSimilarMovies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun renderError() {
        showToast(requireActivity(), "Error")
    }

    private fun renderLoading(isLoading: Boolean) = with(binding) {
        progress.isVisible = isLoading
    }
    private fun onClick(movieId: Int) {
        viewModel.getMovieDetail(movieId.toString())
    }
}