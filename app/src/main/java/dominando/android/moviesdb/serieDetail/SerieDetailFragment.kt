package dominando.android.moviesdb.serieDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dominando.android.moviesdb.R
import dominando.android.moviesdb.databinding.FragmentSerieDetailBinding
import dominando.android.moviesdb.serieDetail.serieInfos.SerieInfosFragment
import dominando.android.moviesdb.serieDetail.serieSeasons.SerieSeasonsFragment

class SerieDetailFragment : Fragment() {

    private lateinit var binding: FragmentSerieDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSerieDetailBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
        setTab()
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
    private fun setViewPager() = with(binding){
        val pagerAdapter = SerieDetailAdapter(this@SerieDetailFragment)
        pagerAdapter.addFragment(SerieInfosFragment())
        pagerAdapter.addFragment(SerieSeasonsFragment())
        viewPager.adapter = pagerAdapter
//        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
//
//        })
    }

}