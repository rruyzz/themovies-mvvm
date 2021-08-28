package dominando.android.moviesdb.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import dominando.android.moviesdb.adapters.HomeAdapter
import dominando.android.moviesdb.databinding.FragmentListBinding
import dominando.android.moviesdb.model.MovieResultResponse
import dominando.android.moviesdb.model.SeriesResultsResponse
import dominando.android.moviesdb.splash.SplashActivity
import dominando.android.moviesdb.utils.extensions.showToast
import dominando.android.moviesdb.utils.extensions.disableTouch
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding : FragmentListBinding
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
    }

    private fun setObservers() {
        viewModel.movieViewState.observe(requireActivity(), Observer {
            when(it){
                is HomeMovieList.Success -> setRecycler(it.listSerie, it.listMovie, it.listTopSerie)
                is HomeMovieList.Error -> renderError()
                is HomeMovieList.Loading -> renderLoading(it.isLoading)
            }
        })
    }

    private fun setRecycler(listSerie: SeriesResultsResponse, listMovie: MovieResultResponse, topSeries: MovieResultResponse) = with(binding){
        rvSeries.adapter= HomeAdapter(::onClick, listSerie.results, requireContext())
        rvMovies.adapter= HomeAdapter(::onClick, listMovie.results, requireContext())
        rvTopSeries.adapter= HomeAdapter(::onClick, topSeries.results, requireContext())
        rvSeries.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        rvMovies.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        rvTopSeries.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
    }

    private fun renderError(){
        showToast(requireActivity(), "Error")
        binding.group.isVisible = false
    }
    private fun renderLoading(isLoading: Boolean) = with(binding){
        progress.isVisible  = isLoading
        group.isVisible = isLoading.not()
        disableTouch(isLoading)
    }
    private fun setButtons() = with(binding){
        textSeries.setOnClickListener {
            mAuth = FirebaseAuth.getInstance()
            mAuth.signOut()
            val intent = Intent(requireContext(), SplashActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun onClick(movieId: Int, isShow: Boolean) {
        val destination = HomeFragmentDirections.actionListFragmentToMovieDetailFragment(movieId.toString())
        if(isShow.not()) navigation.navigate(destination)
    }
}
