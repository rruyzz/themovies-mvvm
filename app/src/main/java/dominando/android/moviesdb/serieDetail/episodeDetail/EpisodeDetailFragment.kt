package dominando.android.moviesdb.serieDetail.episodeDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dominando.android.moviesdb.adapters.ActorsAdapters
import dominando.android.moviesdb.adapters.ProviderAdapter
import dominando.android.moviesdb.databinding.FragmentEpisodeDetailBinding
import dominando.android.moviesdb.utils.Constanst
import dominando.android.moviesdb.utils.extensions.formtattedAsDate


class EpisodeDetailFragment : Fragment() {

    private lateinit var binding: FragmentEpisodeDetailBinding
    private val args by navArgs<EpisodeDetailFragmentArgs>()
    private val episode get() = args.episode
    private val detail get() = args.providers

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
    }

    private fun setView() = with(binding){
        icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        Glide.with(requireActivity()).load(Constanst.IMAGE_URL +episode.stillPath).into(poster)
        textTitle.text = episode.name
        textDate.text = episode.airDate.formtattedAsDate()
        rating.rating = (episode.voteAverage/2).toFloat()
        textResume.text = episode.overview
        rvSimilarMovies.adapter = ActorsAdapters(episode.guestStars, requireContext())
        rvSimilarMovies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvProvider.adapter = ProviderAdapter(detail?.results?.bR?.flatrate ?: listOf())
        rvProvider.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }
}