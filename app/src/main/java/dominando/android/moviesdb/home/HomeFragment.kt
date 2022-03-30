package dominando.android.moviesdb.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import dominando.android.moviesdb.adapters.HomeAdapter
import dominando.android.moviesdb.adapters.MovieSerieItem
import dominando.android.moviesdb.databinding.FragmentListBinding
import dominando.android.moviesdb.model.MovieResultResponse
import dominando.android.moviesdb.model.SeriesResultsResponse
import dominando.android.moviesdb.splash.SplashActivity
import dominando.android.moviesdb.utils.extensions.disableTouch
import dominando.android.moviesdb.utils.extensions.showToast
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentListBinding
    private val navigation get() = findNavController()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtons()
        setObservers()
        viewModel.getAllMovies()
        textSeries.setOnClickListener{
                viewModel.getAllMovies()
            }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.movieState.collect { state ->
                when (state) {
                    is HomeMovieList.Success -> setRecycler(state.listSerie, state.listMovie, state.listTopSerie)
                    is HomeMovieList.Error -> renderError()
                    is HomeMovieList.Loading -> renderLoading(state.isLoading)
                }
            }
        }
    }

    private fun setRecycler(
        listSerie: SeriesResultsResponse,
        listMovie: MovieResultResponse,
        topSeries: MovieResultResponse
    ) = with(binding) {
        rvSeries.apply {
            adapter = homeAdapter(listSerie.results)
            layoutManager = linearLayoutManeger()
        }
        rvMovies.apply {
            adapter =  homeAdapter(listMovie.results)
            layoutManager = linearLayoutManeger()
        }
        rvTopSeries.apply{
            adapter = homeAdapter(topSeries.results)
            layoutManager = linearLayoutManeger()
        }

    }
    private fun renderError() {
        showToast(requireActivity(), "Error")
        binding.group.isVisible = false
    }

    private fun renderLoading(isLoading: Boolean) = with(binding) {
        progress.isVisible = isLoading
        group.isVisible = isLoading.not()
        disableTouch(isLoading)
    }

    private fun setButtons() = with(binding) {
        textSeries.setOnClickListener {
            logOut()
        }
    }

    private fun logOut() {
        mAuth = FirebaseAuth.getInstance()
        mAuth.signOut()
        val intent = Intent(requireContext(), SplashActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun onClick(movie: MovieSerieItem) {
        val destination =
            if (movie.isShow) HomeFragmentDirections.actionListFragmentToSerieDetailFragment(movie.movie_id.toString())
            else HomeFragmentDirections.actionListFragmentToMovieDetailFragment(movie.movie_id.toString(), movie.title)
        navigation.navigate(destination)
    }

    private fun linearLayoutManeger() = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    private fun homeAdapter(list: List<MovieSerieItem>) = HomeAdapter(::onClick, list, requireContext())
}