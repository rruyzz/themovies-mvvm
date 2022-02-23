package dominando.android.moviesdb.watched.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dominando.android.moviesdb.R
import dominando.android.moviesdb.adapters.ActorsAdapters
import dominando.android.moviesdb.adapters.WatchedAdapters
import dominando.android.moviesdb.databinding.FragmentListBinding
import dominando.android.moviesdb.databinding.FragmentMoviesWatchedBinding
import dominando.android.moviesdb.model.MovieFirebase
import dominando.android.moviesdb.utils.firebase.firebase.getMovies
import dominando.android.moviesdb.utils.firebase.firebase.getUser
import dominando.android.moviesdb.utils.firebase.firebase.getUsers
import dominando.android.moviesdb.utils.firebase.firebase.moviesWatched

class MoviesWatchedFragment : Fragment() {

    private lateinit var binding: FragmentMoviesWatchedBinding
    val list = listOf(MovieFirebase("23123", true, "Rodolfo", "/pe17f8VDfzbvbHSAKAlcORtBHmW.jpg"),
        MovieFirebase("23123", true, "Rodolfo", "/mzezdUZEnpiUIlxpdyLO1R08Lqm.jpg"),
        MovieFirebase("23123", true, "Filme", "/pe17f8VDfzbvbHSAKAlcORtBHmW.jpg"),
        MovieFirebase("23123", true, "Filme", "/mzezdUZEnpiUIlxpdyLO1R08Lqm.jpg"),
        MovieFirebase("23123", true, "Filme", "/mzezdUZEnpiUIlxpdyLO1R08Lqm.jpg"),
        MovieFirebase("23123", true, "Rodolfo", "/pe17f8VDfzbvbHSAKAlcORtBHmW.jpg"),
        MovieFirebase("23123", true, "Filme", "/mzezdUZEnpiUIlxpdyLO1R08Lqm.jpg"))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesWatchedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        getUsers()
//        getMovies()
//            getUser()
        renderRecycler()
    }

    private fun renderRecycler() = with(binding){
        rvMovies.adapter= WatchedAdapters(getMovies(),requireActivity())
        rvMovies.layoutManager = GridLayoutManager(requireContext(), 3)
    }
}