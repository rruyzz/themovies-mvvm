package dominando.android.moviesdb.serieDetail.serieSeasons

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dominando.android.moviesdb.adapters.ActorsAdapters
import dominando.android.moviesdb.adapters.SeasonAdapter
import dominando.android.moviesdb.databinding.FragmentSerieSeasonsBinding
import dominando.android.moviesdb.serieDetail.SerieDetail

class SerieSeasonsFragment(val serieDetail: SerieDetail) : Fragment() {

    private lateinit var binding: FragmentSerieSeasonsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSerieSeasonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderSeriesList()
    }

    private fun renderSeriesList() = with(binding){
        recyclerView.adapter= SeasonAdapter(serieDetail.detail.seasons, requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}

