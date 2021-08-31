package dominando.android.moviesdb.search

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dominando.android.moviesdb.R
import dominando.android.moviesdb.databinding.FragmentSearchBinding
import dominando.android.moviesdb.serieDetail.SerieDetail
import dominando.android.moviesdb.serieDetail.SerieDetailAdapter
import dominando.android.moviesdb.serieDetail.serieInfos.SerieInfosFragment
import dominando.android.moviesdb.serieDetail.serieSeasons.SerieSeasonsFragment


class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
        setTab()
    }

    private fun setViewPager() = with(binding){
        val pagerAdapter = SerieDetailAdapter(this@SearchFragment)
        pagerAdapter.addFragment(SerieSeasonsFragment())
        pagerAdapter.addFragment(SerieSeasonsFragment())
        viewPager.adapter = pagerAdapter
    }

    private fun setTab() = with(binding){
        val tabNames = listOf("Sobre", "Episódios")
        tabLayout.apply {
            tabGravity = TabLayout.GRAVITY_FILL
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