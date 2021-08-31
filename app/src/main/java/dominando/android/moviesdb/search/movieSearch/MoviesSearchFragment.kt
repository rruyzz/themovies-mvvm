package dominando.android.moviesdb.search.movieSearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dominando.android.moviesdb.adapters.SearchAdapter
import dominando.android.moviesdb.databinding.FragmentMoviesSearchBinding
import dominando.android.moviesdb.model.ResultsItem
import dominando.android.moviesdb.model.SearchResponse


class MoviesSearchFragment(val moviesList: List<ResultsItem>) : Fragment() {

    private lateinit var binding : FragmentMoviesSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesSearchBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews(moviesList)
    }

    private fun setViews(listMovies: List<ResultsItem>) = with(binding){
        rvMovies.adapter= SearchAdapter(::onClick, listMovies, requireContext())
        rvMovies.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun onClick(id: Int, isShow: Boolean) {
//        val destination = if(isShow) HomeFragmentDirections.actionListFragmentToSerieDetailFragment(id.toString())
//        else HomeFragmentDirections.actionListFragmentToMovieDetailFragment(id.toString())
//        navigation.navigate(destination)
    }
}