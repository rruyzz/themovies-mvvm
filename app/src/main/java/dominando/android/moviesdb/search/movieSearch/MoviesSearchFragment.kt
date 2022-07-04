package dominando.android.moviesdb.search.movieSearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dominando.android.moviesdb.adapters.MovieSerieItem
import dominando.android.moviesdb.adapters.SearchAdapter
import dominando.android.moviesdb.databinding.FragmentMoviesSearchBinding
import dominando.android.moviesdb.model.MovieItem
import dominando.android.moviesdb.model.ResultsItem
import dominando.android.moviesdb.search.SearchFragmentDirections


class MoviesSearchFragment(val moviesList: List<ResultsItem>?, val serieItemList: List<MovieItem>?) : Fragment() {

    private lateinit var binding : FragmentMoviesSearchBinding
    private val navigation get() = findNavController()

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
        setViews(moviesList
            ?.filter { it.mediaType == "movie"}
            ?.sortedByDescending { it.popularity } ?: listOf())
    }

    private fun setViews(listMovies: List<ResultsItem>) = with(binding){

        if(listMovies.isEmpty()) {
            rvMovies.adapter = SearchAdapter(::onClick, serieItemList ?: listOf(), requireContext())
            rvMovies.layoutManager = LinearLayoutManager(requireContext())
        } else {
            rvMovies.adapter = SearchAdapter(::onClick, listMovies, requireContext())
            rvMovies.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onClick(movie: MovieSerieItem) {
        val destination = SearchFragmentDirections.actionSearchFragmentToMovieDetailFragment(movie.movie_id.toString(), movie.title)
        navigation.navigate(destination)
    }
}