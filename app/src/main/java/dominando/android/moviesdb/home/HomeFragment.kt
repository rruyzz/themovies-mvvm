package dominando.android.moviesdb.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import dominando.android.moviesdb.databinding.FragmentListBinding
import dominando.android.moviesdb.model.DiscoveryMovieResponse
import dominando.android.moviesdb.model.DiscoverySerieResponse
import dominando.android.moviesdb.splash.SplashActivity
import dominando.android.moviesdb.utils.extensions.showToast
import dominando.android.moviesdb.utils.extensions.disableTouch
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(), HomeAdapter.onClick {

    private lateinit var mAuth: FirebaseAuth
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding : FragmentListBinding

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
                is HomeMovieList.Success -> setRecycler(it.listSerie, it.listMovie)
                is HomeMovieList.Error -> showToast(requireActivity(), "Error")
                is HomeMovieList.Loading -> renderLoading(it.isLoading)
            }
        })
    }

    private fun setRecycler(listSerie: DiscoverySerieResponse, listMovie: DiscoveryMovieResponse) = with(binding){
        rvSeries.adapter= HomeAdapter(this@HomeFragment ,listSerie.results, requireContext())
        rvMovies.adapter= HomeAdapter(this@HomeFragment ,listMovie.results, requireContext())
        rvSeries.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        rvMovies.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
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

    override fun onClick() {
        TODO("Not yet implemented")
    }
}
