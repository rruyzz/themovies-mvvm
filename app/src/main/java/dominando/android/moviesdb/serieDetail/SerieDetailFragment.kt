package dominando.android.moviesdb.serieDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dominando.android.moviesdb.databinding.FragmentSerieDetailBinding
import dominando.android.moviesdb.model.SerieDetailResponse
import dominando.android.moviesdb.serieDetail.serieInfos.SerieInfosFragment
import dominando.android.moviesdb.serieDetail.serieSeasons.SerieSeasonsFragment
import dominando.android.moviesdb.utils.Constanst.IMAGE_URL
import dominando.android.moviesdb.utils.extensions.formattedAsHour
import dominando.android.moviesdb.utils.extensions.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

class SerieDetailFragment : Fragment() {

    private lateinit var binding: FragmentSerieDetailBinding
    private val viewModel: SerieDetailViewModel by viewModel()
    private val args by navArgs<SerieDetailFragmentArgs>()
    private val serieId get()= args.serieId

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSerieDetailBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSerieDetail(serieId)
        setObserve()
    }

    private fun setViews(serieDetail: SerieDetail){
        setViewPager(serieDetail)
        setTab()
    }

    private fun setObserve(){
        viewModel.serieDetailViewState.observe(requireActivity(), Observer {
            when(it){
                is SerieDetailsState.Success -> renderSuccess(it.serieDetail)
                is SerieDetailsState.Error -> showToast(requireContext(), it.error)
                is SerieDetailsState.Loading -> renderLoading(it.isLoading)
            }
        })
    }

    private fun renderLoading(isLoading: Boolean) = with(binding){
        progress.isVisible = isLoading
        scroll.isVisible = isLoading.not()
    }

    private fun renderSuccess(serieDetail: SerieDetail) = with(binding){
        setViews(serieDetail)
        Glide.with(this@SerieDetailFragment).load(IMAGE_URL+serieDetail.detail.backdropPath).into(poster)
        textTitle.text = serieDetail.detail.name
        textDuration.text = try{
            serieDetail.detail.episodeRunTime?.get(0).toString().formattedAsHour()
        } catch (e: Exception) {
            Log.e("MoviesDetail", "exception", e);
            "Tempo não disponivel"
        }
        textGenero.text = if (serieDetail.detail.genres?.isNotEmpty() == true) serieDetail.detail.genres.first()?.name
        else ""
    }
    private fun setTab() = with(binding){
        val tabNames = listOf("Sobre", "Episódios")
        tabLayout.apply {
            tabGravity = TabLayout.GRAVITY_FILL
            TabLayoutMediator(tabLayout, viewPager){tab, position ->
                tab.text = tabNames[position]
            }.attach()
            addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {}
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }
    private fun setViewPager(serieDetail: SerieDetail) = with(binding){
        val pagerAdapter = SerieDetailAdapter(this@SerieDetailFragment)
        pagerAdapter.addFragment(SerieInfosFragment(serieDetail))
        pagerAdapter.addFragment(SerieSeasonsFragment(serieDetail))
        viewPager.adapter = pagerAdapter
    }
}