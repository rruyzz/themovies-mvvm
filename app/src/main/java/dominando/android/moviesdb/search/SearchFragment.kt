package dominando.android.moviesdb.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dominando.android.moviesdb.databinding.FragmentSearchBinding
import dominando.android.moviesdb.model.MovieItem
import dominando.android.moviesdb.model.ResultsItem
import dominando.android.moviesdb.model.SerieItem
import dominando.android.moviesdb.search.movieSearch.MoviesSearchFragment
import dominando.android.moviesdb.search.seriesSearch.SeriesSearchFragment
import dominando.android.moviesdb.serieDetail.SerieDetailAdapter
import dominando.android.moviesdb.utils.extensions.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        setView()
        viewModel.getDiscoveyList()
    }

    private fun setObserver() {
        viewModel.searchViewState.observe(requireActivity(), Observer {
            when(it){
                is SearchState.Succcess -> setViewPager(it.success.results)
                is SearchState.Error -> showToast(requireContext(), "Error")
                is SearchState.Loading -> renderLoading(it.isLoading)
            }
        })
        viewModel.discoveryListViewState.observe(requireActivity(), Observer {
            when(it){
                is SeriesSearchList.Success -> setViewPagerDiscovery(it.listSerie.results, it.listMovie.results)
                is SeriesSearchList.Error -> showToast(requireContext(), "Error")
                is SeriesSearchList.Loading -> binding.progress.isVisible = it.isLoading
            }
        })
    }

    private fun renderLoading(isLoading: Boolean) = with(binding){
        progress.isVisible  = isLoading
    }
    private fun setView() = with(binding){
        textInputLayout.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchMovie(textInputLayout.query.toString())
                return false
            }
        })
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        tabLayout.addTab((tabLayout.newTab().setText("Serie")))
        tabLayout.addTab((tabLayout.newTab().setText("Movie")))
    }

    private fun setViewPager(results: List<ResultsItem>) = with(binding){
        val pagerAdapter = SerieDetailAdapter(this@SearchFragment)
        pagerAdapter.addFragment(SeriesSearchFragment(results ?: listOf(), null))
        pagerAdapter.addFragment(MoviesSearchFragment(results, null))
        viewPager.adapter = pagerAdapter
        setTab()
    }

    private fun setViewPagerDiscovery(resultsSerie: List<SerieItem>?,resultsMovie: List<MovieItem>?) = with(binding){
        val pagerAdapter = SerieDetailAdapter(this@SearchFragment)
        pagerAdapter.addFragment(SeriesSearchFragment(null, resultsSerie ?: listOf()))
        pagerAdapter.addFragment(MoviesSearchFragment(null, resultsMovie ?: listOf()))
        viewPager.adapter = pagerAdapter
        setTab()
    }

    private fun setTab() = with(binding){
        val tabNames = listOf("Séries", "Filmes")
        tabLayout.apply {
            TabLayoutMediator(tabLayout, viewPager){tab, position ->
                tab.text = tabNames[position]
            }.attach()
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {}
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }
}