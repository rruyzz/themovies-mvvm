package dominando.android.moviesdb.serieDetail.serieSeasons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dominando.android.moviesdb.adapters.SeasonAdapter
import dominando.android.moviesdb.databinding.FragmentSerieSeasonsBinding
import dominando.android.moviesdb.model.EpisodesItem
import dominando.android.moviesdb.serieDetail.SerieDetail
import dominando.android.moviesdb.serieDetail.SerieDetailFragmentDirections

class SerieSeasonsFragment(val serieDetail: SerieDetail) : Fragment() {

    private lateinit var binding: FragmentSerieSeasonsBinding
    private val navigation get() = findNavController()

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
        val seasons =serieDetail.detail?.seasonList?.filterNotNull()
        recyclerView.adapter= SeasonAdapter(::onClick, seasons ?: listOf(), requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun onClick(episode: EpisodesItem) {
        val action = SerieDetailFragmentDirections.actionSerieDetailFragmentToEpisodeDetailFragment(episode, serieDetail.providers)
        navigation.navigate(action)
    }
}

