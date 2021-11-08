package dominando.android.moviesdb.serieDetail.serieInfos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import dominando.android.moviesdb.adapters.ActorsAdapters
import dominando.android.moviesdb.adapters.ProviderAdapter
import dominando.android.moviesdb.databinding.FragmentSerieInfosBinding
import dominando.android.moviesdb.serieDetail.SerieDetail
import dominando.android.moviesdb.utils.extensions.formtattedAsDate


class SerieInfosFragment(val serieDetail: SerieDetail) : Fragment() {

    private lateinit var binding: FragmentSerieInfosBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSerieInfosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
    }

    private fun setViews(){
        setDetails()
        setProviders()
        renderCasting()
    }

    private fun setDetails()= with(binding){
        textResume.text = serieDetail.detail.overview
        firstAir.text = serieDetail.detail.firstAirDate.formtattedAsDate()
        textGenero.text = if (serieDetail.detail.genres?.isNotEmpty() == true) serieDetail.detail.genres.first()?.name else ""
        textGrade.text = "${serieDetail.detail.voteAverage}/10"
    }

    private fun setProviders() = with(binding){
        textWhereWatch.isVisible = serieDetail.providers.results.bR?.flatrate?.isNullOrEmpty()?.not() ?: false
        view2.isVisible = serieDetail.providers.results.bR?.flatrate?.isNullOrEmpty()?.not() ?: false
        rvProvider.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvProvider.adapter = ProviderAdapter(serieDetail.providers.results.bR?.flatrate ?: listOf())
    }

    private fun renderCasting() = with(binding){
        rvCasting.adapter= ActorsAdapters(serieDetail.casting.cast,requireActivity())
        rvCasting.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
    }
}
