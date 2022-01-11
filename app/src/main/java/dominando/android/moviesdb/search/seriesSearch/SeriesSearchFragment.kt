package dominando.android.moviesdb.search.seriesSearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dominando.android.moviesdb.adapters.SearchAdapter
import dominando.android.moviesdb.databinding.FragmentSeriesSearchBinding
import dominando.android.moviesdb.model.ResultsItem
import dominando.android.moviesdb.model.SerieItem
import dominando.android.moviesdb.search.SearchFragmentDirections
import dominando.android.moviesdb.search.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeriesSearchFragment(val serieSearch: List<ResultsItem>?, val serieItemList: List<SerieItem>?) : Fragment() {

    private lateinit var binding : FragmentSeriesSearchBinding
    private val navigation get() = findNavController()

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
        setViews(serieSearch
            ?.filter{ it.mediaType == "tv"}
            ?.sortedByDescending { it.popularity } ?: listOf())
    }

    private fun setViews(listSerie: List<ResultsItem>) = with(binding){
        if(listSerie.isEmpty()) {
            rvSeries.adapter= SearchAdapter(::onClick, serieItemList ?: listOf(), requireContext())
            rvSeries.layoutManager = LinearLayoutManager(requireContext())
        } else {
            rvSeries.adapter= SearchAdapter(::onClick,listSerie, requireContext())
            rvSeries.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onClick(id: Int, isShow: Boolean) {
        val destination =  SearchFragmentDirections.actionSearchFragmentToSerieDetailFragment(id.toString())
        navigation.navigate(destination)
    }
}