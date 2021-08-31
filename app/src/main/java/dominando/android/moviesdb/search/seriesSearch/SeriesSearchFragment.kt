package dominando.android.moviesdb.search.seriesSearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dominando.android.moviesdb.adapters.HomeAdapter
import dominando.android.moviesdb.adapters.SearchAdapter
import dominando.android.moviesdb.databinding.FragmentSeriesSearchBinding
import dominando.android.moviesdb.home.HomeFragmentDirections
import dominando.android.moviesdb.model.ResultsItem
import dominando.android.moviesdb.model.SearchResponse
import dominando.android.moviesdb.model.SeriesResultsResponse
import dominando.android.moviesdb.serieDetail.SerieDetail

class SeriesSearchFragment(val serieList: List<ResultsItem>) : Fragment() {

    private lateinit var binding : FragmentSeriesSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeriesSearchBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews(serieList)
    }

    private fun setViews(listSerie: List<ResultsItem>) = with(binding){
        rvSeries.adapter= SearchAdapter(::onClick, listSerie, requireContext())
        rvSeries.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun onClick(id: Int, isShow: Boolean) {
//        val destination = if(isShow) HomeFragmentDirections.actionListFragmentToSerieDetailFragment(id.toString())
//        else HomeFragmentDirections.actionListFragmentToMovieDetailFragment(id.toString())
//        navigation.navigate(destination)
    }
}